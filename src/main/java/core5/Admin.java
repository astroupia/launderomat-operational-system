package core5;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Admin {
    private String name;
    private String businessName;
    private Date startDate;
    private Date endDate;
    private CustomerDAO customerDAO; // Add CustomerDAO reference
    private ServiceDAO serviceDAO; // Add ServiceDAO reference

    public Admin(String name, String businessName) {
        this.name = name;
        this.businessName = businessName;
        this.customerDAO = new CustomerDAO(); // Initialize CustomerDAO
        this.serviceDAO = new ServiceDAO(); // Initialize ServiceDAO
    }

    public void addCustomer(Customer customer) {
        customerDAO.addCustomer(customer, generateCustomerId()); // Pass generated customer ID
    }

    public void generateReport(Date startDate, Date endDate) {
        java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
    
        List<Customer> customers = customerDAO.getCustomers(sqlStartDate, sqlEndDate); // Retrieve customers from database
        int numberOfCustomers = customers.size();
        double totalRevenue = customers.stream().mapToDouble(c -> c.getService().getPrice()).sum();

        System.out.println("Business Name: " + businessName);
        System.out.println("Report Duration: " + startDate + " to " + endDate);
        System.out.println("Number of Customers: " + numberOfCustomers);
        System.out.println("Total Revenue: " + totalRevenue + " birr");
    }

    // Generate a unique customer ID (you may implement your logic here)
    private int generateCustomerId() {
        return 0; // Placeholder for now
    }
}
