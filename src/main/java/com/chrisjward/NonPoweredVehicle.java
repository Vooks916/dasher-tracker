package com.chrisjward;

public class NonPoweredVehicle extends Vehicle {

    public NonPoweredVehicle() {
        setUsesGas(false);
        setUsesElectricity(false);
    }
    
    public void setFuelEfficiency() {
        return; 
    }

    public double calculateFuelCost(double miles, double gasPrice, double electricityPrice) {
        return 0.0;
    }
}
