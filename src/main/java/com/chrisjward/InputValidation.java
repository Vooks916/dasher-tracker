package com.chrisjward;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Scanner;

public class InputValidation {
    static Scanner scnr = new Scanner(System.in);
    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
    static DateTimeFormatter timeWorkedFormatter = DateTimeFormatter.ofPattern("H:mm");
    static DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder()
        .parseCaseInsensitive()
        .appendPattern("h:mma")
        .toFormatter();

    public static char getYesNoInput() {
        System.out.print("(Y/N): ");
        String input = scnr.nextLine();
        if (input.toLowerCase().charAt(0) == 'y') {
            return 'y';
        } else if (input.toLowerCase().charAt(0) == 'n') {
            return 'n';
        } else {
            System.out.println("Enter Y(es) or N(o).");
            return getYesNoInput();
        }
    }

    public static double getDoubleBetween(double min, double max) {
        try {
            double input = scnr.nextDouble();
            if (input >= min && input <= max) {
                scnr.nextLine(); // Clear scnr
                return input;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            scnr.nextLine(); // Clear scnr
            System.out.printf("Enter a valid number between %.0f and %.0f.\n", min, max);
            return getDoubleBetween(min, max);
        }
    }

    public static double getDoubleMaybeBetween(double min, double max) {
        try {
            double input = scnr.nextDouble();
            scnr.nextLine(); // Clear scnr
            if (input >= min && input <= max) {
                return input;
            } else {
                System.out.printf("Typically this number is somewhere between %.0f and %.0f.\n", min, max);
                System.out.println("Are you sure you entered the correct number?");
                if (InputValidation.getYesNoInput() == 'y') {
                    return input;
                } else {
                    System.out.println("Enter the correct number now.");
                    return getDoubleMaybeBetween(min, max);
                }
            }
        } catch (Exception e) {
            scnr.nextLine(); // Clear scnr
            System.out.println("Enter a valid number.");
            return getDoubleMaybeBetween(min, max);
        }
    }

    public static String getString() {
        return scnr.nextLine();
    }

    public static int getIntBetween(int min, int max) {
        try {
            int input = scnr.nextInt();
            if (input >= min && input <= max) {
                scnr.nextLine(); // Clear scnr
                return input;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            scnr.nextLine(); // Clear scnr
            System.out.printf("Enter a valid number between %d and %d.\n", min, max);
            return getIntBetween(min, max);
        }
    }

    public static int getIntMaybeBetween(int min, int max) {
        try {
            int input = scnr.nextInt();
            scnr.nextLine(); // Clear scnr
            if (input >= min && input <= max) {
                return input;
            } else {
                System.out.printf("Typically this number is somewhere between %d and %d.\n", min, max);
                System.out.println("Are you sure you entered the correct number?");
                if (InputValidation.getYesNoInput() == 'y') {
                    return input;
                } else {
                    System.out.println("Enter the correct number now.");
                    return getIntMaybeBetween(min, max);
                }
            }
        } catch (Exception e) {
            scnr.nextLine(); // Clear scnr
            System.out.println("Enter a valid number.");
            return getIntMaybeBetween(min, max);
        }
    }

    public static LocalDate getDate() {
        try {
            LocalDate  date = LocalDate.parse(InputValidation.getString(), dateFormatter);
            return date;
        } catch (Exception e) {
            System.out.println("Use the mm/dd/yyyy format when entering a date (ex. 1/30/2000).");
            return getDate();
        }
    }

    public static LocalTime getTime() {
        try {
            LocalTime  time = LocalTime.parse(InputValidation.getString(), timeFormatter);
            return time;
        } catch (Exception e) {
            System.out.println("Use the hh:mm(am/pm) format when entering a time (ex. 3:00pm).");
            return getTime();
        }
    }

    public static LocalTime getWorkingTime() {
        try {
            LocalTime  time = LocalTime.parse(InputValidation.getString(), timeWorkedFormatter);
            return time;
        } catch (Exception e) {
            System.out.println("Use the h:mm format when entering a time (ex. 4:30 = 4 hours and 30 minutes).");
            return getTime();
        }
    }

    public static LocalTime getTimeAfter(LocalTime beginningTime) {
        try {
            LocalTime  time = LocalTime.parse(InputValidation.getString(), timeFormatter);

            if (time.isBefore(beginningTime)) {
                System.out.println(time.format(timeFormatter) + " is before your start time of " + beginningTime.format(timeFormatter) + ".");
                System.out.println("Did your shift carry on past midnight to the next day?");
                if (InputValidation.getYesNoInput() == 'y') {
                    return time;
                } else {
                    System.out.println("Enter the correct time your shift ended.");
                    return getTimeAfter(beginningTime);
                }
            } else {
                return time;
            }
        } catch (Exception e) {
            System.out.println("Use the hh:mm(am/pm) format when entering a time (ex. 3:00pm).");
            return getTimeAfter(beginningTime);
        }
    }
}
