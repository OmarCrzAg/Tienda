package administrador;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import adaptadores.AdaptadorProducto;
import javafx.collections.ObservableList;
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
import javafx.scene.control.Button;
import javafx.stage.Window;
import recursos.CodRep;

@SuppressWarnings("exports")
public class TablaProductosController extends CodRep implements Initializable {

	@FXML
	TableView<AdaptadorProducto> tblBusquedaPA;
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
	Label lblUserA;

	@FXML
	Label lblUserCirA;

	@FXML
	TextField txtBuscarPA;

	@FXML
	Button btnAgProd;
	@FXML
	Button btnRegresar;
	@FXML
	Button btnBuscarProd;

	@FXML
	ComboBox<String> idCBA;

	PreparedStatement ps;
	ResultSet rs;

	public ObservableList<AdaptadorProducto> listaPA;

	protected TablaProductosController(Scene scene) {
		// super(scene);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		ComboBoxProductos(idCBA);
		labelsUsuario(lblUserA, lblUserCirA);

		String queryP = "SELECT * FROM producto";

		tablaProductos(queryP, colId, colNombre, colProvedor, colPrecio, colExistencia, tblBusquedaPA);

		tblBusquedaPA.getSelectionModel().selectedItemProperty().addListener((Observable, oldValue, newValue) -> {
			try {
				tblProdA_cellClick(newValue);

			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}

	public void btnBuscarProd_click() {

		String bus, par;
		par = idCBA.getValue();
		bus = txtBuscarPA.getText();

		if (par == null) {

			alerta("ERROR", "Sin parametro", "", "Elije un parametro de busqueda");

		} else {

			String queryBP = "SELECT * FROM producto WHERE " + par + " LIKE '%" + bus + "%'";

			tablaProductos(queryBP, colId, colNombre, colProvedor, colPrecio, colExistencia, tblBusquedaPA);

			tblBusquedaPA.getSelectionModel().selectedItemProperty().addListener((Observable, oldValue, newValue) -> {
				try {
					tblProdA_cellClick(newValue);
				} catch (SQLException ex) {
					// TODOAuto-generated catch block
					ex.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});

		}
	}

	public void tblProdA_cellClick(AdaptadorProducto reg) throws SQLException, IOException{

		if (reg != null) {
			String producto = reg.getNombre().getValue().toString();
			int idp = Integer.parseInt(reg.getId().getValue().toString());

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Editar producto");
			alert.setHeaderText(producto);
			alert.setContentText("Desea editar el producto?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {

				idA= idp;
				
				EditarProdAController controlador = new EditarProdAController(null);
				Window propietario = tblBusquedaPA.getScene().getWindow();

				MuestraFormularioModal(controlador, "/interfaces/EditarProdA.fxml", propietario, "Editar Productos",
						"/recursos/EditarP.jpg");
					
			}
		}
	}
		
					

	public void btnAgProd_click(ActionEvent event) throws IOException {

		AgregarProdAController controlador = new AgregarProdAController(null);
		Window propietario = ((Node) event.getSource()).getScene().getWindow();

		if (this.MuestraFormularioModal(controlador, "/interfaces/AgregarProdA.fxml", propietario, "Agregar Productos",
				"/recursos/AgregarP.jpg")) {

			String bus, par;
			par = idCBA.getValue();
			bus = txtBuscarPA.getText();

			if (par == null) {

				par = "nombre";

			} else {

				String queryBP = "SELECT * FROM producto WHERE " + par + " LIKE '%" + bus + "%'";

				tablaProductos(queryBP, colId, colNombre, colProvedor, colPrecio, colExistencia, tblBusquedaPA);

			}
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
