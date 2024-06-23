package com.chrisjward;

import java.util.ArrayList;

public class Calculations {
    public static void calculateMoneyPerHour(ArrayList<Shift> shifts) {
        double totalMoney = 0.0;
        double totalMoneyAfterGasDeductions = 0.0;
        double totalMoneyAfterAllDeductions = 0.0;
        double totalMiles = 0.0;
        long totalTime = 0;
        long totalActiveTime = 0;
        boolean canUseActiveHours = true;

        for (Shift shift : shifts) {
            double shiftMoney;
            double shiftMoneyAfterGasDeductions;
            double shiftMoneyAfterAllDeductions;
            Vehicle vehicle = shift.getVehicleDriven();

            // Use this one once you have individual delivery entry
            //if (canUseActiveHours && !(shift.getAppWorkedFor().getHasDeliveryActiveTime() || shift.getAppWorkedFor().getHasShiftActiveTime())) {
            if (canUseActiveHours && !(shift.getAppWorkedFor().getHasShiftActiveTime())) {
                canUseActiveHours = false;
            }

            totalActiveTime += shift.getActiveTime();
            totalTime += shift.getTotalTime();
            totalMiles += shift.getMilesDriven();

            shiftMoney = shift.getBasePay() + shift.getTips() + shift.getAdditionalPay();
            shiftMoneyAfterGasDeductions = shiftMoney - (vehicle.calculateFuelCost(shift.getMilesDriven(), shift.getGasPrice(), shift.getElectricityPrice()));
            shiftMoneyAfterAllDeductions = shiftMoneyAfterGasDeductions - (vehicle.getCostToDriveAMile() * shift.getMilesDriven());

            totalMoney += shiftMoney;
            totalMoneyAfterGasDeductions += shiftMoneyAfterGasDeductions;
            totalMoneyAfterAllDeductions += shiftMoneyAfterAllDeductions;
        }

        System.out.printf("Before any deductions, you made:\n");
        System.out.printf("$%.2f per hour\n", (totalMoney / totalTime) * 60);
        if (canUseActiveHours) {
            System.out.printf("$%.2f per active hour\n", (totalMoney / totalActiveTime) * 60);
        }
        System.out.printf("$%.2f per mile\n", totalMoney / totalMiles);

        System.out.printf("\nAfter gas costs, you made:\n");
        System.out.printf("$%.2f per hour\n", (totalMoneyAfterGasDeductions / totalTime) * 60);
        if (canUseActiveHours) {
            System.out.printf("$%.2f per active hour\n", (totalMoneyAfterGasDeductions / totalActiveTime) * 60);
        }
        System.out.printf("$%.2f per mile\n", totalMoneyAfterGasDeductions / totalMiles);

        System.out.printf("\nAfter all deductions, you made:\n");
        System.out.printf("$%.2f per hour\n", (totalMoneyAfterAllDeductions / totalTime) * 60);
        if (canUseActiveHours) {
            System.out.printf("$%.2f per active hour\n", (totalMoneyAfterAllDeductions / totalActiveTime) * 60);
        }
        System.out.printf("$%.2f per mile\n", totalMoneyAfterAllDeductions / totalMiles);
    }

    public static void calculateMoneyPerHourPerApp(ArrayList<Shift> shifts, Settings settings) {
        for (DeliveryApp app : settings.getDeliveryApps()) {
            ArrayList<Shift> shiftsOfApp = new ArrayList<Shift>();

            for (Shift shift : shifts) {
                if (shift.getAppWorkedFor().getName().equals(app.getName())) {
                    shiftsOfApp.add(shift);
                }
            }
            
            System.out.println("\n\n" + app.getName().toUpperCase());
            calculateMoneyPerHour(shiftsOfApp);
        }
    }
}
