package controller;

import javafx.event.ActionEvent;
import util.SceneManager;

public class MainController {

    public void openBrowsePage(ActionEvent event) {

        SceneManager.switchScene("BrowseCakes.fxml");

    }

    public void openCustomCakePage(ActionEvent event) {

        SceneManager.switchScene("CustomCake.fxml");

    }

    public void openTrackOrderPage(ActionEvent event) {

        SceneManager.switchScene("TrackOrder.fxml");

    }

    public void openStaffDashboard(ActionEvent event){

        SceneManager.switchScene("StaffDashboard.fxml");

    }

}