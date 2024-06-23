package com.chrisjward;

public class PlugInHybridVehicle extends Vehicle {
    private double electricOnlyRange;
    private double MPkWh;
    private double MPG;

    public PlugInHybridVehicle() {
        setUsesGas(true);
        setUsesElectricity(true);
    }

    public void setElectricOnlyRange(double electricOnlyRange) {
        this.electricOnlyRange = electricOnlyRange;
    }

    public double getElectricOnlyRange() {
        return electricOnlyRange;
    }

    public void setMPKWh(double MPkWh) {
        this.MPkWh = MPkWh;
    }

    public double getMPkWh() {
        return MPkWh;
    }

    public void setMPG(double MPG) {
        this.MPG = MPG;
    }

    public double getMPG() {
        return MPG;
    }

    public void setFuelEfficiency() {
        System.out.println("\nLet's set the fuel efficiency of your vehicle.");
        System.out.println("First, what is the Electric Only Range of your vehicle?");
        System.out.println("This is the range, in miles, that your vehicle can drive on electric power.");
        System.out.println("If you do not know this number, it can probably be found on fueleconomy.gov");

        electricOnlyRange = InputValidation.getDoubleMaybeBetween(10, 50);

        System.out.println("When on electric power, what is your fuel efficiency\n");
        System.out.println("Which would you like to use?");
        System.out.println("1) miles per kWh");
        System.out.println("2) kWh per 100 miles");
        System.out.println("3) MPGe");

        switch (InputValidation.getIntBetween(1,3)) {
            case 1:
                System.out.println("How many miles per kWh does your vehicle get on electric power?");
                MPkWh = InputValidation.getDoubleMaybeBetween(2,6);
                break;
            case 2:
                System.out.println("How many kWh per 100 miles does your vehicle get on electric power?");
                MPkWh = 100 / InputValidation.getDoubleMaybeBetween(17,50);
                break;
            case 3:
                System.out.println("What is your vehicle's fuel efficiency in MPGe on electric power?");
                MPkWh = InputValidation.getDoubleMaybeBetween(70,150) / 33.7; //33.7 is what the EPA uses for this conversion
                break;
        }

        System.out.println("What is the fuel efficiency (MPG) of your vehicle when it is running on gas?");
        MPG = InputValidation.getDoubleMaybeBetween(10, 60);
    }

    public double calculateFuelCost(double miles, double gasPrice, double electricityPrice) {
        double electricMiles = Math.min(miles, electricOnlyRange);
        double totalElectricityUsed = electricMiles / MPkWh;
        double totalElectricityCost = totalElectricityUsed * electricityPrice;

        double gasMiles = Math.max(0, miles - electricOnlyRange);
        double totalGasUsed = gasMiles / MPG;
        double totalGasCost = totalGasUsed * gasPrice;

        return totalElectricityCost + totalGasCost;
    }
}
