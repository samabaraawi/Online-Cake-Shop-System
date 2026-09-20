package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import service.ReportService;
import util.SceneManager;

public class ReportsController {

    @FXML
    private ComboBox<String> reportTypeBox;

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private TableView<ReportRow> reportTable;

    @FXML
    private TableColumn<ReportRow,String> metricColumn;

    @FXML
    private TableColumn<ReportRow,String> valueColumn;

    private final ReportService reportService =
            new ReportService();

    @FXML
    public void initialize() {

        reportTypeBox.getItems().addAll(

                "Sales Report",
                "Order Report"

        );

        reportTypeBox.setValue("Sales Report");

        metricColumn.setCellValueFactory(
                data -> new SimpleStringProperty(
                        data.getValue().getMetric()));

        valueColumn.setCellValueFactory(
                data -> new SimpleStringProperty(
                        data.getValue().getValue()));

    }

    @FXML
    private void generateReport() {

        ObservableList<ReportRow> rows =
                FXCollections.observableArrayList();

        if(reportTypeBox.getValue().equals("Sales Report")){

            rows.add(new ReportRow(
                    "Total Sales",
                    "$" + String.format("%.2f",
                            reportService.getTotalSales())
            ));

            rows.add(new ReportRow(
                    "Completed Orders",
                    String.valueOf(
                            reportService.getCompletedOrders())
            ));

        }

        else{

            rows.add(new ReportRow(
                    "Total Orders",
                    String.valueOf(
                            reportService.getTotalOrders())
            ));

            rows.add(new ReportRow(
                    "Pending Orders",
                    String.valueOf(
                            reportService.getPendingOrders())
            ));

            rows.add(new ReportRow(
                    "Cancelled Orders",
                    String.valueOf(
                            reportService.getCancelledOrders())
            ));

            rows.add(new ReportRow(
                    "Completed Orders",
                    String.valueOf(
                            reportService.getCompletedOrders())
            ));

        }

        reportTable.setItems(rows);

    }

    @FXML
    private void goBack(){

        SceneManager.goBack();

    }

    @FXML
    private void goHome(){

        SceneManager.goHome();

    }

    public static class ReportRow{

        private final String metric;

        private final String value;

        public ReportRow(String metric,
                         String value){

            this.metric = metric;

            this.value = value;

        }

        public String getMetric(){

            return metric;

        }

        public String getValue(){

            return value;

        }

    }

}