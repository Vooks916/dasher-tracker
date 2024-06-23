package com.chrisjward;
import java.util.ArrayList;

public class Settings {
    private ArrayList<DeliveryApp> deliveryApps;
    private ArrayList<Vehicle> vehicles;
    private ArrayList<String> locations;
    private double estimatedTaxes;

    public Settings() {
        deliveryApps = new ArrayList<DeliveryApp>();
        vehicles = new ArrayList<Vehicle>();
        locations = new ArrayList<String>();
        estimatedTaxes = 0.2;
    }

    public void addDeliveryApp(DeliveryApp app) {
        deliveryApps.add(app);
    }

    public ArrayList<DeliveryApp> getDeliveryApps() {
        return deliveryApps;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public void addLocation(String location) {
        locations.add(location);
    }

    public ArrayList<String> getLocations() {
        return locations;
    }

    public void setEstimatedTaxes(double estimatedTaxes) {
        this.estimatedTaxes = estimatedTaxes;
    }

    public double getEstimatedTaxes() {
        return estimatedTaxes;
    }

    public void editSettings() {
        System.out.println("\nSETTINGS");
        System.out.println("1) Change estimated tax rate");
        System.out.println("2) Edit vehicles");
        System.out.println("3) Edit delivery apps");
        //TODO: Edit locations
        System.out.println("4) Exit settings");

        switch (InputValidation.getIntBetween(1, 4)) {
            case 1:
                //Change tax rate
                System.out.printf("The current estimated tax rate is set to %.2f%%\n", this.getEstimatedTaxes() * 100);
                System.out.println("What would you like to change it to?");
                this.setEstimatedTaxes(InputValidation.getDoubleBetween(0, 100) / 100.0);
                break;
            case 2:
                ArrayList<Vehicle> vehicles = this.getVehicles();
                int i;
                System.out.println("Select the vehicle you would like to edit.");
                for (i = 0; i < vehicles.size(); i++) {
                    System.out.printf("%d) %s\n", i + 1, vehicles.get(i).getName());
                }
                System.out.printf("%d) Add new vehicle\n", i + 1);
                System.out.printf("%d) Back\n", i + 2);
                
                int input = InputValidation.getIntBetween(1, i + 2);

                if (input == i + 1) {
                    Vehicle.addNewVehicle(this);
                } else  if (input == i + 2) {
                    this.editSettings();
                } else {
                    vehicles.get(i - 1).editVehicle(this);
                }
                break;
            case 3:
                //TODO: Edit apps
                break;
            case 4:
                //Exit
                break;
        }
    }

    public void removeVehicle(Vehicle vehicle) {
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i) == vehicle) {
                vehicles.remove(i);
            }
        }
    }
}
