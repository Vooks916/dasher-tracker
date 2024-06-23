package com.chrisjward;

public class ElectricVehicle extends Vehicle {
    private double MPkWh;

    public ElectricVehicle() {
        setUsesGas(false);
        setUsesElectricity(true);
    }

    public void setMPKWh(double MPkWh) {
        this.MPkWh = MPkWh;
    }

    public double getMPkWh() {
        return MPkWh;
    }

    public void setFuelEfficiency() {
        System.out.println("\nLet's set the fuel efficiency of your vehicle.");
        System.out.println("Which would you like to use?");
        System.out.println("1) miles per kWh");
        System.out.println("2) kWh per 100 miles");
        System.out.println("3) MPGe");

        switch (InputValidation.getIntBetween(1,3)) {
            case 1:
                System.out.println("How many miles per kWh does your vehicle get?");
                MPkWh = InputValidation.getDoubleMaybeBetween(2,6);
                break;
            case 2:
                System.out.println("How many kWh per 100 miles does your vehicle get?");
                MPkWh = 100 / InputValidation.getDoubleMaybeBetween(17,50);
                break;
            case 3:
                System.out.println("What is your vehicle's fuel efficiency in MPGe?");
                MPkWh = InputValidation.getDoubleMaybeBetween(70,150) / 33.7; //33.7 is what the EPA uses for this conversion
                break;
        }
    }

    public double calculateFuelCost(double miles, double gasPrice, double electricityPrice) {
        return (miles / MPkWh) * electricityPrice;
    }
}
