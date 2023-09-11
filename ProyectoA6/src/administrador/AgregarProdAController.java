package administrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import recursos.CodRep;

@SuppressWarnings("exports")
public class AgregarProdAController extends CodRep {

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

	PreparedStatement ps;

	boolean resultado = false;

	Connection cnx = null;

	protected AgregarProdAController(Scene scene) {
		// super(scene);
	}

	public void btnGuadarPA_click(ActionEvent e) throws SQLException {

		boolean flagBP = false;

		try {
			cnx = conectar();

			String queryBN = "SELECT * FROM producto WHERE Nombre = ? and Provedor = ?";
			ps = cnx.prepareStatement(queryBN);
			ps.setString(1, txtNombre.getText());
			ps.setString(2, txtProvedor.getText());

			rs = ps.executeQuery();

			if (rs.next()) {
				flagBP = true;
			}

			if (flagBP == true) {

				alerta("WARNING", "Cuidado", "Detalles del registro", "El producto ya existe en la base de datos");

				cancela(e);

			} else {

				String queryIP = ("INSERT INTO producto (Nombre, Provedor, Precio, Existencias) VALUES(?,?,?,?)");
				ps = cnx.prepareStatement(queryIP);

				ps.setString(1, txtNombre.getText());
				ps.setString(2, txtProvedor.getText());
				ps.setDouble(3, Double.valueOf(txtPrecio.getText()));
				ps.setInt(4, Integer.valueOf(txtCantidad.getText()));

				int filasInsertadas = 0;
				filasInsertadas = ps.executeUpdate();

				if (filasInsertadas > 0) {

					alerta("INFORMATION", "Informacion", "Detalles del registro",
							"Ese agrego correctamente el producto");
					resultado = true;

					cancela(e);

				}

				cnx.close();
			}
		} catch (Exception ex) {

			alerta("ERROR", "Error", "Detalles del registro", "Error al agregar el producto, valores invalidos");

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
