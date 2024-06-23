package com.chrisjward;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class Shift {

    enum EarnByType {
        TIME,
        OFFER,
        NA
    }

    private LocalTime startTime;
    private LocalTime endTime;
    private long totalTime;
    private long activeTime;
    private double gasPrice;
    private double electricityPrice;
    private double tips;
    private double basePay;
    private double additionalPay;
    private LocalDate startDate;
    private LocalDate endDate;
    private DayOfWeek dayOfWeek;
    private int numDeliveries;
    private double milesDriven;
    private boolean hadGoodWeather;
    private EarnByType earnByMethod;
    private boolean isSignificantDay;
    private DeliveryApp appWorkedFor;
    private String location;
    private String rewardTier;
    private Vehicle vehicleDriven;
    private String additionalNotes;
    private ArrayList<Delivery> deliveries; //TODO: implement individual deliveries

    public Shift() {
        deliveries = new ArrayList<Delivery>();
    }

    public void initializeShift(Settings settings) {
        // GET APP WORKED FOR
        System.out.println("\nWhat app were you working for?");
        ArrayList<DeliveryApp> deliveryApps = settings.getDeliveryApps();
        int i;
        for (i = 0; i < deliveryApps.size(); i++) {
            System.out.printf("%d) %s\n", i + 1, deliveryApps.get(i).getName());
        }
        System.out.printf("%d) Add new delivery app\n", i + 1);
        
        int input = InputValidation.getIntBetween(1, i + 1);

        if (input == i + 1) {
            appWorkedFor = DeliveryApp.addNewDeliveryApp(settings);
        } else {
            appWorkedFor = deliveryApps.get(i - 1);
        }

        // GET EARN BY METHOD
        if (appWorkedFor.getHasEarnByMethod()) {
            System.out.println("\nWhich earning method did you use during this shift?");
            System.out.println("1) Earn by offer");
            System.out.println("2) Earn by time");

            switch (InputValidation.getIntBetween(1, 2)) {
                case 1:
                    earnByMethod = EarnByType.OFFER;
                    break;
                case 2:
                    earnByMethod = EarnByType.TIME;
                    break;
            }
        } else {
            earnByMethod = EarnByType.NA;
        }

        // GET REWARD LEVEL
        if (appWorkedFor.getHasRewardTier()) {
            System.out.println("\nWhich app reward tier were you at during this shift?");
            ArrayList<String> rewardTiers = appWorkedFor.getRewardTiers();

            for (i = 0; i < rewardTiers.size(); i++) {
                System.out.printf("%d) %s\n", i + 1, rewardTiers.get(i));
            }

            System.out.printf("%d) None\n", i + 1);
            System.out.printf("%d) Add new reward tier\n", i + 2);
            
            input = InputValidation.getIntBetween(1, i + 2);

            if (input == i + 2) {
                System.out.println("Enter the name of a new reward tier.");
                String newRewardTier = InputValidation.getString();
                rewardTier = newRewardTier;
                rewardTiers.add(newRewardTier);
            } else if (input == i + 1) {
                rewardTier = "None";
            } else {
                rewardTier = rewardTiers.get(i - 1);
            }
        }

        // GET VEHICLE DRIVEN
        ArrayList<Vehicle> vehicles = settings.getVehicles();
        System.out.println("\nWhich vehicle were you driving?");
        for (i = 0; i < vehicles.size(); i++) {
            System.out.printf("%d) %s\n", i + 1, vehicles.get(i).getName());
        }
        System.out.printf("%d) Add new vehicle\n", i + 1);
        
        input = InputValidation.getIntBetween(1, i + 1);

        if (input == i + 1) {
            vehicleDriven = Vehicle.addNewVehicle(settings);
        } else {
            vehicleDriven = vehicles.get(i - 1);
        }

        // GET START DATE
        System.out.println("\nWhat was the date you started this shift? (mm/dd/yyyy)");
        startDate = InputValidation.getDate();

        // GET START TIME
        System.out.println("\nWhat time did you start this shift? (ex. 8:30am)");
        startTime = InputValidation.getTime();

        //GET END TIME
        System.out.println("\nWhat time did you end this shift? (ex. 3:00pm)");
        endTime = InputValidation.getTimeAfter(startTime);

        //GET ACTIVE TIME
        if (appWorkedFor.getHasShiftActiveTime()) {
            System.out.println("\nEnter your active time during this shift (3:15 = 3 hours and 15 minutes)");
            activeTime = ChronoUnit.MINUTES.between(LocalTime.MIDNIGHT, InputValidation.getWorkingTime());
        }

        // GET GAS PRICE
        if (vehicleDriven.getUsesGas()) {
            System.out.println("\nHow much did gas cost per gallon on " + startDate.format(InputValidation.dateFormatter) + "?");
            gasPrice = InputValidation.getDoubleMaybeBetween(2, 6);
        } else {
            gasPrice = 0.0;
        }

        // GET ELECTRICITY PRICE
        if (vehicleDriven.getUsesElectricity()) {
            System.out.println("\nHow much did electricity cost per kilowatt hour on " + startDate.format(InputValidation.dateFormatter) + "?");
            electricityPrice = InputValidation.getDoubleMaybeBetween(0.1, 0.5);
        } else {
            electricityPrice = 0.0;
        }

        // GET LOCATION DRIVEN
        ArrayList<String> locations = settings.getLocations();
        System.out.println("\nWhere were you driving?");
        for (i = 0; i < locations.size(); i++) {
            System.out.printf("%d) %s\n", i + 1, locations.get(i));
        }
        System.out.printf("%d) Add new location\n", i + 1);
        
        input = InputValidation.getIntBetween(1, i + 1);

        if (input == i + 1) {
            System.out.println("Enter a new location name.");
            String newLocation = InputValidation.getString();
            location = newLocation;
            locations.add(newLocation);
        } else {
            location = locations.get(i - 1);
        }

        // GET WEATHER
        System.out.println("\nWas there any bad weather or other poor road conditions?");
        if (InputValidation.getYesNoInput() == 'y') {
            hadGoodWeather = false;
        } else {
            hadGoodWeather = true;
        }

        // GET SIGNIFICANT DAY STATUS
        System.out.printf("\nWas %s a holiday or other significant day?\n", startDate.format(InputValidation.dateFormatter));
        if (InputValidation.getYesNoInput() == 'y') {
            isSignificantDay = true;
        } else {
            isSignificantDay = false;
        }

        // GET MILES DRIVEN
        System.out.println("\nHow many total miles did you drive this shift?");
        milesDriven = InputValidation.getDoubleMaybeBetween(10, 200);

        // GET NUM DELIVERIES
        System.out.println("\nHow many total deliveries did you complete?");
        numDeliveries = InputValidation.getIntMaybeBetween(1, 20);

        // GET BASE PAY
        System.out.println("\nHow much money did you make from base pay?");
        basePay = InputValidation.getDoubleMaybeBetween(1, 100);

        // GET TIPS
        System.out.println("\nHow much money did you make in tips?");
        tips = InputValidation.getDoubleMaybeBetween(1, 100);

        // GET ADDITIONAL PAY
        System.out.println("\nHow much additional money did you make? (ie. peak/bonus pay, additional contributions, etc.)");
        additionalPay = InputValidation.getDoubleMaybeBetween(0, 25);

        // GET ADDITIONAL NOTES
        System.out.println("\nDo you have any additional notes regarding this shift? If not, hit enter to continue.");
        additionalNotes = InputValidation.getString();

        // CALCULATE END DATE
        if (startTime.isAfter(endTime)) {
            endDate = startDate.plusDays(1);
        } else {
            endDate = startDate;
        }

        // CALCULATE ACTIVE TIME
        totalTime = startTime.until(endTime, ChronoUnit.MINUTES);

        if (totalTime < 0) {
            totalTime = 1440 + totalTime; // There are 1440 minutes in a day, this accounts for shifts that end after midnight
        }

        // CALCULATE DAY OF WEEK
        dayOfWeek = startDate.getDayOfWeek();
    }

    public LocalTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    
    public long getActiveTime() {
        return activeTime;
    }
    
    public void setActiveTime(long activeTime) {
        this.activeTime = activeTime;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }
    
    public double getGasPrice() {
        return gasPrice;
    }
    
    public void setGasPrice(double gasPrice) {
        this.gasPrice = gasPrice;
    }
    
    public double getElectricityPrice() {
        return electricityPrice;
    }
    
    public void setElectricityPrice(double electricityPrice) {
        this.electricityPrice = electricityPrice;
    }
    
    public double getTips() {
        return tips;
    }
    
    public void setTips(double tips) {
        this.tips = tips;
    }
    
    public double getBasePay() {
        return basePay;
    }
    
    public void setBasePay(double basePay) {
        this.basePay = basePay;
    }
    
    public double getAdditionalPay() {
        return additionalPay;
    }
    
    public void setAdditionalPay(double additionalPay) {
        this.additionalPay = additionalPay;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }
    
    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    
    public int getNumDeliveries() {
        return numDeliveries;
    }
    
    public void setNumDeliveries(int numDeliveries) {
        this.numDeliveries = numDeliveries;
    }
    
    public double getMilesDriven() {
        return milesDriven;
    }
    
    public void setMilesDriven(double milesDriven) {
        this.milesDriven = milesDriven;
    }
    
    public boolean getHadGoodWeather() {
        return hadGoodWeather;
    }
    
    public void setHadGoodWeather(boolean hadGoodWeather) {
        this.hadGoodWeather = hadGoodWeather;
    }
    
    public EarnByType getEarnByMethod() {
        return earnByMethod;
    }
    
    public void setEarnByMethod(EarnByType earnByMethod) {
        this.earnByMethod = earnByMethod;
    }
    
    public boolean getIsSignificantDay() {
        return isSignificantDay;
    }
    
    public void setSignificantDay(boolean SignificantDay) {
        isSignificantDay = SignificantDay;
    }
    
    public DeliveryApp getAppWorkedFor() {
        return appWorkedFor;
    }
    
    public void setAppWorkedFor(DeliveryApp appWorkedFor) {
        this.appWorkedFor = appWorkedFor;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }

    public Vehicle getVehicleDriven() {
        return vehicleDriven;
    }

    public void setVehicleDriven(Vehicle vehicleDriven) {
        this.vehicleDriven = vehicleDriven;
    }
    
    public String getRewardTier() {
        return rewardTier;
    }
    
    public void setRewardTier(String rewardTier) {
        this.rewardTier = rewardTier;
    }
    
    public String getAdditionalNotes() {
        return additionalNotes;
    }
    
    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }
    
    public ArrayList<Delivery> getDeliveries() {
        return deliveries;
    }
    
    public void setDeliveries(ArrayList<Delivery> deliveries) {
        this.deliveries = deliveries;
    }
}
