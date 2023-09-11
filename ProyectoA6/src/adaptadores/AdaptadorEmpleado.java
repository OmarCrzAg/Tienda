package adaptadores;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("exports")

public class AdaptadorEmpleado {

	private IntegerProperty id;
	private StringProperty nombre;
	private StringProperty user;
	private BooleanProperty administrador;

	public IntegerProperty getId() {
		return id;
	}

	public void setId(IntegerProperty id) {
		this.id = id;
	}

	public StringProperty getNombre() {
		return nombre;
	}

	public void setNombre(StringProperty nombre) {
		this.nombre = nombre;
	}

	public StringProperty getUser() {
		return user;
	}

	public void setUser(StringProperty user) {
		this.user = user;
	}

	public BooleanProperty getAdministrador() {
		return administrador;
	}

	public void setAdministrador(BooleanProperty administrador) {
		this.administrador = administrador;
	}

	public AdaptadorEmpleado(int id, String nombre, String user, Boolean administrador) {

		this.id = new SimpleIntegerProperty(id);
		this.nombre = new SimpleStringProperty(nombre);
		this.user = new SimpleStringProperty(user);
		this.administrador = new SimpleBooleanProperty(administrador);

	}

}
