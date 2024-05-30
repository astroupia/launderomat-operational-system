package core5;

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
        customerDAO.addCustomer(this);

        int customerId = getCustomerId(); // Retrieve customer ID from database
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
        if (service instanceof DryCleaningService) {
            for (String item : ((DryCleaningService) service).listItems()) {
                System.out.println(item);
            }
        } else if (service instanceof WetCleaningService) {
            System.out.println(((WetCleaningService) service).getWeight() + " kg");
        }
        System.out.println("Total Price: " + service.getPrice() + " birr");
    }

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

    private int getCustomerId() {
        // Retrieve customer ID from the database (implement logic here)
        return 0;
    }
}