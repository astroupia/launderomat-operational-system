package core5;


// File: WetCleaningService.java
public class WetCleaningService extends Service {
    private double weight; // in kg
    private double pricePerKg;

    public WetCleaningService(double pricePerKg) {
        super("Wet Cleaning");
        this.pricePerKg = pricePerKg;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void calculatePrice() {
        price = weight * pricePerKg * 1.15; // Add 15% VAT
    }
}

// File: Customer.java
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
}