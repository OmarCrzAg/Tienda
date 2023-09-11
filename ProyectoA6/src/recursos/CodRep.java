package recursos;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import adaptadores.AdaptadorCarrito;
import adaptadores.AdaptadorEmpleado;
import adaptadores.AdaptadorProducto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

@SuppressWarnings("exports")
public abstract class CodRep implements Initializable {

	@FXML
	protected static TextField txtId;
	@FXML
	protected static TextField txtNombre;

	protected static PreparedStatement ps;
	protected static ResultSet rs;

	protected static String user;
	protected static String nombre;
	protected static int idA;
	protected static String provedor;
	protected static double precio;

	private static ObservableList<String> itemsP;
	ObservableList<AdaptadorProducto> listP;
	ObservableList<AdaptadorCarrito> listC;
	ObservableList<AdaptadorEmpleado> listE;

	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	protected abstract boolean getResultado();

	protected boolean MuestraFormularioModal(CodRep controlador, String fxml, Window propietario, String titulo,
			String imagen) throws IOException {
		

		FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fxml));
		loader.setController(controlador);
		Stage stage = new Stage();
		stage.setTitle(titulo);
		stage.getIcons().add(new Image(this.getClass().getResourceAsStream(imagen)));
		stage.setScene(new Scene(loader.load()));
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(propietario);
		stage.showAndWait();

		return controlador.getResultado();
	}

	public static Connection conectar() throws SQLException {

		Connection cnx = null;
		String cadena = "jdbc:postgresql://localhost:5432/POO2?useSSL=false";
		String drv = "org.postgresql.Driver";
		try {
			Class.forName(drv);
			cnx = (Connection) DriverManager.getConnection(cadena, "postgres", "omar2001");
			// System.out.println("Conexion exitosa!");

		} catch (ClassNotFoundException ex) {
			System.err.println("Error1: " + ex.getMessage());
			return null;
		}
		return cnx;

	}

	public void labelsUsuario(Label lblU, Label lblC) {

		try {
			lblU.setText(getUsuario());
			lblC.setText(String.valueOf(getUsuario().charAt(0)));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void cancela(ActionEvent e) {

		Node source = (Node) e.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}

	public void tablaProductos(String query, TableColumn<AdaptadorProducto, Integer> col1,
			TableColumn<AdaptadorProducto, String> col2, TableColumn<AdaptadorProducto, String> col3,
			TableColumn<AdaptadorProducto, Double> col4, TableColumn<AdaptadorProducto, Integer> col5,
			TableView<AdaptadorProducto> tabla) {

		Connection cnx = null;

		try {

			cnx = conectar();

			ps = cnx.prepareStatement(query);

			rs = ps.executeQuery();

			listP = FXCollections.observableArrayList();

			while (rs.next()) {

				listP.add(new AdaptadorProducto(rs.getInt("id"), rs.getString("nombre"), rs.getString("provedor"),
						rs.getDouble("precio"), rs.getInt("existencias")));
			}

			rs.close();
			cnx.close();
		} catch (Exception ex) {
			System.err.println("Error2: " + ex.getMessage());
		}

		col1.setCellValueFactory(dato -> dato.getValue().getId().asObject());
		col2.setCellValueFactory(dato -> dato.getValue().getNombre());
		col3.setCellValueFactory(dato -> dato.getValue().getProvedor());
		col4.setCellValueFactory(dato -> dato.getValue().getPrecio().asObject());
		col5.setCellValueFactory(dato -> dato.getValue().getExistencia().asObject());

		tabla.setItems(listP);

	}

	public void tablaCarrito(String user, String query, TableColumn<AdaptadorCarrito, Integer> col1,
			TableColumn<AdaptadorCarrito, String> col2, TableColumn<AdaptadorCarrito, Double> col3,
			TableColumn<AdaptadorCarrito, Integer> col4, TableView<AdaptadorCarrito> tabla) {

		Connection cnx = null;

		try {

			cnx = conectar();

			ps = cnx.prepareStatement(query);

			rs = ps.executeQuery();

			listC = FXCollections.observableArrayList();

			while (rs.next()) {

				listC.add(new AdaptadorCarrito(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("precio"),
						rs.getInt("existencia")));
			}

			rs.close();
			cnx.close();
		} catch (Exception ex) {
			System.err.println("Error2: " + ex.getMessage());
		}

		col1.setCellValueFactory(dato -> dato.getValue().getId().asObject());
		col2.setCellValueFactory(dato -> dato.getValue().getNombre());
		col3.setCellValueFactory(dato -> dato.getValue().getPrecio().asObject());
		col4.setCellValueFactory(dato -> dato.getValue().getExistencia().asObject());
		tabla.setItems(listC);

	}

	public void TablaE(String query, TableColumn<AdaptadorEmpleado, Integer> col1,
			TableColumn<AdaptadorEmpleado, String> col2, TableColumn<AdaptadorEmpleado, String> col3,
			TableColumn<AdaptadorEmpleado, Boolean> col4, TableView<AdaptadorEmpleado> tabla,
			ObservableList<AdaptadorEmpleado> list) {

		Connection cnx = null;

		try {

			cnx = conectar();

			ps = cnx.prepareStatement(query);

			rs = ps.executeQuery();

			list = FXCollections.observableArrayList();

			while (rs.next()) {

				list.add(new AdaptadorEmpleado(rs.getInt("id"), rs.getString("nombre"), rs.getString("usuario"),
						rs.getBoolean("administrador")));

			}

			rs.close();
			cnx.close();
		} catch (Exception ex) {
			System.err.println("Error2: " + ex.getMessage());
		}

		col1.setCellValueFactory(dato -> dato.getValue().getId().asObject());
		col2.setCellValueFactory(dato -> dato.getValue().getNombre());
		col3.setCellValueFactory(dato -> dato.getValue().getUser());
		col4.setCellValueFactory(dato -> dato.getValue().getAdministrador());

		tabla.setItems(list);
	}

	public void alerta(String Tipo, String Titulo, String Header, String Contenido) {

		Alert alert = new Alert(AlertType.valueOf(Tipo));
		alert.setTitle(Titulo);
		alert.setHeaderText(Header);
		alert.setContentText(Contenido);
		alert.showAndWait();

	}

	public ComboBox<String> ComboBoxProductos(ComboBox<String> CB) {
		if (CB == null) {
			CB = new ComboBox<>();
		}

		itemsP = FXCollections.observableArrayList();
		itemsP.add("nombre");
		itemsP.add("provedor");

		CB.setItems(itemsP);
		return CB;
	}

	public static String getUsuario() throws SQLException {

		return user;

	}

	public void agregarCarr(int id) throws SQLException {

		Connection cnx = null;

		try {
			cnx = conectar();

			String queryAC = "INSERT INTO carrito" + user
					+ " (id, nombre, precio, existencia) SELECT id, nombre, precio, existencias FROM producto WHERE id = "
					+ id;
			ps = cnx.prepareStatement(queryAC);

			ps.executeUpdate();

		} catch (Exception e1) {
			System.err.println(e1);
		}

	}

	public void eliminarCarr(int id) throws SQLException {

		Connection cnx = null;

		try {

			cnx = conectar();
			String queryEC = "DELETE FROM carrito" + user + " WHERE id = " + id;
			ps = cnx.prepareStatement(queryEC);

			ps.executeUpdate();

		} catch (Exception e1) {
			System.err.println(e1);
		}

	}

	public Double sumarCarr() throws SQLException {

		Connection cnx = null;
		Double suma = null;

		try {
			cnx = conectar();

			String querySC = "SELECT SUM(precio) AS total FROM carrito" + user;
			ps = cnx.prepareStatement(querySC);

			rs = ps.executeQuery();

			if (rs.next()) {
				suma = rs.getDouble("total");
			}

		} catch (Exception e1) {
			System.err.println(e1);
		}
		return suma;

	}

	public void borrarCarr() {

		Connection cnx = null;

		try {

			cnx = conectar();
			String queryBC = "DELETE FROM carrito" + user;
			ps = cnx.prepareStatement(queryBC);

			ps.executeUpdate();

		} catch (Exception e1) {
			System.err.println(e1);
		}
	}

	public void guardarVenta(Double total) {

		Connection cnx = null;

		try {

			cnx = conectar();
			String queryGV = "INSERT INTO ventas (usuario, total, fecha, hora) VALUES (?, ?, CURRENT_DATE, CURRENT_TIME)";
			PreparedStatement ps = cnx.prepareStatement(queryGV);

			ps.setString(1, user);
			ps.setDouble(2, total);

			ps.executeUpdate();

		} catch (Exception e1) {
			System.err.println(e1);
		}

	}

	public void restarExistencias(int idProducto) {
		Connection cnx = null;

		try {

			cnx = conectar();
			String query = "UPDATE producto SET existencias = existencias - 1 WHERE id = ?";
			PreparedStatement ps = cnx.prepareStatement(query);

			ps.setInt(1, idProducto);

			ps.executeUpdate();

		} catch (Exception e1) {
			System.err.println(e1);
		}
	}

	public void sumarExistencias(int idProducto) {
		Connection cnx = null;

		try {

			cnx = conectar();
			String query = "UPDATE producto SET existencias = existencias + 1 WHERE id = ?";
			PreparedStatement ps = cnx.prepareStatement(query);

			ps.setInt(1, idProducto);

			ps.executeUpdate();

		} catch (Exception e1) {
			System.err.println(e1);
		}
	}

}
