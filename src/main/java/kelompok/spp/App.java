package kelompok.spp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.text.Font;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins/Poppins-Regular.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins/Poppins-SemiBold.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins/Poppins-SemiBoldItalic.ttf"), 12);
        
        scene = new Scene((Parent) loadFXML("auth").load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(
                new Image(getClass().getResourceAsStream("/assets/icon.jpg"))
        );
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot((Parent) loadFXML(fxml).load());
    }

    public static FXMLLoader loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader;
    }

    public static void main(String[] args) {
        launch();
    }

}
