package com.example.proyectofinalpm.models;

public class Persona {

    private String uid;
    private String Nombre;
    private String Apellidos;
    private String Correo;
    private String Numero;
    private String FechaNacimiento;


    public Persona() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
    }

    public String getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        FechaNacimiento = fechaNacimiento;
    }

    //ESTE METODO MUESTRE LO QUE QUIERES QUE SE MUESTRE EN LA LISTA:
    @Override
    public String toString() {
        return Nombre;
    }
}
