package adaptadores;

import java.sql.Date;
import java.sql.Time;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

@SuppressWarnings("exports")
public class AdaptadorVentas {

	private SimpleIntegerProperty id;
	private SimpleStringProperty usuario;
	private SimpleDoubleProperty total;
	private SimpleObjectProperty<Date> fecha;
	private SimpleObjectProperty<Time> hora;

	public SimpleIntegerProperty getId() {
		return id;
	}

	public void setId(SimpleIntegerProperty id) {
		this.id = id;
	}

	public SimpleStringProperty getUsuario() {
		return usuario;
	}

	public void setUsuario(SimpleStringProperty usuario) {
		this.usuario = usuario;
	}

	public SimpleDoubleProperty getTotal() {
		return total;
	}

	public void setTotal(SimpleDoubleProperty total) {
		this.total = total;
	}

	public SimpleObjectProperty<Date> getFecha() {
		return fecha;
	}

	public void setFecha(SimpleObjectProperty<Date> fecha) {
		this.fecha = fecha;
	}

	public SimpleObjectProperty<Time> getHora() {
		return hora;
	}

	public void setHora(SimpleObjectProperty<Time> hora) {
		this.hora = hora;
	}

	public AdaptadorVentas(int id, String usuario, Double total, Date fecha, Time hora) {

		this.id = new SimpleIntegerProperty(id);
		this.usuario = new SimpleStringProperty(usuario);
		this.total = new SimpleDoubleProperty(total);
		this.fecha = new SimpleObjectProperty<Date>(fecha);
		this.hora = new SimpleObjectProperty<Time>(hora);
	}

}
