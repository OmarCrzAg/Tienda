package administrador;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Window;
import recursos.CodRep;

@SuppressWarnings("exports")
public class OpAdminController extends CodRep {

	@FXML
	Button btnOpEmpleados;
	@FXML
	Button btnOpProductos;
	@FXML
	Button btnOpVentas;

	public OpAdminController(Scene scene) {
		// super(scene);
	}

	public void btnOpEmpleados_click(ActionEvent event) throws IOException {

		TablaEmpleadosController controlador = new TablaEmpleadosController(null);
		Window propieario = ((Node) event.getSource()).getScene().getWindow();

		MuestraFormularioModal(controlador, "/interfaces/TablaEmpleados.fxml", propieario, "Administrador",
				"/recursos/Empleados.jpg");

	}

	public void btnOpProductos_click(ActionEvent event) throws IOException {

		TablaProductosController controlador = new TablaProductosController(null);
		Window propieario = ((Node) event.getSource()).getScene().getWindow();

		MuestraFormularioModal(controlador, "/interfaces/TablaProductos.fxml", propieario, "Administrador",
				"/recursos/Inventario.jpg");

	}

	public void btnOpVentas_click(ActionEvent event) throws IOException {

		TablaVentasController controlador = new TablaVentasController(null);
		Window propieario = ((Node) event.getSource()).getScene().getWindow();

		MuestraFormularioModal(controlador, "/interfaces/TablaVentas.fxml", propieario, "Administrador", "/recursos/Ventas.jpg");

	}

	public void btnReg_click(ActionEvent e) {

		cancela(e);

	}

	@Override
	protected boolean getResultado() {
		// TODO Auto-generated method stub
		return false;
	}

}
