package co.edu.uniquindio.poo.empresaseguridad.viewController;

import co.edu.uniquindio.poo.empresaseguridad.model.Empleado;
import co.edu.uniquindio.poo.empresaseguridad.model.Equipo;

import java.util.List;

public class AsignarPersonalViewController {

    // Atributos del controlador
    private List<Empleado> listaPersonal;
    private Equipo equipoSeleccionado;

    public AsignarPersonalViewController(Equipo equipoSeleccionado) {
        this.equipoSeleccionado = equipoSeleccionado;
        this.listaPersonal = cargarPersonalDisponible();
    }

    private List<Empleado> cargarPersonalDisponible() {
        // lógica para obtener personal no asignado o elegible
        return Empleado.obtenerEmpleadoDisponible();
    }

    public void mostrarVista() {
        // lógica para mostrar UI con lista de personal y permitir seleccionar
    }

    public void asignarPersonal(List<Empleado> seleccionados) {
        for (Empleado p : seleccionados) {
            equipoSeleccionado.asignarPersonal(p);
            PersonalDAO.actualizarAsignacion(p, equipoSeleccionado);
        }
        // tal vez refrescar lista, cerrar vista, notificar éxito
    }

    public List<Empleado> getListaPersonal() {
        return listaPersonal;
    }

    public void setListaPersonal(List<Empleado> listaPersonal) {
        this.listaPersonal = listaPersonal;
    }

    public Equipo getEquipoSeleccionado() {
        return equipoSeleccionado;
    }

    public void setEquipoSeleccionado(Equipo equipoSeleccionado) {
        this.equipoSeleccionado = equipoSeleccionado;
    }
}
