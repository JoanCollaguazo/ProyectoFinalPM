package com.example.proyectofinalpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.List;

public class EditarActivity extends AppCompatActivity {

    EditText editTextNombre, editTextApellidos, editTextCorreo, editTextNumero, editTextFechaNacimiento;
    Button btnActualizar;


    //VARIABLES PARA LA BASE DE DATOS:
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //LISTA DE TIPO DE PERSONA:
    private List<Persona> listPersona = new ArrayList<Persona>();
    //CREACION DE ARRAYADAPTER DE TIPO PERSONA::::::::::::::::::::
    ArrayAdapter<Persona> arrayAdapterPersona;
    ListView listV_persona;

    //PARA SELECCIONAR DE LA LISTA:
    Persona personaSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        editTextNombre = (EditText) findViewById(R.id.txt_nombrePersona);
        editTextApellidos = (EditText) findViewById(R.id.txt_apellidPersona);
        editTextCorreo = (EditText) findViewById(R.id.txt_emailPersona);
        editTextNumero = (EditText) findViewById(R.id.txt_numero);
        editTextFechaNacimiento = (EditText) findViewById(R.id.txtFechadeCumpleaños);
        btnActualizar = (Button) findViewById(R.id.btnActualizar);
        listV_persona = findViewById(R.id.listv_DatosPersonas);

        //ESTE METODO DEBE ESTAR SIEMPRE::::::::::::
        inicializarFirebase();
        listarDatos();


        //OCULTAR ACTION BAR
        ActionBar actionbar = getSupportActionBar();
        actionbar.hide();

        //DARLE ACCION A ITEM SELECCIONADO DE LA LISTA::::::::::::::::::::::::::::::
        listV_persona.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //posicion - item seleccionado:
                personaSelected = (Persona) parent.getItemAtPosition(position);
                editTextNombre.setText(personaSelected.getNombre());
                editTextApellidos.setText(personaSelected.getApellidos());
                editTextCorreo.setText(personaSelected.getCorreo());
                editTextNumero.setText(personaSelected.getNumero());
                editTextFechaNacimiento.setText(personaSelected.getFechaNacimiento());

            }
        });
        //METODO PARA GUARDAR DATOS::::::::::::::::::::::::::::::::::::
        //.trim() sirve para ignorar espacios en blanco:
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Persona p = new Persona();
                p.setUid(personaSelected.getUid());
                p.setNombre(editTextNombre.getText().toString().trim());
                p.setApellidos(editTextApellidos.getText().toString().trim());
                p.setCorreo(editTextCorreo.getText().toString().trim());
                p.setNumero(editTextNumero.getText().toString().trim());
                p.setFechaNacimiento(editTextFechaNacimiento.getText().toString().trim());
                //CODIGO FIREBASE:::::::::::::::::::::::::::::::::::::::::::::::
                databaseReference.child("Persona").child(p.getUid()).setValue(p);
                Toast.makeText(getApplicationContext(), "Usuario Actualizado", Toast.LENGTH_SHORT).show();
                limpiarCajas();
                CambiodeActivity();

            }
        });

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

                    arrayAdapterPersona = new ArrayAdapter<Persona>(EditarActivity.this, android.R.layout.simple_list_item_1, listPersona);
                    listV_persona.setAdapter(arrayAdapterPersona);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }
    private void CambiodeActivity() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    public void abrirCalendario(View view) {
        Calendar cal = Calendar.getInstance();
        int año = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fecha = dayOfMonth + "/" + month + "/" + year;
                editTextFechaNacimiento.setText(fecha);
            }

        }, año, mes, dia);
        dpd.show();


    }

    private void limpiarCajas() {
        editTextNombre.setText("");
        editTextCorreo.setText("");
        editTextApellidos.setText("");
        editTextNumero.setText("");
        editTextFechaNacimiento.setText("");
    }

}