module ProyectoA4 {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.base;
	requires javafx.graphics;
	
	exports inicio;
	exports administrador;
	exports empleado;
	exports adaptadores;
	exports interfaces;
	exports recursos;
	
	opens inicio to javafx.graphics, javafx.fxml;
	opens empleado to javafx.fxml;
	opens administrador to javafx.fxml;
}
