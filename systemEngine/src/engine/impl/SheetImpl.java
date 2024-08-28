package engine.impl;
import engine.api.*;
import java.io.File;
import java.util.*;
import javax.xml.*;

public class SheetImpl implements SheetReader,SheetWriter {
    private Map<Coordinate, Cell> sheetMap;
    private String sheetName = "example";
    private int version;
    private List<VersionsHandler> versionHistory;
    SheetLayout layout;

    public SheetImpl(String sheetName, int numRows, int numCols , int[] colsWidth, int[]rowsHeight) {
        this.sheetMap = new HashMap<>(); // Initialize the map
        this.sheetName = sheetName;
        this.version = 1; // do it as constance ;
        this.versionHistory = new ArrayList<>();
        this.layout = new SheetLayoutImp(numRows, numCols, colsWidth, rowsHeight);
    }
    @Override
    public SheetLayout getSheetLayout(){
        return this.layout;
    }
    @Override
    public Map<Coordinate, Cell> getSheet() {
        return sheetMap;
    }

    @Override
    public String getSheetName() {
        return sheetName;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public List<VersionsHandler> getVersionHistory() {
        return versionHistory;
    }

    @Override
    public Cell getCell(String cell) {
        return sheetMap.get(parseCoordiante(cell));
    }


    @Override
    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    @Override
    public void setSheet(Map<Coordinate, Cell> sheet) {
        this.sheetMap = sheet;
    }

    @Override
    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public void setCell(String cell, String originalValue) {
        Coordinate newCoordinate = parseCoordiante(cell);
        if (!sheetMap.containsKey(newCoordinate)) {
            Cell newCell = new CellImpl(newCoordinate.getRow(), newCoordinate.getColumn(), originalValue, 1,cell);
            sheetMap.put(newCoordinate, newCell);
        } else {
            sheetMap.get(newCoordinate).setPreviousVal(sheetMap.get(newCoordinate));
            sheetMap.get(newCoordinate).setOriginalValue(originalValue);
        }
    }

    @Override
    public void saveVersion() {
        SheetVersionImpl newVersion = new SheetVersionImpl(version, sheetMap);
        versionHistory.add(newVersion);
        version++;
    }

    @Override
    public boolean isValidSheet() {
        return true;
        //impl
    }

    @Override
    public File loadFromFile(String filename) {
        return null;
        //impl
    }

    @Override
    public void saveToFile(String filename, File file) {
        //impl
    }

    @Override
    public void printSheet() {
        System.out.println("Sheet Name: " + sheetName);
        System.out.println("Version: " + version);

        Map<Integer, Integer> maxColWidths = calculateMaxColWidths();

        // Print column headers
        System.out.print("   ");
        for (int col = 1; col <= layout.getNumOfCols(); col++) {
            System.out.print(padRight(Character.toString((char) ('A' + col - 1)), maxColWidths.get(col)) + "|");
        }
        System.out.println();

        // Print rows
        for (int row = 1; row <= layout.getNumOfRows(); row++) {
            System.out.print(padLeft(Integer.toString(row), 2) + " ");

            for (int col = 1; col <= layout.getNumOfCols(); col++) {
                CoordinateImpl coordinate = new CoordinateImpl(row, col);
                Cell cell = sheetMap.get(coordinate);
                Object cellEffectiveValue = (cell != null) ? cell.getEffectiveValue() : " ";
                System.out.print(padRight(cellEffectiveValue.toString(), maxColWidths.get(col)) + "|");
            }
            System.out.println();
        }
    }

    @Override
    public String padRight(String s, int length) {
        return String.format("%-" + length + "s", s);
    }

    @Override
    public String padLeft(String s, int length) {
        return String.format("%" + length + "s", s);
    }

    @Override
    public void displayVersions() {
        //show previos version
    }

    @Override
    public Coordinate parseCoordiante(String cell) {
        int row = cell.charAt(0) - 'A' + 1;
        int col = Integer.parseInt(cell.substring(1));
        return new CoordinateImpl(row, col);
    }

    public boolean updateCell(String cellIdentity, String value){
        Cell cell = getCell(cellIdentity);
        if (cell != null){
            cell.updatePreviousVal();
        }
        setCell(cellIdentity, value);
        cell = getCell(cellIdentity);


                cell.calcEffectiveValue(this);
                Set<Cell> visited = new HashSet<>();
                Set<Cell> recursionStack = new HashSet<>();
                if (hasCycle(cell, visited, recursionStack ,cell)) {
                    resetCell(cell);
                    throw new IllegalStateException("Circular dependency detected involving cell " + cellIdentity);
                }
                else {
                    updateInfluencingCells(cell);
                    cell.setLastVersion(version);
                    this.version++;
                    return true;
                }




    }

    public void updateInfluencingCells(Cell cell) {
        List<Cell> influencedCellsCopy = new ArrayList<>(cell.getInfluencingOn());

        for (Cell influencedCell : influencedCellsCopy) {
            influencedCell.calcEffectiveValue(this);
            influencedCell.setLastVersion(version);
            updateInfluencingCells(influencedCell); // Recursively update influenced cells
        }
    }
    public Map<Integer, Integer> calculateMaxColWidths() {
        Map<Integer, Integer> maxColWidths = new HashMap<>();

        for (int col = 1; col <= layout.getNumOfCols(); col++) {
            maxColWidths.put(col, 1);
        }

        for (int row = 1; row <= layout.getNumOfRows(); row++) {
            for (int col = 1; col <= layout.getNumOfCols(); col++) {
                CoordinateImpl coordinate = new CoordinateImpl(row, col);
                Cell cell = sheetMap.get(coordinate);
                String cellValue = (cell != null) ? cell.getEffectiveValue().toString() : " ";
                maxColWidths.put(col, Math.max(maxColWidths.get(col), cellValue.length()));
            }
        }

        return maxColWidths;
    }

    public boolean checkIfValidCell(String s) {
        try{
            int row = s.charAt(0) - 'A' + 1;
            int col = Integer.parseInt(s.substring(1));
            String rowLetter = String.valueOf(layout.getNumOfRows() + 'A');

            if (row > layout.getNumOfRows() || row < 1) {
                throw new NumberFormatException();
            }
            if (col > layout.getNumOfCols() || col < 1) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    public String getColumnLetter(int columnNumber) {
        StringBuilder columnLetter = new StringBuilder();

        while (columnNumber > 0) {
            columnNumber--; // Adjusting because Excel columns are 1-indexed
            columnLetter.insert(0, (char) ('A' + (columnNumber % 26)));
            columnNumber /= 26;
        }

        return columnLetter.toString();
    }
    public Boolean hasCycle(Cell cell, Set<Cell> visited, Set<Cell> recursionStack , Cell depend) {
        if (recursionStack.contains(cell)) {
            removeCircularDependency(cell,depend);
            return true;
        }
        if (visited.contains(cell)) {
            return false;
        }

        visited.add(cell);
        recursionStack.add(cell);

        for (Cell dependentCell : cell.getDependsOn()) {
            depend = cell;
            if (hasCycle(dependentCell, visited, recursionStack, depend)) {
                return true;
            }
        }
        recursionStack.remove(cell);
        return false;
    }
    public void removeCircularDependency(Cell source, Cell ref) {
        source.getDependsOn().remove(ref);
        ref.getInfluencingOn().remove(source);
    }
    public void resetCell(Cell cell) {
        cell = cell.getPreviousVal();
        cell.cancelDependencies();
    }

    public void LoadSheet(String filename) {

    }

}



