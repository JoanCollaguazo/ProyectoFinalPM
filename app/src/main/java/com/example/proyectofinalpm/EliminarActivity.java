package com.example.proyectofinalpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.proyectofinalpm.models.Persona;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EliminarActivity extends AppCompatActivity {


    EditText editTextNombre, editTextApellidos, editTextCorreo, editTextNumero, editTextFechaNacimiento;

    //LISTA DE TIPO DE PERSONA:
    private List<Persona> listPersona = new ArrayList<Persona>();
    //CREACION DE ARRAYADAPTER DE TIPO PERSONA::::::::::::::::::::
    ArrayAdapter<Persona> arrayAdapterPersona;
    ListView listV_persona;

    //VARIABLES PARA LA BASE DE DATOS:
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //PARA SELECCIONAR DE LA LISTA:
    Persona personaSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar);


        editTextNombre = (EditText) findViewById(R.id.txt_nombrePersona);
        editTextNombre.setEnabled(false);
        editTextApellidos = (EditText) findViewById(R.id.txt_apellidPersona);
        editTextApellidos.setEnabled(false);
        editTextCorreo = (EditText) findViewById(R.id.txt_emailPersona);
        editTextCorreo.setEnabled(false);
        editTextNumero = (EditText) findViewById(R.id.txt_numero);
        editTextNumero.setEnabled(false);
        editTextFechaNacimiento = (EditText) findViewById(R.id.txtFechadeCumpleaños);
        editTextFechaNacimiento.setEnabled(false);
        listV_persona = findViewById(R.id.listv_DatosPersonas);

        inicializarFirebase();
        listarDatos();


        listV_persona.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                personaSelected = (Persona) parent.getItemAtPosition(position);
                editTextNombre.setText(personaSelected.getNombre());
                editTextApellidos.setText(personaSelected.getApellidos());
                editTextCorreo.setText(personaSelected.getCorreo());
                editTextNumero.setText(personaSelected.getNumero());
                editTextFechaNacimiento.setText(personaSelected.getFechaNacimiento());

                AlertDialog.Builder alerta = new AlertDialog.Builder(EliminarActivity.this);
                alerta.setMessage("¿Desea eliminar al usuario?")
                        .setCancelable(false) //para clickear a lado de dialog y salir
                        //EN ESTE PARTE PONDREMOS QUE HARA CUANDO LE DEMOS OPCION SI:::::::::::
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Persona p = new Persona();
                                p.setUid(personaSelected.getUid());
                                p.setNombre(personaSelected.getNombre().trim());
                                p.setApellidos(personaSelected.getApellidos().trim());
                                p.setCorreo(personaSelected.getCorreo().trim());
                                p.setNumero(personaSelected.getNumero().trim());
                                p.setFechaNacimiento(personaSelected.getFechaNacimiento().trim());
                                databaseReference.child("Persona").child(p.getUid()).removeValue();
                                Toast.makeText(getApplicationContext(), "Usuario Eliminado", Toast.LENGTH_SHORT).show();
                                limpiarCajas();
                                CambiodeActivity();
                            }
                        })
                        //EN CASO DE QUE LA OPCION SEA NO:::::::::::::::::::::::::::::::::::::
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                limpiarCajas();
                            }
                        });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("¿ESTA SEGURO DE ELIMINAR?");
                titulo.show();


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

                    arrayAdapterPersona = new ArrayAdapter<Persona>(EliminarActivity.this, android.R.layout.simple_list_item_1, listPersona);
                    listV_persona.setAdapter(arrayAdapterPersona);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void limpiarCajas() {
        editTextNombre.setText("");
        editTextCorreo.setText("");
        editTextApellidos.setText("");
        editTextNumero.setText("");
        editTextFechaNacimiento.setText("");
    }

    private void CambiodeActivity() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }


}