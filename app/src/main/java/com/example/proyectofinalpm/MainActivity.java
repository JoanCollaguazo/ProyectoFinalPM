package com.example.proyectofinalpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.proyectofinalpm.models.Persona;
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

    //BTN FLOTANTE DE AGREGAR::::::::::::::::::
    private FloatingActionButton btnAgregarFAB;
    //LISTA DE TIPO DE PERSONA:
    private List<Persona> listPersona = new ArrayList<Persona>();
    //CREACION DE ARRAYADAPTER DE TIPO PERSONA::::::::::::::::::::
    ArrayAdapter<Persona> arrayAdapterPersona;

    //VARIABLES PARA LA BASE DE DATOS:
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ListView listV_persona;

    //PARTE DE LA EDICION DE CONTACTOS:
    Persona personaSelected;
    EditText editTextNombre, editTextApellidos, editTextCorreo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarFirebase();
        listarDatos();

        //PONER ICONO EN ACCION BAR:::::::::::::::::::
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        listV_persona = findViewById(R.id.lv_DatosPersonas);
        btnAgregarFAB = (FloatingActionButton) findViewById(R.id.fabbtnAgregar);

        //EDICION DE PERSONA
        editTextNombre = (EditText) findViewById(R.id.txt_nombrePersona);
        editTextApellidos = (EditText) findViewById(R.id.txt_apellidPersona);
        editTextCorreo = (EditText) findViewById(R.id.txt_emailPersona);


        //METODO PARA PODER EDITAR LOS DATOS DE LA LISTA:::
        listV_persona.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //PROBLEMA CON EL CMABIO DE VENTANA PARA EDITAR
                CambiodeActivity();
                personaSelected = (Persona) parent.getItemAtPosition(position);
                editTextNombre.setText(personaSelected.getNombre());
                editTextApellidos.setText(personaSelected.getApellidos());
                editTextCorreo.setText(personaSelected.getCorreo());
            }
        });


        //ESTRUCTURA PARA PODERLE DAR FUNCION AL BOTON ADD
        btnAgregarFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AgregarActivity.class);
                startActivity(i);
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

    private void CambiodeActivity() {
        Intent i = new Intent(getApplicationContext(), AgregarActivity.class);
        startActivity(i);
    }

}