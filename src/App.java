import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    private Admin admin;
    private List<Customer> customers;

    @Override
    public void start(Stage primaryStage) {
        admin = new Admin("Admin", "Best Laundromat");
        customers = new ArrayList<>();

        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

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
        GridPane customerLayout = new GridPane();
        customerLayout.setPadding(new Insets(20));
        customerLayout.setHgap(10);
        customerLayout.setVgap(10);
        customerLayout.setAlignment(Pos.CENTER);

        Scene customerScene = new Scene(customerLayout, 600, 400);

        Label nameLabel = new Label("Enter your name:");
        TextField nameField = new TextField();

        Label serviceTypeLabel = new Label("Select service type:");
        ComboBox<String> serviceTypeBox = new ComboBox<>();
        serviceTypeBox.getItems().addAll("Dry Cleaning", "Wet Cleaning");

        Label serviceDateLabel = new Label("Select service date:");
        DatePicker serviceDatePicker = new DatePicker();

        Label deliveryDateLabel = new Label("Select delivery date:");
        DatePicker deliveryDatePicker = new DatePicker();

        Button registerButton = new Button("Register Service");
        TextArea receiptArea = new TextArea();
        receiptArea.setEditable(false);

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
                // Example items for dry cleaning service
                ((DryCleaningService) service).addItem("Fur item", 100);
                ((DryCleaningService) service).addItem("Leather item", 150);
            } else {
                service = new WetCleaningService(200); // Example price per kg
                ((WetCleaningService) service).setWeight(2); // Example weight
            }

            Customer customer = new Customer(name, serviceType, serviceDate, deliveryDate, service);
            customer.registerService();
            customers.add(customer);

            receiptArea.setText(customer.displayReceipt());
        });

        Button viewServicesButton = new Button("View Current Services");
        TextArea servicesArea = new TextArea();
        servicesArea.setEditable(false);

        viewServicesButton.setOnAction(e -> {
            String name = nameField.getText();
            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter your name to view your services.");
                alert.show();
                return;
            }

            StringBuilder servicesText = new StringBuilder();
            for (Customer customer : customers) {
                if (customer.getName().equals(name)) {
                    servicesText.append(customer.displayReceipt()).append("\n");
                }
            }

            if (servicesText.length() == 0) {
                servicesText.append("No services found for the given name.");
            }

            servicesArea.setText(servicesText.toString());
        });

        customerLayout.add(nameLabel, 0, 0);
        customerLayout.add(nameField, 1, 0);
        customerLayout.add(serviceTypeLabel, 0, 1);
        customerLayout.add(serviceTypeBox, 1, 1);
        customerLayout.add(serviceDateLabel, 0, 2);
        customerLayout.add(serviceDatePicker, 1, 2);
        customerLayout.add(deliveryDateLabel, 0, 3);
        customerLayout.add(deliveryDatePicker, 1, 3);
        customerLayout.add(registerButton, 0, 4, 2, 1);
        customerLayout.add(new Label("Receipt:"), 0, 5);
        customerLayout.add(receiptArea, 0, 6, 2, 1);
        customerLayout.add(viewServicesButton, 0, 7, 2, 1);
        customerLayout.add(new Label("Current Services:"), 0, 8);
        customerLayout.add(servicesArea, 0, 9, 2, 1);

        stage.setScene(customerScene);
    }

    private void switchToAdminMode(Stage stage) {
        GridPane adminLayout = new GridPane();
        adminLayout.setPadding(new Insets(20));
        adminLayout.setHgap(10);
        adminLayout.setVgap(10);
        adminLayout.setAlignment(Pos.CENTER);

        Scene adminScene = new Scene(adminLayout, 400, 400);

        Label startDateLabel = new Label("Select start date:");
        DatePicker startDatePicker = new DatePicker();

        Label endDateLabel = new Label("Select end date:");
        DatePicker endDatePicker = new DatePicker();

        Button generateReportButton = new Button("Generate Report");
        TextArea reportArea = new TextArea();
        reportArea.setEditable(false);

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
            reportArea.setText(admin.generateReport(startDate, endDate));
        });

        adminLayout.add(startDateLabel, 0, 0);
        adminLayout.add(startDatePicker, 1, 0);
        adminLayout.add(endDateLabel, 0, 1);
        adminLayout.add(endDatePicker, 1, 1);
        adminLayout.add(generateReportButton, 0, 2, 2, 1);
        adminLayout.add(new Label("Report:"), 0, 3);
        adminLayout.add(reportArea, 0, 4, 2, 1);

        stage.setScene(adminScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
