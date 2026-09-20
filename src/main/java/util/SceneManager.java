package util;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class SceneManager {

    private static Stage stage;
    private static final Stack<String> history = new Stack<>();
    private static String currentScene;

    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    public static void switchScene(String fxmlFile) {
        try {
            if (currentScene != null) {
                history.push(currentScene);
            }

            loadScene(fxmlFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void goBack() {
        if (history.isEmpty()) {
            return;
        }

        try {
            loadScene(history.pop());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void goHome() {
        history.clear();

        try {
            loadScene("Main.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadScene(String fxmlFile) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                SceneManager.class.getResource("/view/" + fxmlFile)
        );

        Parent root = loader.load();

        Scene scene = new Scene(root);

        scene.getStylesheets().add(
                SceneManager.class.getResource("/css/style.css").toExternalForm()
        );

        currentScene = fxmlFile;

        stage.setMaximized(false);
        stage.setScene(scene);
        stage.setWidth(1200);
        stage.setHeight(750);
        stage.centerOnScreen();
        stage.show();

        Platform.runLater(() -> stage.setMaximized(true));
    }
}