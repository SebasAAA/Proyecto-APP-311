package com.example.proyectoapp311cr;

public class IncidentData {

    public int id, cedula;
    public String nombre, apellido1, apellido2, categoria, empresa, estado, detalle, provincia, canton, distrito, direccion, georeferencia, fotografia1, fotografia2, fotografia3, fotografia4;

    public IncidentData(){}

    public IncidentData(int id, int cedula, String nombre, String apellido1, String apellido2, String categoria, String empresa, String estado, String detalle, String provincia, String canton, String distrito, String direccion, String georeferencia, String fotografia1, String fotografia2, String fotografia3, String fotografia4) {

        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.categoria = categoria;
        this.empresa = empresa;
        this.estado = estado;
        this.detalle = detalle;
        this.provincia = provincia;
        this.canton = canton;
        this.distrito = distrito;
        this.direccion = direccion;
        this.georeferencia = georeferencia;
        this.fotografia1 = fotografia1;
        this.fotografia2 = fotografia2;
        this.fotografia3 = fotografia3;
        this.fotografia4 = fotografia4;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getGeoreferencia() {
        return georeferencia;
    }

    public void setGeoreferencia(String georeferencia) {
        this.georeferencia = georeferencia;
    }

    public String getFotografia1() {
        return fotografia1;
    }

    public void setFotografia1(String fotografia1) {
        this.fotografia1 = fotografia1;
    }

    public String getFotografia2() {
        return fotografia2;
    }

    public void setFotografia2(String fotografia2) {
        this.fotografia2 = fotografia2;
    }

    public String getFotografia3() {
        return fotografia3;
    }

    public void setFotografia3(String fotografia3) {
        this.fotografia3 = fotografia3;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFotografia4() {
        return fotografia4;
    }

    public void setFotografia4(String fotografia4) {
        this.fotografia4 = fotografia4;
    }
}
