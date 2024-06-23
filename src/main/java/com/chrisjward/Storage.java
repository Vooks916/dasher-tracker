package com.chrisjward;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;

public class Storage {
    private static final String SETTINGS_FILE_PATH = "settings.json";
    private static final String SHIFTS_FILE_PATH = "shifts.json";
    private static ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static void saveSettings(Settings settings) throws IOException {
        mapper.writeValue(new File(SETTINGS_FILE_PATH), settings);
    }

    public static void saveShifts(Shifts shifts) throws IOException {
        mapper.writeValue(new File(SHIFTS_FILE_PATH), shifts);
    }

    public static Settings loadSettings() throws IOException {
        File file = new File(SETTINGS_FILE_PATH);
        if (!file.exists()) {
            return new Settings();
        }
        return mapper.readValue(file, Settings.class);
    }

    public static Shifts loadShifts() throws IOException {
        File file = new File(SHIFTS_FILE_PATH);
        if (!file.exists()) {
            return new Shifts();
        }
        return mapper.readValue(file, Shifts.class);
    }
}
