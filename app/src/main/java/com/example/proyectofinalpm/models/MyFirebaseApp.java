package com.example.proyectofinalpm.models;

import com.google.firebase.database.FirebaseDatabase;

public class MyFirebaseApp extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //CREE ESTA CLASE PARA PODER ACTIVAR LA PERSISTENCIA DE DATOS DE MANERA GLOBAL
        //CON ESTO EVITO ACTIVAR PERSISTENCIA DE DATOS POR ACTIVITY
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
