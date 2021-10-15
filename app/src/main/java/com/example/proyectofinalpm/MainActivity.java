package com.example.proyectofinalpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.example.proyectofinalpm.models.Persona;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    //LISTA DE TIPO DE PERSONA:
    private List<Persona> listPersona = new ArrayList<Persona>();
    //CREACION DE ARRAYADAPTER DE TIPO PERSONA::::::::::::::::::::
    ArrayAdapter<Persona> arrayAdapterPersona;


    ListView listV_persona;

    //VARIABLES PARA LA BASE DE DATOS:
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    //PARTE DE LA EDICION DE CONTACTOS:
    Persona personaSelected;
    EditText editTextNombre, editTextApellidos, editTextCorreo;

    //FLOATING BUTTONS:
    FloatingActionsMenu grupoBotones;
    com.getbase.floatingactionbutton.FloatingActionButton fabbtnAgregar;
    com.getbase.floatingactionbutton.FloatingActionButton fabbtnEliminar;
    com.getbase.floatingactionbutton.FloatingActionButton fabbtnEditar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //FLOATING BUTTONS:
        grupoBotones = (FloatingActionsMenu) findViewById(R.id.grupoFab);
        fabbtnAgregar = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fabbtnAgregar);
        fabbtnEditar = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fabbtnEditar);
        fabbtnEliminar = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fabbtnEliminar);
        inicializarFirebase();
        listarDatos();


        //PONER ICONO EN ACCION BAR:::::::::::::::::::
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        listV_persona = findViewById(R.id.listv_DatosPersonas);

        //EDICION DE PERSONA
        editTextNombre = (EditText) findViewById(R.id.txt_nombrePersona);
        editTextApellidos = (EditText) findViewById(R.id.txt_apellidPersona);
        editTextCorreo = (EditText) findViewById(R.id.txt_emailPersona);


        listV_persona.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, MostrarActivity.class);
                personaSelected = (Persona) parent.getItemAtPosition(position);

                /*
                String itemSeleccionado = String.valueOf((listV_persona.getItemAtPosition(position)));


                i.putExtra("nombre", itemSeleccionado);

                startActivity(i);
                */

            }
        });


        //ESTRUCTURA PARA PODERLE DAR FUNCION AL BOTON ADD
        fabbtnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AgregarActivity.class);
                startActivity(i);
                grupoBotones.collapse();
            }
        });

        fabbtnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EditarActivity.class);
                startActivity(i);
                grupoBotones.collapse();
            }
        });


        fabbtnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EliminarActivity.class);
                startActivity(i);
                grupoBotones.collapse();
            }
        });
    }


    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();

    }

    private void listarDatos() {
        //ESTA LINEA DESPLIEGA LAS DEMAS
        databaseReference.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listPersona.clear();
                for (DataSnapshot objSnaptshot : snapshot.getChildren()) {
                    Persona p = objSnaptshot.getValue(Persona.class);
                    listPersona.add(p);

                    arrayAdapterPersona = new ArrayAdapter<Persona>(MainActivity.this, android.R.layout.simple_list_item_1, listPersona);
                    listV_persona.setAdapter(arrayAdapterPersona);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //CODIGO REFERENTE AL MENU SUPERIOR::::::::::::::::::::::::::::::
    //CON ESTE METODO RETORNO EL MENU QUE CREE
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //EN ESTA PARTE DEL CODIGO PONGO LO QUE ES EL MENU
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);


    }

    //METODO QUE CREARA LAS ACCIONES QUE PONGA EN ITEM DE MENU
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item1) {
            Toast.makeText(this, "App creada por: OxOrboy - Danix25 - Yego", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }


}
