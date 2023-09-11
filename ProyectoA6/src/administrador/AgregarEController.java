package administrador;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import recursos.CodRep;

@SuppressWarnings("exports")
public class AgregarEController extends CodRep {

	@FXML
	Button btnGuardarEN;
	@FXML
	Button btnCancelaEN;

	@FXML
	TextField txtNombreEN;
	@FXML
	TextField txtUsuarioEN;

	@FXML
	PasswordField psContrasenaEN;
	@FXML
	PasswordField psContrasenaENC;

	@FXML
	CheckBox cbAdminEN;

	boolean resultado = false;

	protected AgregarEController(Scene scene) {
		// super(scene);
	}

	Connection cnx = null;

	public void btnGuardarEN_click(ActionEvent e) {

		int rs1 = 0;

//		System.out.println(psContrasenaEN.getText());
//		System.out.println(psContrasenaENC.getText());
//		System.out.println(cbAdminEN.isSelected());

		if (psContrasenaEN.getText().equals(psContrasenaENC.getText())) {

			try {

				cnx = conectar();

				String queryAU = "INSERT INTO empleados (nombre, usuario, contrasena, administrador) VALUES (?,?,?,?)";
				ps = cnx.prepareStatement(queryAU);

				ps.setString(1, txtNombreEN.getText());
				ps.setString(2, txtUsuarioEN.getText());
				ps.setString(3, psContrasenaEN.getText());
				ps.setBoolean(4, cbAdminEN.isSelected());

				rs1 = ps.executeUpdate();

				if (rs1 > 0) {

					alerta("INFORMATION", "Empleado agregado", "", "El empleado fue agregado correctamente");
					resultado = true;

				} else {

					alerta("ERROR", "Empleado no agregado", "", "El empleado no fue agregado");
				}

				if (cbAdminEN.isSelected() == false) {

					String queryCBDE = "CREATE TABLE public.carrito" + txtUsuarioEN.getText()
							+ "( id integer NOT NULL, " + "nombre character varying NOT NULL, "
							+ "precio real NOT NULL, " + "existencia integer NOT NULL)";

					Statement st = cnx.createStatement();
					st.execute(queryCBDE);

					alerta("INFORMATION", "Carrito de empleado", "", "El carrito se creó correctamente");

				}

			} catch (SQLException e1) {
				alerta("ERROR", "Carrito de empleado", "", "El carrito del empleado no fue creado");
				e1.printStackTrace();
			}

			cancela(e);

		} else {

			alerta("ERROR", "Contraseña incorrecta", "", "La contraseña no coincide");

		}
	}

	public void btnCancelaEN_click(ActionEvent e) {

		cancela(e);

	}

	@Override
	protected boolean getResultado() {
		// TODO Auto-generated method stub
		return resultado;
	}

}
