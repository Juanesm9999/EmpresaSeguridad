package co.edu.uniquindio.poo.empresaseguridad.model;

import java.util.ArrayList;
import java.util.List;

public class CustodiaFija extends ServicioSeguridad {
    private List<Vigilante> vigilantesAsignados;

    public CustodiaFija(String codigoContrato, String cliente,
                        double tarifaMensual, EstadoServicio estado) {
        super(codigoContrato, cliente, tarifaMensual, estado);
        this.vigilantesAsignados = new ArrayList<>();
    }

    @Override
    public double calcularCostoMensual() {
        double costoBase = getTarifaMensual();
        double costoVigilantes = vigilantesAsignados.size() * 500000;
        return costoBase + costoVigilantes;
    }

    public void asignarVigilante(Vigilante vigilante) {
        vigilantesAsignados.add(vigilante);
        asignarEmpleado(vigilante);
    }

    public List<Vigilante> getVigilantesAsignados() {
        return vigilantesAsignados;
    }
}
