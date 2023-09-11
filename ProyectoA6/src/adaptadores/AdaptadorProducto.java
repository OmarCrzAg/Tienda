package adaptadores;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("exports")
public class AdaptadorProducto {

	private IntegerProperty id;
	private StringProperty nombre;
	private StringProperty provedor;
	private DoubleProperty precio;
	private IntegerProperty existencia;

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

	public StringProperty getProvedor() {
		return provedor;
	}

	public void setProvedor(StringProperty provedor) {
		this.provedor = provedor;
	}

	public DoubleProperty getPrecio() {
		return precio;
	}

	public void setPrecio(DoubleProperty precio) {
		this.precio = precio;
	}

	public IntegerProperty getExistencia() {
		return existencia;
	}

	public void setExistencia(IntegerProperty existencia) {
		this.existencia = existencia;
	}

	public AdaptadorProducto(int id, String nombre, String provedor, double precio, int existencia) {
		this.id = new SimpleIntegerProperty(id);
		this.nombre = new SimpleStringProperty(nombre);
		this.provedor = new SimpleStringProperty(provedor);
		this.precio = new SimpleDoubleProperty(precio);
		this.existencia = new SimpleIntegerProperty(existencia);
	}
}