module model.cakeshop {

    requires javafx.controls;
    requires javafx.fxml;

    exports application;

    opens application to javafx.fxml;
    opens controller to javafx.fxml;
    opens model to javafx.base, javafx.fxml;

}