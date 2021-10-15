package com.example.proyectofinalpm.models;

public class Persona {

    private String uid;
    private String Nombre;
    private String Apellidos;
    private String Correo;
    private String Numero;
    private String FechaNacimiento;
    private int img;

    public Persona(String uid, String nombre, String apellidos, String correo, String numero, String fechaNacimiento, int img) {
        this.uid = uid;
        Nombre = nombre;
        Apellidos = apellidos;
        Correo = correo;
        Numero = numero;
        FechaNacimiento = fechaNacimiento;
        this.img = img;
    }

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

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    //ESTE METODO MUESTRE LO QUE QUIERES QUE SE MUESTRE EN LA LISTA:
    @Override
    public String toString() {
        return Nombre+" "+Apellidos;
    }
}
