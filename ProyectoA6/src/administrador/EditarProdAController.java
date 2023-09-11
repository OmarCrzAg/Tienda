package administrador;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import recursos.CodRep;

@SuppressWarnings("exports")
public class EditarProdAController extends CodRep implements Initializable {

	@FXML
	Label lblId;
	@FXML
	Label lblNombre;
	@FXML
	Label lblProvedor;
	@FXML
	Label lblPrecio;
	@FXML
	Label lblCantidad;

	@FXML
	TextField txtId;
	@FXML
	TextField txtNombre;
	@FXML
	TextField txtProvedor;
	@FXML
	TextField txtPrecio;
	@FXML
	TextField txtCantidad;

	@FXML
	Button btnGuardar;
	@FXML
	Button btnCancela;

	boolean resultado = false;

	Connection cnx = null;

	protected EditarProdAController(Scene scene) {
		// super(scene);
	}

	public void initialize(URL arg0, ResourceBundle arg1) {

		Connection cnx = null;

		try {

			cnx = conectar();

			String queryBPA = "SELECT * FROM producto WHERE id = ?";
			PreparedStatement ps = cnx.prepareStatement(queryBPA);
			ps.setInt(1, idA);

			rs = ps.executeQuery();

			if (rs.next()) {

				txtId.setText(rs.getString("id"));
				txtNombre.setText(rs.getString("nombre"));
				txtProvedor.setText(rs.getString("provedor"));
				txtPrecio.setText(rs.getString("precio"));
				txtCantidad.setText(rs.getString("existencias"));

				cnx.close();

			}
		} catch (Exception e1) {
			System.err.println(e1);
		}
	}

	public void btnActualizar_click(ActionEvent e) {

		try {
			cnx = conectar();

			String queryAP = ("UPDATE producto SET nombre = ?, provedor = ?, precio = ?, existencias = ? WHERE id = ? ");
			PreparedStatement ps = cnx.prepareStatement(queryAP);

			ps.setString(1, txtNombre.getText());
			ps.setString(2, txtProvedor.getText());
			ps.setDouble(3, Double.valueOf(txtPrecio.getText()));
			ps.setInt(4, Integer.valueOf(txtCantidad.getText()));
			ps.setInt(5, Integer.valueOf(txtId.getText()));

			int res = ps.executeUpdate();

			if (res > 0) {

				alerta("INFORMATION", "Informacion", "Detalles del registro",
						"Ese actualizo correctamente el producto");

				resultado = true;

				cancela(e);

			} else {

				alerta("ERROR", "Error", "Detalles del registro", "Error al actualizar el producto, valores invalidos");

			}

			ps.close();
			cnx.close();
		} catch (Exception ex) {

			alerta("ERROR", "Error", "Detalles del registro", "Error al actualizar el producto, valores invalidos");

		}
	}

	public void btnCancela_click(ActionEvent e) {

		cancela(e);

	}

	@Override
	protected boolean getResultado() {
		// TODO Auto-generated method stub
		return resultado;
	}

}
