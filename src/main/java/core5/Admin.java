package core5;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Admin {
    private String name;
    private String businessName;
    private Date startDate;
    private Date endDate;
    private List<Customer> customers;

    public Admin(String name, String businessName) {
        this.name = name;
        this.businessName = businessName;
        customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void generateReport(Date startDate, Date endDate) {
        List<Customer> customers = customerDAO.getCustomers(startDate, endDate);
        int numberOfCustomers = customers.size();
        double totalRevenue = customers.stream().mapToDouble(c -> c.getService().getPrice()).sum();

        System.out.println("Business Name: " + businessName);
        System.out.println("Report Duration: " + startDate + " to " + endDate);
        System.out.println("Number of Customers: " + numberOfCustomers);
        System.out.println("Total Revenue: " + totalRevenue + " birr");
    }
}