package application;

import javafx.application.Application;
import javafx.stage.Stage;
import util.SceneManager;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        SceneManager.setStage(stage);

        SceneManager.switchScene("Main.fxml");

        stage.setTitle("Cake Shop");

    }

    public static void main(String[] args) {
        launch(args);
    }
}