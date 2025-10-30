package co.edu.uniquindio.poo.empresaseguridad.model;

public class Equipo {
    private String id;
    private String valorReposicion;
    private TipoEquipo tipoEquipo;
    private EstadoEquipo estadoEquipo;

    public Equipo(){
        this.id = id;
        this.valorReposicion = valorReposicion;
        this.tipoEquipo = tipoEquipo;

    }


    //-------------------------------getters y setters------------------------------------------


    public String getIdo() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValorReposicion() {
        return valorReposicion;
    }

    public void setValorReposicion(String valorReposicion) {
        this.valorReposicion = valorReposicion;
    }

    public TipoEquipo getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTipoEquipo(TipoEquipo tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }

    public EstadoEquipo getEstadoEquipo() {
        return estadoEquipo;
    }

    public void setEstadoEquipo(EstadoEquipo estadoEquipo) {
        this.estadoEquipo = estadoEquipo;
    }

    public String getId() {
        return id;
    }
}
