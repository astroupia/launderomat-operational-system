package core5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Customer {
    private String name;
    private String serviceType;
    private Date serviceDate;
    private Date deliveryDate;
    private Service service;

    public Customer(String name, String serviceType, Date serviceDate, Date deliveryDate, Service service) {
        this.name = name;
        this.serviceType = serviceType;
        this.serviceDate = serviceDate;
        this.deliveryDate = deliveryDate;
        this.service = service;
    }

    public void registerService() {
        service.calculatePrice();
        int customerId = getCustomerId(); // Retrieve customer ID from database

    // Add customer to database
    CustomerDAO customerDAO = new CustomerDAO();
    customerDAO.addCustomer(this, customerId);

    // Add service to database based on type
    ServiceDAO serviceDAO = new ServiceDAO();
    if (service instanceof DryCleaningService) {
        serviceDAO.addDryCleaningService(customerId, (DryCleaningService) service);
    } else if (service instanceof WetCleaningService) {
        serviceDAO.addWetCleaningService(customerId, (WetCleaningService) service);
    }
    }

    public void displayReceipt() {
        System.out.println("Customer Name: " + name);
        System.out.println("Service Type: " + serviceType);
        System.out.println("Service Date: " + serviceDate);
        System.out.println("Delivery Date: " + deliveryDate);
        System.out.println("Items/Weight: ");

        // Display service details based on type
        if (service instanceof DryCleaningService) {
            for (String item : ((DryCleaningService) service).listItems()) {
                System.out.println(item);
            }
        } else if (service instanceof WetCleaningService) {
            System.out.println(((WetCleaningService) service).getWeight() + " kg");
        }

        System.out.println("Total Price: " + service.getPrice() + " ETB");
    }

    // Getters for customer details
    public String getName() {
        return name;
    }

    public String getServiceType() {
        return serviceType;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public Service getService() {
        return service;
    }

    // Method to retrieve customer ID (to be implemented)
    private int getCustomerId() {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    int customerId = 0; // Default value if no customer ID found

    try {
        conn = DatabaseUtil.getConnection(); // Assuming this method returns a Connection
        String sql = "SELECT customerId FROM customers WHERE name = ? AND serviceDate = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, name); // Assuming 'name' is a field in Customer class
        stmt.setDate(2, new java.sql.Date(serviceDate.getTime())); // Assuming 'serviceDate' is a field in Customer class
        rs = stmt.executeQuery();

        if (rs.next()) {
            customerId = rs.getInt("customerId");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle SQLException as needed
    } finally {
        DatabaseUtil.closeResultSet(rs);
        DatabaseUtil.closeStatement(stmt);
        DatabaseUtil.closeConnection(conn);
    }

    return customerId;
}
    }

