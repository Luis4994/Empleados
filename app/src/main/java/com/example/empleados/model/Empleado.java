package com.example.empleados.model;

public class Empleado {
    String Nombre, RUT, Cargo, Antiguedad;

    public Empleado(){}


    public Empleado(String Nombre, String RUT, String Cargo, String Antiguedad) {
        this.Nombre = Nombre;
        this.RUT = RUT;
        this.Cargo = Cargo;
        this.Antiguedad = Antiguedad;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getRUT() {
        return RUT;
    }

    public void setRUT(String RUT) {
        this.RUT = RUT;
    }

    public String getCargo() {
        return Cargo;
    }

    public void setCargo(String cargo) {
        Cargo = cargo;
    }

    public String getAntiguedad() {
        return Antiguedad;
    }

    public void setAntiguedad(String antiguedad) {
        Antiguedad = antiguedad;
    }
}
