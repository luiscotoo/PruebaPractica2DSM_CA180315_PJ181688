package sv.edu.udb.carsmotorsapp;

public class VehiculosVo {
    private String Marca, Modelo, Anio, Color, Capacidad, Precio;
    private int foto;

    public VehiculosVo(String marca, String modelo, String anio, String color, String capacidad, String precio, int foto) {
        Marca = marca;
        Modelo = modelo;
        Anio = anio;
        Color = color;
        Capacidad = capacidad;
        Precio = precio;
        this.foto = foto;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String modelo) {
        Modelo = modelo;
    }

    public String getAnio() {
        return Anio;
    }

    public void setAnio(String anio) {
        Anio = anio;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getCapacidad() {
        return Capacidad;
    }

    public void setCapacidad(String capacidad) {
        Capacidad = capacidad;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
}
