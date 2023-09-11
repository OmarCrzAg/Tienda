package administrador;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import adaptadores.AdaptadorEmpleado;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Window;
import recursos.CodRep;

@SuppressWarnings("exports")
public class TablaEmpleadosController extends CodRep implements Initializable {

	@FXML
	TableView<AdaptadorEmpleado> tblEmpleados;
	@FXML
	TableColumn<AdaptadorEmpleado, Integer> colId;
	@FXML
	TableColumn<AdaptadorEmpleado, String> colEmpleados;
	@FXML
	TableColumn<AdaptadorEmpleado, String> colUser;
	@FXML
	TableColumn<AdaptadorEmpleado, Boolean> colAdmin;

	@FXML
	Label lblUserA;

	@FXML
	Label lblUserCirA;

	@FXML
	TextField txtBuscarE;

	@FXML
	Button btnBuscarEmp;
	@FXML
	Button btnRegresar;

	@FXML
	ComboBox<String> idCB;

	PreparedStatement ps;
	ResultSet rs;

	private ObservableList<AdaptadorEmpleado> listaE;

	protected TablaEmpleadosController(Scene scene) {
		// super(scene);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		labelsUsuario(lblUserA, lblUserCirA);

		String queryE = "SELECT * FROM empleados";

		TablaE(queryE, colId, colEmpleados, colUser, colAdmin, tblEmpleados, listaE);

		tblEmpleados.getSelectionModel().selectedItemProperty().addListener((Observable, oldValue, newValue) -> {
			try {
				tblEmpl_cellClick(newValue);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		);

	}

	public void btnBuscarEmp_click() {

		String bus;

		bus = txtBuscarE.getText();

		String queryBE = "SELECT * FROM empleados WHERE nombre LIKE '%" + bus + "%' OR usuario LIKE '%" + bus + "%'";

		TablaE(queryBE, colId, colEmpleados, colUser, colAdmin, tblEmpleados, listaE);

		tblEmpleados.getSelectionModel().selectedItemProperty().addListener((Observable, oldValue, newValue) -> {
			try {
				tblEmpl_cellClick(newValue);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		);
	}

	public void tblEmpl_cellClick(AdaptadorEmpleado reg) throws SQLException {

		if (reg != null) {
			String nombre = reg.getNombre().getValue().toString();
			int idE = Integer.parseInt(reg.getId().getValue().toString());
			String userB = reg.getUser().getValue().toString();
			Connection cnx = null;

			Alert alert = new Alert(AlertType.valueOf("CONFIRMATION"));
			alert.setTitle("Administrar Empleado");
			alert.setHeaderText(nombre);
			alert.setContentText("Desea borrar al empleado?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {

				try {

					cnx = conectar();
					String queryBE = "DELETE FROM empleados WHERE id = " + idE;
					ps = cnx.prepareStatement(queryBE);

					ps.executeUpdate();

					String queryBTC = "DROP TABLE carrito" + userB;
					ps = cnx.prepareStatement(queryBTC);

					ps.executeUpdate();
					
					alerta("INFORMATION", "Empleado eliminado", "", "El perfil del empleado se borro incluyendo su carrito");

				} catch (Exception e1) {
					System.err.println(e1);
				}

			}

		}
	}

	public void btnAgEmp_click(ActionEvent event) throws IOException {

		AgregarEController controlador = new AgregarEController(null);
		Window propieario = ((Node) event.getSource()).getScene().getWindow();

		if (this.MuestraFormularioModal(controlador, "/interfaces/AgregarE.fxml", propieario, "Nuevo empleado",
				"/recursos/AgregarE.jpg")) {

			String bus = txtBuscarE.getText();

			String queryBE = "SELECT * FROM empleados WHERE nombre LIKE '%" + bus + "%' OR usuario LIKE '%" + bus
					+ "%'";

			TablaE(queryBE, colId, colEmpleados, colUser, colAdmin, tblEmpleados, listaE);

		}

	}

	public void btnRegresar_click(ActionEvent e) {

		cancela(e);
	}

	@Override
	protected boolean getResultado() {
		// TODO Auto-generated method stub
		return false;
	}

}
