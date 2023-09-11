package empleado;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import adaptadores.AdaptadorProducto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Window;
import recursos.CodRep;


public class BusquedaPController extends CodRep implements Initializable {

	@FXML
	TableView<AdaptadorProducto> tblBusquedaP;
	@FXML
	TableColumn<AdaptadorProducto, Integer> colId;
	@FXML
	TableColumn<AdaptadorProducto, String> colNombre;
	@FXML
	TableColumn<AdaptadorProducto, String> colProvedor;
	@FXML
	TableColumn<AdaptadorProducto, Integer> colExistencia;
	@FXML
	TableColumn<AdaptadorProducto, Double> colPrecio;

	@FXML
	Label lblUser;

	@FXML
	Label lblUserCir;

	@FXML
	TextField txtBuscarP;

	@FXML
	ComboBox<String> idCB;

	PreparedStatement ps;
	ResultSet rs;

	@SuppressWarnings("exports")
	public BusquedaPController(Scene scene) {
		super();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		ComboBoxProductos(idCB);
		labelsUsuario(lblUser, lblUserCir);

		String queryP = "SELECT * FROM producto";

		tablaProductos(queryP, colId, colNombre, colProvedor, colPrecio, colExistencia, tblBusquedaP);

		tblBusquedaP.getSelectionModel().selectedItemProperty().addListener((Observable, oldValue, newValue) -> {
			try {
				tblProd_cellClick(newValue);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}

	public void btnBuscarP_click() {

		String bus, par;
		par = idCB.getValue();
		bus = txtBuscarP.getText();

		if (par == null) {

			alerta("ERROR", "Sin parametro", "", "Elije un parametro de busqueda");

		} else {

			String queryBP = "SELECT * FROM producto WHERE " + par + " LIKE '%" + bus + "%'";

			tablaProductos(queryBP, colId, colNombre, colProvedor, colPrecio, colExistencia, tblBusquedaP);

			tblBusquedaP.getSelectionModel().selectedItemProperty().addListener((Observable, oldValue, newValue) -> {
				try {
					tblProd_cellClick(newValue);
				} catch (SQLException ex) {
					// TODOAuto-generated catch block
					ex.printStackTrace();
				}
			});

		}

	}

	public void tblProd_cellClick(AdaptadorProducto reg) throws SQLException {

		if (reg != null) {

			String producto = reg.getNombre().getValue();
			int idp = Integer.parseInt(reg.getId().getValue().toString());
			int existencias = Integer.parseInt(reg.getExistencia().getValue().toString());

			Alert alert = new Alert(AlertType.valueOf("CONFIRMATION"));
			alert.setTitle("Agregar al carrito");
			alert.setHeaderText(producto);
			alert.setContentText("Desea agregar el producto al carrito?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {

				if (existencias > 0) {

					restarExistencias(idp);
					agregarCarr(idp);
					alerta("INFORMATION", "PRODUCTO AGREGADO", "", "El producto se agrego correctamente al carrito");

				} else {

					alerta("ERROR", "SIN EXISTENCIAS", "", "El producto no cuenta con existencias suficientes");

				}

			}

		}
	}

	@SuppressWarnings("exports")
	public void btnCarrito_click(ActionEvent event) throws IOException {

		CarritoController controlador = new CarritoController(null);
		Window propieario = ((Node) event.getSource()).getScene().getWindow();

		if (this.MuestraFormularioModal(controlador, "/interfaces/Carrito.fxml", propieario, "Carrito de compra",
				"/recursos/Carrito.jpg")) {

			String bus, par;
			par = idCB.getValue();
			bus = txtBuscarP.getText();

			if (par == null) {

				par = "nombre";

			} else {

				String queryBP = "SELECT * FROM producto WHERE " + par + " LIKE '%" + bus + "%'";

				tablaProductos(queryBP, colId, colNombre, colProvedor, colPrecio, colExistencia, tblBusquedaP);
			}
		}

	}

	@Override
	protected boolean getResultado() {
		// TODO Auto-generated method stub
		return false;
	}

}