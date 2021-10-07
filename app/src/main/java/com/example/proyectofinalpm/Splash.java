package com.example.proyectofinalpm;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //OCULTAR ACTION BAR
       ActionBar actionbar = getSupportActionBar();
       actionbar.hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int duraction_splash = 1500;

        //NOS PERMITE EJECUTAR CODIGO POR UN DETERMINADO TIEMPO:
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //intent para pasar de una activity a otra: contexto,a donde vamos a ir;
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finish();
                //CODIGO QUE SE EJECUTARA:


            };
        }, duraction_splash);

    }
}