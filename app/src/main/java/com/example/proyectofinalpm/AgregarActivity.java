package com.example.proyectofinalpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.proyectofinalpm.R;
import com.example.proyectofinalpm.models.Persona;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AgregarActivity extends AppCompatActivity {

    EditText editTextNombre, editTextApellidos, editTextCorreo;
    ListView listViewPersona;

    //VARIABLES PARA LA BASE DE DATOS:
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuario);
        editTextNombre = (EditText) findViewById(R.id.txt_nombrePersona);
        editTextApellidos = (EditText) findViewById(R.id.txt_apellidPersona);
        editTextCorreo = (EditText) findViewById(R.id.txt_emailPersona);

        listViewPersona = findViewById(R.id.lv_DatosPersonas);

        //ESTE METODO DEBE ESTAR SIEMPRE::::::::::::
        inicializarFirebase();


    }
    //METODO PARA INICIALIZAR FIREBASE:::::::::::
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //EN ESTA PARTE DEL CODIGO PONGO LO QUE ES EL MENU
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //CREACION DE VARIABLE PARA VALIDACION
        String nombre = editTextNombre.getText().toString();
        String apellido = editTextApellidos.getText().toString();
        String correo = editTextCorreo.getText().toString();

        switch (item.getItemId()) {
            case R.id.icon_add: {
                if (nombre.equals("") || apellido.equals("") || correo.equals("")) {
                    validacion();
                } else {
                    //AQUI IRA EL METODO DE GUARDAR Y TODO LO QUE TIENE QUE VER CON ELLO:::::
                    Persona p = new Persona();
                    //EN EL CASO DEL ID SE LO GENERA CON ESTA LINEA AUTOMATICO:
                    p.setUid(UUID.randomUUID().toString());
                    p.setNombre(nombre);
                    p.setApellidos(apellido);
                    p.setCorreo(correo);
                    //AÑADE AL FIREBASE DATOS:::
                    databaseReference.child("Persona").child(p.getUid()).setValue(p);
                    //Toast.makeText(this, "Friend! agregado con exito", Toast.LENGTH_SHORT).show();
                    limpiarCajas();
                    CambiodeActivity();
                    break;

                }
            }

            case R.id.icon_delete: {
                if (nombre.equals("") || apellido.equals("") || correo.equals("")) {
                    validacion();
                } else {
                    Toast.makeText(this, "Eliminar usuario", Toast.LENGTH_SHORT).show();
                    limpiarCajas();
                    break;
                }
            }

            case R.id.icon_save: {
                Toast.makeText(this, "Usuario almacenado", Toast.LENGTH_SHORT).show();
                break;
            }


        }


        return super.onOptionsItemSelected(item);
    }

    private void CambiodeActivity() {
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }

    private void limpiarCajas() {
        editTextNombre.setText("");
        editTextCorreo.setText("");
        editTextApellidos.setText("");
    }

    private void validacion() {
        String nombre = editTextNombre.getText().toString();
        String apellidos = editTextApellidos.getText().toString();
        String email = editTextCorreo.getText().toString();

        if (nombre.equals("")) {
            editTextNombre.setError("Required");
        }
        if (apellidos.equals("")) {
            editTextApellidos.setError("Required");
        }
        if (email.equals("")) {
            editTextCorreo.setError("Required");
        }
    }
}