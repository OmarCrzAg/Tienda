package administrador;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import adaptadores.AdaptadorVentas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import recursos.CodRep;

@SuppressWarnings("exports")
public class TablaVentasController extends CodRep implements Initializable {

	@FXML
	TableView<AdaptadorVentas> tblVentasA;

	@FXML
	TableColumn<AdaptadorVentas, Integer> colId;
	@FXML
	TableColumn<AdaptadorVentas, String> colUser;
	@FXML
	TableColumn<AdaptadorVentas, Double> colTotal;
	@FXML
	TableColumn<AdaptadorVentas, Date> colFecha;
	@FXML
	TableColumn<AdaptadorVentas, Time> colHora;

	@FXML
	Button bntRegresar;
	@FXML
	Button btnBuscarVen;

	@FXML
	ComboBox<String> idCBV;

	@FXML
	Label lblUserA;
	@FXML
	Label lblUserCirA;

	@FXML
	TextField txtBuscarV;

	private ObservableList<String> itemsTV;
	private ObservableList<AdaptadorVentas> listaV;

	protected TablaVentasController(Scene scene) {
		// super(scene);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		ventasComboBox(idCBV);
		Connection cnx;

		try {

			lblUserA.setText(getUsuario());
			lblUserCirA.setText(String.valueOf(getUsuario().charAt(0)));

			cnx = conectar();

			String query = "SELECT * FROM ventas";
			Statement cmd = cnx.createStatement();
			ResultSet res = cmd.executeQuery(query);

			listaV = FXCollections.observableArrayList();

			while (res.next()) {

				listaV.add(new AdaptadorVentas(res.getInt("id"), res.getString("usuario"), res.getDouble("total"),
						res.getDate("fecha"), res.getTime("hora")));
			}

			cnx.close();

		} catch (SQLException e) {

			e.getMessage();
		}

		colId.setCellValueFactory(dato -> dato.getValue().getId().asObject());
		colUser.setCellValueFactory(dato -> dato.getValue().getUsuario());
		colTotal.setCellValueFactory(dato -> dato.getValue().getTotal().asObject());
		colFecha.setCellValueFactory(dato -> dato.getValue().getFecha());
		colHora.setCellValueFactory(dato -> dato.getValue().getHora());

		tblVentasA.setItems(listaV);

		tblVentasA.getSelectionModel().selectedItemProperty().addListener((Observable, oldValue, newValue) -> {
			try {
				tblVentasA_cellClick(newValue);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}

	public void btnBuscarVen_click() {

		String bus, par;
		java.sql.Date fechaB = null;
		java.util.Date date = null;

		Connection cnx = null;

		try {
			cnx = conectar();
			par = idCBV.getValue();
			bus = txtBuscarV.getText();
			DateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");

			if (par.equals("fecha")) {
				

				try {

					date = formatofecha.parse(bus);
					fechaB = new java.sql.Date(date.getTime());

				} catch (ParseException e) {
					
					alerta("INFORMATION", "Formato invalido de fecha", "", "El formato de fecha debe ser: yyyy-mm-dd");
				}

				String query = "SELECT * FROM ventas WHERE fecha = ?";
				ps = cnx.prepareStatement(query);
				ps.setDate(1, fechaB);
				rs = ps.executeQuery();

				listaV = FXCollections.observableArrayList();

				while (rs.next()) {
					listaV.add(new AdaptadorVentas(rs.getInt("id"), rs.getString("usuario"), rs.getDouble("total"),
							rs.getDate("fecha"), rs.getTime("hora")));
				}

				cnx.close();
			} else {

				String query = "SELECT * FROM ventas WHERE usuario	LIKE '%" + bus + "%'";
				ps = cnx.prepareStatement(query);
				rs = ps.executeQuery();

				listaV = FXCollections.observableArrayList();

				while (rs.next()) {
					listaV.add(new AdaptadorVentas(rs.getInt("id"), rs.getString("usuario"), rs.getDouble("total"),
							rs.getDate("fecha"), rs.getTime("hora")));

				}

				cnx.close();
			}

			colId.setCellValueFactory(dato -> dato.getValue().getId().asObject());
			colUser.setCellValueFactory(dato -> dato.getValue().getUsuario());
			colTotal.setCellValueFactory(dato -> dato.getValue().getTotal().asObject());
			colFecha.setCellValueFactory(dato -> dato.getValue().getFecha());
			colHora.setCellValueFactory(dato -> dato.getValue().getHora());

			tblVentasA.setItems(listaV);

			tblVentasA.getSelectionModel().selectedItemProperty().addListener((Observable, oldValue, newValue) -> {
				try {
					tblVentasA_cellClick(newValue);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		} catch (Exception ex) {
			alerta("ERROR", "Sin parametro a buscar", "", "Selecciona un parametro de coincidencias");
		}
	}

	public void tblVentasA_cellClick(AdaptadorVentas newValue) throws SQLException {

		String usuarioV = newValue.getUsuario().getValue().toString();
		Double totalV = Double.parseDouble(newValue.getId().getValue().toString());
		String fechaV = newValue.getFecha().getValue().toString();
		String horaV = newValue.getHora().getValue().toString();

		alerta("INFORMATION", "Detalles de venta", "",
				"\n" + "Empleado que realizo la venta: " + usuarioV + "\n" + "Valor total de venta: " + totalV + "\n"
						+ "Fecha de realizacion de venta: " + fechaV + "\n" + "Hora de realizacion de venta: " + horaV);

	}

	public void btnRegresar_click(ActionEvent e) {

		cancela(e);

	}

	public ComboBox<String> ventasComboBox(ComboBox<String> CBV) {
		if (CBV == null) {
			CBV = new ComboBox<String>();
		}

		itemsTV = FXCollections.observableArrayList();
		itemsTV.add("usuario");
		itemsTV.add("fecha");

		CBV.setItems(itemsTV);
		return CBV;
	}

	@Override
	protected boolean getResultado() {
		// TODO Auto-generated method stub
		return false;
	}

}
