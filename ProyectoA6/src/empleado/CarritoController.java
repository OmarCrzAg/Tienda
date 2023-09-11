package empleado;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import adaptadores.AdaptadorCarrito;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import recursos.CodRep;

public class CarritoController extends CodRep implements Initializable {

	@FXML
	TableView<AdaptadorCarrito> tblCar;
	@FXML
	TableColumn<AdaptadorCarrito, Integer> colId;
	@FXML
	TableColumn<AdaptadorCarrito, String> colNombre;
	@FXML
	TableColumn<AdaptadorCarrito, Double> colPrecio;
	@FXML
	TableColumn<AdaptadorCarrito, Integer> colExistencia;

	private boolean resultado = false;

	protected CarritoController(Scene scene) {
		// super(scene);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {
			user = getUsuario();
			String queryC = "SELECT * FROM carrito" + user;

			tablaCarrito(user, queryC, colId, colNombre, colPrecio, colExistencia, tblCar);

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		tblCar.getSelectionModel().selectedItemProperty().addListener((Observable, oldValue, newValue) -> {
			try {
				tblCar_cellClick(newValue);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});
	}

	public void tblCar_cellClick(AdaptadorCarrito reg) throws SQLException {

		if (reg != null) {

			String producto = reg.getNombre().getValue().toString();
			int idpe = Integer.parseInt(reg.getId().getValue().toString());

			Alert alert = new Alert(AlertType.valueOf("CONFIRMATION"));
			alert.setTitle("Eliminar al carrito");
			alert.setHeaderText(producto);
			alert.setContentText("Desea eliminar el producto al carrito?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {

				sumarExistencias(idpe);
				eliminarCarr(idpe);
				alerta("INFORMATION", "PRODUCTO ELIMINADO", "", "El producto se elimino correctamente al carrito");

			}
		}
	}

	public void btnCarPa_click() throws SQLException {

		resultado = false;

		if (sumarCarr() > 0) {

			Alert alert = new Alert(AlertType.valueOf("CONFIRMATION"));
			alert.setTitle("Pago");
			alert.setHeaderText(null);
			alert.setContentText("El total a pagar es de $" + sumarCarr());
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				guardarVenta(sumarCarr());
				borrarCarr();
				resultado = true;
			}
		} else {
			alerta("INFORMATION", "Carrito vacio", "", "No hay productos en el carrito");
		}
	}

	@SuppressWarnings("exports")
	@FXML
	public void btnCarCan_click(ActionEvent e) {

		cancela(e);
	}

	@Override
	protected boolean getResultado() {
		// TODO Auto-generated method stub
		return resultado;
	}

}