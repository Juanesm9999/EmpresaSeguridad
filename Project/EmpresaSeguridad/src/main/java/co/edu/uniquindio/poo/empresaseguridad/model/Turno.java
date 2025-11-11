package co.edu.uniquindio.poo.empresaseguridad.model;

public enum Turno {
    DIA("Día"),
    NOCHE("Noche");

    private String nombre;

    Turno(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
