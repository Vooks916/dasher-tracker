package com.chrisjward;

public class GasVehicle extends Vehicle {
    private double MPG;

    public GasVehicle() {
        setUsesGas(true);
        setUsesElectricity(false);
    }
    public void setMPG(double MPG) {
        this.MPG = MPG;
    }

    public double getMPG() {
        return MPG;
    }

    public void setFuelEfficiency() {
        System.out.println("\nWhat is the fuel efficiency (MPG) of your vehicle?");
        MPG = InputValidation.getDoubleMaybeBetween(10, 60);
    }

    public double calculateFuelCost(double miles, double gasPrice, double electricityPrice) {
        return (miles / MPG) * gasPrice;
    }
}