
package inicio;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(@SuppressWarnings("exports") Stage primaryStage) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/Login.fxml"));
			LoginController controlador = new LoginController(null);
			loader.setController(controlador);
			Stage Login = new Stage();
			Login.setTitle("Inicio de sesion");
			Login.getIcons().add(new Image(this.getClass().getResourceAsStream("/recursos/Login.jpg")));
			Login.setScene(new Scene(loader.load()));
			Login.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
