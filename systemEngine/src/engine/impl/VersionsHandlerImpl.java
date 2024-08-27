package engine.impl;
import engine.api.SheetReader;

import java.io.*;

public class VersionsHandlerImpl {
    private static final String SAVE_DIRECTORY = "saves/";

    public VersionsHandlerImpl() {
        // Create save directory if it doesn't exist
        File directory = new File(SAVE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    // Save the current version of the sheet
    public void saveSheetVersion(SheetReader sheet, String versionName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(SAVE_DIRECTORY + versionName + ".ser"))) {
            oos.writeObject(sheet);
        }
    }
}
