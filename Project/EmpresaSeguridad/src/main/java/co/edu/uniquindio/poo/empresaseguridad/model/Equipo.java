package co.edu.uniquindio.poo.empresaseguridad.model;

public class Equipo {
    private String codigo;
    private TipoEquipo tipo;
    private EstadoEquipo estado;
    private double valorReposicion;

    public Equipo(String codigo, TipoEquipo tipo, EstadoEquipo estado,
                  double valorReposicion) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.estado = estado;
        this.valorReposicion = valorReposicion;
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public TipoEquipo getTipo() { return tipo; }
    public void setTipo(TipoEquipo tipo) { this.tipo = tipo; }
    public EstadoEquipo getEstado() { return estado; }
    public void setEstado(EstadoEquipo estado) { this.estado = estado; }
    public double getValorReposicion() { return valorReposicion; }
    public void setValorReposicion(double valorReposicion) {
        this.valorReposicion = valorReposicion;
    }
}