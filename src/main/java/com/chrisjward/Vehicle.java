package com.chrisjward;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = GasVehicle.class, name = "gas"),
    @JsonSubTypes.Type(value = ElectricVehicle.class, name = "electric"),
    @JsonSubTypes.Type(value = NonPoweredVehicle.class, name = "non-powered"),
    @JsonSubTypes.Type(value = PlugInHybridVehicle.class, name = "plug-in-hybrid")
})

public abstract class Vehicle {
    protected String name;
    protected double costToDriveAMile;
    protected double annualInsuranceCost;
    protected double estimatedAnnualMaintenanceCost;
    protected double purchasePrice;
    protected double expectedResaleValue;
    protected double plannedNumYearsOfOwnership;
    protected double estimatedAnnualMilage;
    protected double otherAnnualCosts;
    protected double annualDepreciation;
    protected double totalAnnualCost;
    protected boolean usesGas;
    protected boolean usesElectricity;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAnnualInsuranceCost(double annualInsuranceCost) {
        this.annualInsuranceCost = annualInsuranceCost;
    }

    public double getAnnualInsuranceCost() {
        return annualInsuranceCost;
    }

    public void setEstimatedAnnualMaintenanceCost(double estimatedAnnualMaintenanceCost) {
        this.estimatedAnnualMaintenanceCost = estimatedAnnualMaintenanceCost;
    }

    public double getEstimatedAnnualMaintenanceCost() {
        return estimatedAnnualMaintenanceCost;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setExpectedResaleValue(double expectedResaleValue) {
        this.expectedResaleValue = expectedResaleValue;
    }

    public double getExpectedResaleValue() {
        return expectedResaleValue;
    }

    public void setPlannedNumYearsOfOwnership(double plannedNumYearsOfOwnership) {
        this.plannedNumYearsOfOwnership = plannedNumYearsOfOwnership;
    }

    public double getPlannedNumYearsOfOwnership() {
        return plannedNumYearsOfOwnership;
    }

    public void setEstimatedAnnualMilage(double estimatedAnnualMilage) {
        this.estimatedAnnualMilage = estimatedAnnualMilage;
    }

    public double getEstimatedAnnualMilage() {
        return estimatedAnnualMilage;
    }

    public void setOtherAnnualCosts(double otherAnnualCosts) {
        this.otherAnnualCosts = otherAnnualCosts;
    }

    public double getOtherAnnualCosts() {
        return otherAnnualCosts;
    }

    public void setAnnualDepreciation(double annualDepreciation) {
        this.annualDepreciation = annualDepreciation;
    }

    public double getAnnualDepreciation() {
        return annualDepreciation;
    }

    public void setTotalAnnualCost(double totalAnnualCost) {
        this.totalAnnualCost = totalAnnualCost;
    }

    public double getTotalAnnualCost() {
        return totalAnnualCost;
    }

    public void setCostToDriveAMile(double costToDriveAMile) {
        this.costToDriveAMile = costToDriveAMile;
    }

    public double getCostToDriveAMile() {
        return costToDriveAMile;
    }

    public void setUsesGas(boolean usesGas) {
        this.usesGas = usesGas;
    }

    public boolean getUsesGas() {
        return usesGas;
    }

    public void setUsesElectricity(boolean usesElectricity) {
        this.usesElectricity = usesElectricity;
    }

    public boolean getUsesElectricity() {
        return usesElectricity;
    }

    public void calculateCostToDriveAMile() {
        annualDepreciation = (purchasePrice - expectedResaleValue) / plannedNumYearsOfOwnership;
        totalAnnualCost = annualInsuranceCost + estimatedAnnualMaintenanceCost + annualDepreciation + otherAnnualCosts;
        costToDriveAMile = totalAnnualCost / estimatedAnnualMilage;
    }

    public void gatherVehicleInfo() {
        System.out.println("\nWhat would you like to call this vehicle?");
        this.name = InputValidation.getString();

        System.out.println("\nThe following questions are used to determine the total cost of driving your vehicle.");
        System.out.println("How much do you pay annually for insurance on this vehicle?");
        this.annualInsuranceCost = InputValidation.getDoubleMaybeBetween(500, 3000);

        System.out.println("\nHow much do you pay annually in maintenance? (oil changes, tire changes, etc.)");
        this.estimatedAnnualMaintenanceCost = InputValidation.getDoubleMaybeBetween(150, 1500);

        System.out.println("\nHow much did you purchase this vehicle for?");
        this.purchasePrice = InputValidation.getDoubleMaybeBetween(1000, 80000);

        System.out.println("\nHow many total years do you plan on owning this vehicle?");
        this.plannedNumYearsOfOwnership = InputValidation.getDoubleMaybeBetween(1, 15);

        System.out.println("\nWhat do you expect the value of this vehicle to be when you sell it?");
        this.expectedResaleValue = InputValidation.getDoubleMaybeBetween(purchasePrice * 0.2, purchasePrice * 0.8); // between 20% and 80% of initial value

        System.out.println("\nHow much do you pay annually for other costs? (registration fees, taxes, etc.)");
        this.otherAnnualCosts = InputValidation.getDoubleMaybeBetween(10, 500);

        System.out.println("\nHow many miles does this vehicle drive per a year?");
        this.estimatedAnnualMilage = InputValidation.getDoubleMaybeBetween(5000, 20000);
    }

    public static Vehicle addNewVehicle(Settings settings) {
        System.out.println("What kind of fuel do you put in your vehicle?");
        System.out.println("1) Only gas");
        System.out.println("2) Only electricity");
        System.out.println("3) Gas and Electricity");
        System.out.println("4) None");

        Vehicle vehicle = null;

        switch (InputValidation.getIntBetween(1, 4)) {
            case 1:
                vehicle = new GasVehicle();
                break;
            case 2:
                vehicle = new ElectricVehicle();
                break;
            case 3:
                vehicle = new PlugInHybridVehicle();
                break;
            case 4:
                vehicle = new NonPoweredVehicle();
                break;
        }

        vehicle.gatherVehicleInfo();
        vehicle.setFuelEfficiency();
        vehicle.calculateCostToDriveAMile();

        settings.addVehicle(vehicle);

        return vehicle;
    }

    public void editVehicle(Settings settings) {
        //TODO: Fix this menu layout
        System.out.println("\nWhat would you like to edit?");
        System.out.println("1) Name");
        System.out.println("2) Fuel Efficiency");
        System.out.println("3) Miles driven per year");
        System.out.println("4) Delete vehicle");
        System.out.println("5) More options");
        System.out.println("6) Return to settings");

        switch (InputValidation.getIntBetween(1, 6)) {
            case 1:
                System.out.println("\nWhat would you like to call this vehicle?");
                this.setName(InputValidation.getString());
                break;
            case 2: 
                this.setFuelEfficiency();
                break;
            case 3:
                System.out.println("How many miles does this vehicle drive per a year?");
                this.setEstimatedAnnualMilage(InputValidation.getDoubleMaybeBetween(5000, 20000));
                break;
            case 4:
                settings.removeVehicle(this);
                break;
            case 5:
                System.out.println("");
                additionalEditVehicle(settings);
                break;
            case 6:
                System.out.println("");
                settings.editSettings();
                break;
        }
    }

    public void additionalEditVehicle(Settings settings) {
        System.out.println("What would you like to edit?");
        System.out.println("1) Insurance cost");
        System.out.println("2) Maintenance cost");
        System.out.println("3) All other annual costs");
        System.out.println("4) Purchase price");
        System.out.println("5) Expected resale value");
        System.out.println("6) Planned years of ownership");
        System.out.println("7) Back");
        
        switch (InputValidation.getIntBetween(1, 7)) {
            case 1:
                System.out.println("How much do you pay annually for insurance on this vehicle?");
                this.annualInsuranceCost = InputValidation.getDoubleMaybeBetween(500, 3000);
                break;
            case 2:
                System.out.println("How much do you pay annually in maintenance? (oil changes, tire changes, etc.)");
                this.estimatedAnnualMaintenanceCost = InputValidation.getDoubleMaybeBetween(150, 1500);
                break;
            case 3:
                System.out.println("How much do you pay annually for other costs? (registration fees, taxes, etc.)");
                this.otherAnnualCosts = InputValidation.getDoubleMaybeBetween(10, 500);
                break;
            case 4:
                System.out.println("How much did you purchase this vehicle for?");
                this.purchasePrice = InputValidation.getDoubleMaybeBetween(1000, 80000);
                break;
            case 5:
                System.out.println("What do you expect the value of this vehicle to be when you sell it?");
                this.expectedResaleValue = InputValidation.getDoubleMaybeBetween(purchasePrice * 0.2, purchasePrice * 0.8); // between 20% and 80% of initial value
                break;
            case 6:
                System.out.println("\nHow many total years do you plan on owning this vehicle?");
                this.plannedNumYearsOfOwnership = InputValidation.getDoubleMaybeBetween(1, 15);
                break;
            case 7:
                editVehicle(settings);
                break;
        }
    }

    public abstract void setFuelEfficiency();
    public abstract double calculateFuelCost(double miles, double gasPrice, double electricityPrice);
}
