package core5;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Date;

public class App extends Application {
    private Admin admin;

    @Override
    public void start(Stage primaryStage) {
        admin = new Admin("Admin", "Best Laundromat");

        VBox root = new VBox();
        Scene scene = new Scene(root, 400, 400);

        Button customerModeButton = new Button("Customer Mode");
        customerModeButton.setOnAction(e -> switchToCustomerMode(primaryStage));
        Button adminModeButton = new Button("Administration Mode");
        adminModeButton.setOnAction(e -> switchToAdminMode(primaryStage));

        root.getChildren().addAll(customerModeButton, adminModeButton);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Laundromat System");
        primaryStage.show();
    }

    private void switchToCustomerMode(Stage stage) {
        VBox customerLayout = new VBox();
        Scene customerScene = new Scene(customerLayout, 400, 400);

        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");

        ComboBox<String> serviceTypeBox = new ComboBox<>();
        serviceTypeBox.getItems().addAll("Dry Cleaning", "Wet Cleaning");

        DatePicker serviceDatePicker = new DatePicker();
        DatePicker deliveryDatePicker = new DatePicker();

        Button registerButton = new Button("Register Service");
        registerButton.setOnAction(e -> {
            String name = nameField.getText();
            String serviceType = serviceTypeBox.getValue();
            LocalDate serviceDateValue = serviceDatePicker.getValue();
            LocalDate deliveryDateValue = deliveryDatePicker.getValue();

            if (name.isEmpty() || serviceType == null || serviceDateValue == null || deliveryDateValue == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("All fields must be filled.");
                alert.show();
                return;
            }

            Date serviceDate = Date.valueOf(serviceDateValue);
            Date deliveryDate = Date.valueOf(deliveryDateValue);
            Service service;

            if ("Dry Cleaning".equals(serviceType)) {
                service = new DryCleaningService();
                // Add items to dry cleaning service
            } else {
                service = new WetCleaningService(200); // Example price per kg
                // Set weight for wet cleaning service
            }

            Customer customer = new Customer(name, serviceType, serviceDate, deliveryDate, service);
            customer.registerService();
            customer.displayReceipt();
        });

        customerLayout.getChildren().addAll(nameField, serviceTypeBox, serviceDatePicker, deliveryDatePicker, registerButton);
        stage.setScene(customerScene);
    }

    private void switchToAdminMode(Stage stage) {
        VBox adminLayout = new VBox();
        Scene adminScene = new Scene(adminLayout, 400, 400);

        DatePicker startDatePicker = new DatePicker();
        DatePicker endDatePicker = new DatePicker();

        Button generateReportButton = new Button("Generate Report");
        generateReportButton.setOnAction(e -> {
            LocalDate startDateValue = startDatePicker.getValue();
            LocalDate endDateValue = endDatePicker.getValue();

            if (startDateValue == null || endDateValue == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Both start date and end date must be selected.");
                alert.show();
                return;
            }

            Date startDate = Date.valueOf(startDateValue);
            Date endDate = Date.valueOf(endDateValue);
            admin.generateReport(startDate, endDate);
        });

        adminLayout.getChildren().addAll(startDatePicker, endDatePicker, generateReportButton);
        stage.setScene(adminScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
