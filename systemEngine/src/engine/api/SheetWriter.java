package engine.api;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SheetWriter {

    //sheet reader functions
    Map<Coordinate, Cell> getSheet();
    int getVersion();
    String getSheetName();
    List<VersionsHandler> getVersionHistory();
    Cell getCell(String cell);
    SheetLayout getSheetLayout();

    //set functions
    void setSheet(Map<Coordinate, Cell> sheet);
    void setVersion(int version);
    void setSheetName(String sheetName);
    void setCell(String cell, String originalValue);

    //changes cell identity to col and row coordinate
    Coordinate parseCoordiante(String s);

    //file and versions related functions
    void saveVersion();
    File loadFromFile(String filename);
    void saveToFile(String filename, File file);
    boolean isValidSheet();
    void displayVersions();

    //print sheet functions
    void printSheet();
    String padRight(String s, int length);
    String padLeft(String s, int length);
    Map<Integer, Integer> calculateMaxColWidths();

   //update cell functions
    Boolean hasCycle(Cell cell, Set<Cell> visited, Set<Cell> recursionStack , Cell sourceCell);
    void resetCell(Cell cell);
    boolean updateCell(String cellIdentity,String value);
    void updateInfluencingCells(Cell cell);

}
