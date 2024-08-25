package engine.api;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Sheet {

    Map<Coordinate, Cell> getSheet();
    int getVersion();
    String getSheetName();
    List<SheetVersion> getVersionHistory();
    int getNumOfRows();
    int getNumOfCols();
    Cell getCell(String cell);

    void setSheet(Map<Coordinate, Cell> sheet);
    void setVersion(int version);
    void setSheetName(String sheetName);
    void setNumOfRows(int numOfRows);
    void setNumOfCols(int numOfCols);

    Coordinate parseCoordiante(String s);

    void saveVersion();
    File loadFromFile(String filename);
    void saveToFile(String filename, File file);
    void printSheet();
    Map<Integer, Integer> calculateMaxColWidths();
    String padRight(String s, int length);
    String padLeft(String s, int length);
    boolean isValidSheet();
    void displaySingleCell(String cellIdentity);
    void displayVersions();
    void setCell(String cell, String originalValue);
    boolean checkIfValidCell(String s);
    String getColumnLetter(int columnNumber);
    Boolean hasCycle(Cell cell, Set<Cell> visited, Set<Cell> recursionStack , Cell sourceCell);
    void resetCell(Cell cell);

    boolean updateCell(String cellIdentity,String value);
    void updateInfluencingCells(Cell cell);
}
