package userInterface;

import engine.api.*;
import engine.impl.CellImpl;
import engine.impl.CoordinateImpl;
import engine.impl.SheetDTO;
import engine.impl.SheetImpl;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.util.*;


public class Ui {

    private final Scanner scanner;
    private final Engine engine;

    public Ui(Engine engine) {
        scanner = new Scanner(System.in);
        this.engine = engine;
    }

    public void start() throws JAXBException, IOException, ClassNotFoundException {
        String choice;
        while (true) {
            do {
                printMenu();
                choice = getUserInput().trim();
            }while(!checkIfValidChoice(choice));

            switch (choice) {
                case "1":
                    loadSheet();
                    break;
                case "2":
                    printSheet();
                    break;
                case "3":
                    displaySingleCell();
                    break;
                case "4":
                    updateCell();
                    break;
                case "5":
                    displayVersions();
                    break;
                case "6":
                    System.out.println("Exiting the system...");// exit the system corectlly
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        }
    }
        private void printMenu () {
            System.out.println("===MENU===");
            System.out.println("1. Load Sheet");
            System.out.println("2. Display Sheet");
            System.out.println("3. Display Single Cell");
            System.out.println("4. Update Cell");
            System.out.println("5. Display Versions");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

        }

        private String getUserInput() {
            return scanner.nextLine().trim();
        }
        private void loadSheet() throws JAXBException {
            try {
                System.out.println("Please enter the full path to the XML file you wish to load:");
                String pathName = scanner.nextLine().trim();
                System.out.println("Loading file....");
                engine.loadXMLFile(pathName);
                System.out.println("File successfully loaded.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Failed to load file.");
            }

        }
        private void displaySingleCell() {
            String cellIdentity;
            do {
                System.out.print("Enter a valid cell identity (e.g., A4): ");
                cellIdentity = scanner.next();
                cellIdentity = cellIdentity.toUpperCase();
                scanner.nextLine();
                if (!engine.checkIfValidCell(cellIdentity)) {
                    System.out.println("Invalid cell identity. Expected a row between 1 and " + engine.getNumOfRows()+ " ,and a column between A and " + engine.getColumnLetter(engine.getNumOfCols()) + " Please try again.");
                }
            } while (!engine.checkIfValidCell(cellIdentity));
            if(engine.getSheetReader().getCell(cellIdentity)!=null) {


                System.out.println("-Cell " + cellIdentity + " info-");
                System.out.println("- Original value: " + engine.getSheetReader().getCell(cellIdentity).getOriginalValue());
                System.out.println("- Effective value: " + engine.getSheetReader().getCell(cellIdentity).getEffectiveValue());
                System.out.println("- Last version changed: " + engine.getSheetReader().getCell(cellIdentity).getLastVersion());
                System.out.print("- Influencing on: ");
                printInfluencingOn(cellIdentity);
                System.out.println();
                System.out.print("- Depends on: ");
                printDependOn(cellIdentity);
                System.out.println();

            }
            else {
                System.out.println("-Cell " + cellIdentity + " is empty-");
            }
        }
        private void updateCell() {
            String cellIdentity =" ";
            do {
                try {
                    System.out.print("Enter a valid cell identity (e.g., A4): ");
                    cellIdentity = scanner.next();
                    cellIdentity = cellIdentity.toUpperCase();
                    scanner.nextLine();
                    if (!engine.checkIfValidCell(cellIdentity)) {
                        System.out.println("Invalid cell identity. Expected a row between 1 and " + engine.getNumOfRows() + " ,and a column between A and " + engine.getColumnLetter(engine.getNumOfCols()) + " Please try again.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid cell identity. Please try again.");
                }
            } while (!engine.checkIfValidCell(cellIdentity));

            try {
                if (engine.getCell(cellIdentity) == null) {
                    throw new NullPointerException();
                }
            } catch (NullPointerException e) {
                System.out.println("-Cell " + cellIdentity + " info-");
                System.out.println("Original value: ");
                System.out.println("Effective value: ");
                System.out.println("Last verison changed: Cell have not been changed yet.");
            }
            String newValue;
            try {
                System.out.print("Enter new value (e.g., 4, {PLUS,4,5}): ");
                newValue = getUserInput();
                engine.updateCell(cellIdentity, newValue);
                System.out.println("Cell updated successfully.");

            }catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Cell update failed.");

            }
        }

        private void displayVersions() throws IOException, ClassNotFoundException {
            List<Integer> versionChanges = engine.getVersionChanges();
            String number = "Version number";
            String changedCell = "Amount of cells changed in this version";
            System.out.println(number + " | " + changedCell);
            for (int i = 0; i < versionChanges.size(); i++) {
                for(int j = 0; j<number.length() + changedCell.length() + 3; j++) {
                    System.out.print("_");
                }
                System.out.println();
                System.out.println((i+1) + "              " + "|" + versionChanges.get(i));
            }
            if (versionChanges.size() == 0) {
                System.out.println("There are currently no versions available for this sheet. It hasn't been modified yet.");
                return;
            }
            String choice;
            int version;
            do {
                System.out.println("Enter the version number to display:");
                choice = getUserInput().trim();
                version = Integer.parseInt(choice);
                if (version <= versionChanges.size() && version > 0) {
                    SheetDTO sheetDTO = engine.getVersion(version);
                    printSheet(sheetDTO);

                }
                else {
                    System.out.println("Invalid version number. Please try again.");

                }
            }while (version > versionChanges.size() || version <= 0);


        }

    private Boolean checkIfValidChoice(String userChoice) {
        try {
            int choice = Integer.parseInt(userChoice);
            if(userChoice.equals("1")){
                return true;
            }
            else if (engine.getSheetReader() == null  && (choice >= 1 && choice <= 6)) {
                System.out.println("No sheet is currently loaded. Please load a sheet first to access these option.");
                return false;
            }
            else if (choice >= 1 && choice <= 6) {
                return true;
            }else {
                System.out.println("Invalid choice. Please select a number between 1 and 6.");
                return false;
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number."); // Handle non-numeric input
            return false;
        }
    }
    public void printSheet(SheetDTO sheet) {
        printCommonSheet(sheet.getSheetName(), sheet.getVersion(), sheet.getSheetMap(), sheet.getLayout());
    }

    public void printSheet() {
        SheetReader sheet = engine.getSheetReader();
        printCommonSheet(sheet.getSheetName(), sheet.getVersion(), sheet.getSheet(), sheet.getSheetLayout());
    }


    private void printCommonSheet(String sheetName, int version, Map<Coordinate, Cell> sheet, SheetLayout sheetLayout) {
        System.out.println("Version: " + version);
        System.out.println("Name: " + sheetName);

        System.out.print("   ");
        for (int col = 0; col < sheetLayout.getNumOfCols(); col++) {
            System.out.print(String.format("%-" + sheetLayout.getColsWidth() + "s", (char) ('A' + col)) + "|");
        }
        System.out.println();

        for (int row = 1; row <= sheetLayout.getNumOfRows(); row++) {
            String[][] rowLines = new String[sheetLayout.getNumOfCols()][sheetLayout.getRowsHeight()];

            for (int col = 0; col < sheetLayout.getNumOfCols(); col++) {
                String cellId = String.format("%c%d", 'A' + col, row);
                CoordinateImpl coordinate = new CoordinateImpl(row, col);
                Cell cell = sheet.get(coordinate);
                CellImpl cellImpl = (CellImpl) cell;
                String value = !sheet.containsKey(coordinate) ? " " : (cellImpl.getEffectiveValue() == null ? " " : cellImpl.getEffectiveValue().toString());

                for (int rh = 0; rh < sheetLayout.getRowsHeight(); rh++) {
                    int startIdx = rh * sheetLayout.getColsWidth();
                    int endIdx = Math.min(startIdx + sheetLayout.getColsWidth(), value.length());
                    rowLines[col][rh] = startIdx < value.length() ? value.substring(startIdx,endIdx) : "";
                }
            }

            for (int rh = 0; rh < sheetLayout.getRowsHeight(); rh++) {
                if (rh == 0) {
                    System.out.print(String.format("%02d ", row));
                } else {
                    System.out.print("   ");
                }


                for (int col = 0; col < sheetLayout.getNumOfCols(); col++) {
                    System.out.print(String.format("%-" + sheetLayout.getColsWidth() + "s", rowLines[col][rh]) + "|");
                }
                System.out.println();
            }
        }


    }

    public void printDependOn(String cellIdentity){
        if(engine.getSheetReader().getCell(cellIdentity).getDependsOn().isEmpty()){
            System.out.println("None");
        } else {
            for (Cell cell : engine.getSheetReader().getCell(cellIdentity).getDependsOn()) {
                System.out.print(cell.getName() + " ");
            }
        }
    }
    public void printInfluencingOn(String cellIdentity){
        if(engine.getSheetReader().getCell(cellIdentity).getInfluencingOn().isEmpty()){
            System.out.print("None");
        } else {
            for (Cell cell : engine.getSheetReader().getCell(cellIdentity).getInfluencingOn()) {
                System.out.print(cell.getName() + " ");
            }
        }
    }


}
