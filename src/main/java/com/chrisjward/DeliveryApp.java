package com.chrisjward;

import java.util.ArrayList;

public class DeliveryApp {
    private boolean hasShiftActiveTime;
    private boolean hasDeliveryActiveTime;
    private boolean hasEarnByMethod;
    private boolean hasRewardTier;
    private String name;
    private ArrayList<String> rewardTiers;

    public DeliveryApp() {
        rewardTiers = new ArrayList<String>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setHasShiftActiveTime(boolean hasShiftActiveTime) {
        this.hasShiftActiveTime = hasShiftActiveTime;
    }

    public boolean getHasShiftActiveTime() {
        return hasShiftActiveTime;
    }

    public void setHasDeliveryActiveTime(boolean hasDeliveryActiveTime) {
        this.hasDeliveryActiveTime = hasDeliveryActiveTime;
    }

    public boolean getHasDeliveryActiveTime() {
        return hasDeliveryActiveTime;
    }

    public void setHasEarnByMethod(boolean hasEarnByMethod) {
        this.hasEarnByMethod = hasEarnByMethod;
    }

    public boolean getHasEarnByMethod() {
        return hasEarnByMethod;
    }

    public void setHasRewardTier(boolean hasRewardTier) {
        this.hasRewardTier = hasRewardTier;
    }

    public boolean getHasRewardTier() {
        return hasRewardTier;
    }

    public void addRewardTier(String rewardTier) {
        rewardTiers.add(rewardTier);
    }

    public ArrayList<String> getRewardTiers() {
        return rewardTiers;
    }

    public static DeliveryApp addNewDeliveryApp(Settings settings) {
        DeliveryApp newApp = new DeliveryApp();

        System.out.println("\nWhat is the name of this delivery app?");
        newApp.setName(InputValidation.getString());

        System.out.printf("\nHow does %s report your active time?\n", newApp.getName());
        System.out.println("(Active time is the elapsed time between accepting and delivering an order)");
        System.out.println("1) Total active time and active time per delivery");
        System.out.println("2) Only total active time");
        System.out.println("3) Only active time per delivery");
        System.out.println("4) It does not report active time");

        switch (InputValidation.getIntBetween(1, 4)) {
            case 1:
                newApp.setHasDeliveryActiveTime(true);
                newApp.setHasShiftActiveTime(true);
                break;
            case 2:
                newApp.setHasDeliveryActiveTime(false);
                newApp.setHasShiftActiveTime(true);
                break;
            case 3:
                newApp.setHasDeliveryActiveTime(true);
                newApp.setHasShiftActiveTime(false);
                break;
            case 4:
                newApp.setHasDeliveryActiveTime(false);
                newApp.setHasShiftActiveTime(false);
                break;
        }

        System.out.printf("\nDoes %s let you choose between earning by time and earning by offer?\n", newApp.getName());
        if (InputValidation.getYesNoInput() == 'y') {
            newApp.setHasEarnByMethod(true);
        } else {
            newApp.setHasEarnByMethod(false);
        }

        System.out.printf("\nDoes %s have different reward tiers you can earn?\n", newApp.getName());
        if (InputValidation.getYesNoInput() == 'y') {
            newApp.setHasRewardTier(true);
        } else {
            newApp.setHasRewardTier(false);
        }

        settings.addDeliveryApp(newApp);

        return newApp;
    }
}
