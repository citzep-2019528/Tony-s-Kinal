package org.christianitzep.bean;

import java.util.Date;


public class ServiciosHasEmpleados {
    private int servicios_codigoServicio;
    private int codigoEmpleado;
    private int codigoServicio;
    private Date fechaEvento;
    private String horaEvento;
    private String LugarEvento;

    public ServiciosHasEmpleados() {
    }

    public ServiciosHasEmpleados(int servicios_codigoServicio, int codigoEmpleado, int codigoServicio, Date fechaEvento, String horaEvento, String LugarEvento) {
        this.servicios_codigoServicio = servicios_codigoServicio;
        this.codigoEmpleado = codigoEmpleado;
        this.codigoServicio = codigoServicio;
        this.fechaEvento = fechaEvento;
        this.horaEvento = horaEvento;
        this.LugarEvento = LugarEvento;
    }

   

    public int getServicios_codigoServicio() {
        return servicios_codigoServicio;
    }

    public void setServicios_codigoServicio(int servicios_codigoServicio) {
        this.servicios_codigoServicio = servicios_codigoServicio;
    }

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public int getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(int codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getHoraEvento() {
        return horaEvento;
    }

    public void setHoraEvento(String horaEvento) {
        this.horaEvento = horaEvento;
    }

    public String getLugarEvento() {
        return LugarEvento;
    }

    public void setLugarEvento(String LugarEvento) {
        this.LugarEvento = LugarEvento;
    }

   
    
}
