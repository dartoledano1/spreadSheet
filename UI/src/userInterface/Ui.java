package userInterface;

import engine.api.Engine;
import engine.api.Sheet;

import java.util.*;


public class Ui {

    private final Scanner scanner;
    private Engine engine;

    public Ui(Engine engine) {
        scanner = new Scanner(System.in);
        this.engine = engine;
    }

    public void start() {
        while (true) {
            String choice;
            do {
                printMenu();
                choice = getUserInput();
            }while(!checkIfValidChoice(choice));

            switch (choice) {
                case "1":
                    // Implement presentation of the sheet
                    loadSheet();
                    break;
                case "2":
                    displaySheet();
                    break;
                case "3":
                    displaySingleCell();
                    break;
                case "4":
                    updateCell();
                    break;
                case "5":
                    // Implement presentation of versions
                    displayVersions();
                    break;
                case "6":
                    // Exit the system
                    exit();
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
            return scanner.next();
        }
        private void loadSheet() {
            //impl loading sheet from xml
        }
        private void displaySheet() {
            engine.printSheet();
        }
        private void displaySingleCell() {
            String cellIdentity;
            do {
                System.out.print("Enter a valid cell identity (e.g., A4): ");
                cellIdentity = scanner.next();
                scanner.nextLine();
                if (!engine.checkIfValidCell(cellIdentity)) {
                    System.out.println("Invalid cell identity. Expected a row between 1 and " + engine.getNumOfRows()+ " ,and a column between A and " + engine.getColumnLetter(engine.getNumOfCols()) + " Please try again.");
                }
            } while (!engine.checkIfValidCell(cellIdentity));

            engine.displaySingleCell(cellIdentity);
        }
        private void updateCell() {
            String cellIdentity;
            do {
                System.out.print("Enter a valid cell identity (e.g., A4): ");
                cellIdentity = scanner.next();
                scanner.nextLine();
                if (!engine.checkIfValidCell(cellIdentity)) {
                    System.out.println("Invalid cell identity. Expected a row between 1 and " + engine.getNumOfRows() + " ,and a column between A and " + engine.getColumnLetter(engine.getNumOfCols()) + " Please try again.");
                }
            } while (!engine.checkIfValidCell(cellIdentity));

            try {
                if (engine.getCell(cellIdentity) == null) {
                    throw new NullPointerException();
                }
                engine.displaySingleCell(cellIdentity);
            } catch (NullPointerException e) {
                System.out.println("-Cell " + cellIdentity + " info-");
                System.out.println("Original value: ");
                System.out.println("Effective value: ");
                System.out.println("Last verison changed: Cell have not been changed yet.");
            }
            String newValue;
            boolean validValue = false;
            String choice;
            try {
                do {
                    System.out.print("Enter new value (e.g., 4, {PLUS,4,5}): ");
                    newValue = getUserInput();
                    validValue = engine.updateCell(cellIdentity, newValue);
                    if (!validValue) {
                        System.out.print("1.Return to main menu\n2.Enter a new value for cell " + cellIdentity + "\n");
                        System.out.println("Enter your choice: ");
                        choice = getUserInput();
                        do {
                            if (choice.equals("1")) {
                                validValue = true;
                                break;
                            } else if (choice.equals("2")) {
                                engine.resetCell(engine.getCell(cellIdentity));
                                validValue = false;
                            } else {
                                System.out.println("Invalid choice. Please try again.");
                                choice = getUserInput();
                            }
                        } while (!choice.equals("1") && !choice.equals("2"));
                    }

                } while (!validValue);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        private void displayVersions() {
            engine.displayVersions();
        }
         private void exit() {
            System.out.println("Exiting the system...");
            exit();

        }
    private Boolean checkIfValidChoice(String userChoice) {
        try {
            int choice = Integer.parseInt(userChoice);
            if (choice >= 1 && choice <= 6) {
                return true;
            } else {
                System.out.println("Invalid choice. Please select a number between 1 and 6.");
                return false;                         // Return false if not in range
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number."); // Handle non-numeric input
            return false;
        }
    }
}
