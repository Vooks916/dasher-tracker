package com.chrisjward;

import java.io.IOException;

public class App 
{
    public static void main( String[] args ) {
        Settings settings = new Settings();
        Shifts shifts = new Shifts();
        Boolean userDone = false;

        try {
            settings = Storage.loadSettings();
            shifts = Storage.loadShifts();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (settings.getVehicles().size() == 0 ) {
            setupNewUser(settings);
        }

        while (!userDone) {
            displayMainMenu();

            switch (InputValidation.getIntBetween(1, 5)) {
                case 1:
                    //TODO: Add new shift
                    Shift newShift = new Shift();
                    newShift.initializeShift(settings);
                    shifts.addShift(newShift);
                    break;
                case 2:
                    //TODO: Edit existing shift
                    System.out.println("This option will be available in the future.");
                    break;
                case 3:
                    //TODO: Generate report
                    Calculations.calculateMoneyPerHourPerApp(shifts.getShifts(), settings);
                    break;
                case 4:
                    //Access settings
                    settings.editSettings();
                    break;
                case 5:
                    //Save and exit;
                    try {
                        Storage.saveSettings(settings);
                        Storage.saveShifts(shifts);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    userDone = true;
                    break;
            }
        }
    }

    public static void displayMainMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1) Add new shift");
        System.out.println("2) Edit existing shift");
        System.out.println("3) Generate report");
        System.out.println("4) Settings");
        System.out.println("5) Save and exit");;
    }

    public static void setupNewUser(Settings settings) {
        System.out.println("It looks like this is your first time using this program!");
        
        Vehicle.addNewVehicle(settings);
    
        System.out.println("\nThis program will use a default value of 20% to estimate the taxes you will owe.");
        System.out.println("Would you like to change this value?");
        if (InputValidation.getYesNoInput() == 'y') {
            System.out.println("What percentage would you like to use?");
            settings.setEstimatedTaxes(InputValidation.getDoubleBetween(0, 100) / 100.0);            
        }
    }
}
