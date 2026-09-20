package controller;

import util.SceneManager;

public class StaffDashboardController {

    public void openAcceptOrders() {

        SceneManager.switchScene("AcceptOrders.fxml");

    }

    public void openUpdateOrders() {

        SceneManager.switchScene("UpdateOrders.fxml");

    }

    public void openManageCakes() {

        SceneManager.switchScene("ManageCakes.fxml");

    }

    public void openPromotions() {

        SceneManager.switchScene("ManagePromotions.fxml");

    }

    public void openReports() {

        SceneManager.switchScene("Reports.fxml");

    }

    public void goHome() {

        SceneManager.goHome();

    }

}