    package org.christianitzep.bean;

    public class Empresa {
        private int codigoEmpresa;
        private String nombreEmpresa;
        private String Direccion;
        private String Telefono;

    public Empresa() {
    }

    public Empresa(int codigoEmpresa, String nombreEmpresa, String Direccion, String Telefono) {
        this.codigoEmpresa = codigoEmpresa;
        this.nombreEmpresa = nombreEmpresa;
        this.Direccion = Direccion;
        this.Telefono = Telefono;
    }

    public int getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(int codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    @Override
    public String toString() {
        return  codigoEmpresa +". " + nombreEmpresa ;
    }
  
}
