package com.chrisjward;
import java.util.ArrayList;

public class Shifts {
    private ArrayList<Shift> shifts;

    public Shifts() {
        shifts = new ArrayList<Shift>();
    }

    public void addShift(Shift shift) {
        shifts.add(shift);
    }

    public ArrayList<Shift> getShifts() {
        return shifts;
    }
}
