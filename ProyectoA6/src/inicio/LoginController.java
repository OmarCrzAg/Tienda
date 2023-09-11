package inicio;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import administrador.OpAdminController;
import empleado.BusquedaPController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import recursos.CodRep;

public class LoginController extends CodRep {

	@FXML
	TextField txtUsuario;
	@FXML
	PasswordField psContraseña;

	@FXML
	Button btnGuardarLog;
	@FXML
	Button btnCancelaLog;

	PreparedStatement ps;
	ResultSet rs;

	Connection cnx = null;

	protected LoginController(Scene scene) {
		// super(scene);
	}

	public void btnLoginAcep_click(@SuppressWarnings("exports") ActionEvent event) throws IOException, SQLException {

		Boolean flagBU = false, flagUA = false;

		try {
			cnx = conectar();

			String queryBN = "SELECT * FROM empleados WHERE usuario = ? and contrasena = ?";
			ps = cnx.prepareStatement(queryBN);
			ps.setString(1, txtUsuario.getText());
			ps.setString(2, psContraseña.getText());

			user = txtUsuario.getText();

			rs = ps.executeQuery();

			if (rs.next()) {
				flagBU = true;
			}

			if (flagBU == true) {

				String queryBU = "SELECT * FROM empleados WHERE usuario = ? and contrasena = ?";
				ps = cnx.prepareStatement(queryBU);

				ps.setString(1, txtUsuario.getText());
				ps.setString(2, psContraseña.getText());

				rs = ps.executeQuery();

				if (rs.next()) {

					flagUA = rs.getBoolean("administrador");

				}

				if (flagUA == true) {

					OpAdminController controlador = new OpAdminController(null);
					Window propieario = ((Node) event.getSource()).getScene().getWindow();

					MuestraFormularioModal(controlador, "/interfaces/OpAdmin.fxml", propieario, "Administrador","/recursos/Administrado.jpg");

					txtUsuario.clear();

					psContraseña.clear();

				} else {

					BusquedaPController controlador = new BusquedaPController(null);
					Window propieario = ((Node) event.getSource()).getScene().getWindow();

					MuestraFormularioModal(controlador, "/interfaces/BusquedaP.fxml", propieario, "Inventario",
							"/recursos/Logo.jpg");

					txtUsuario.clear();

					psContraseña.clear();

				}

			} else {

				alerta("ERROR", "Error de datos", "", "Ususario o contraseña invalidos");

			}

			cnx.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}


	public void btnLoginCancela_click() {

		System.exit(0);

	}

	@Override
	protected boolean getResultado() {
		// TODO Auto-generated method stub
		return false;
	}

}
