package engine.api;

import java.io.File;

public interface Engine {
    File loadFromFile(String filename);
    void printSheet();
    void displaySingleCell(String cellIdentity);
    boolean checkIfValidCell(String s);
    boolean updateCell(String cellIdentity,String value);
    Cell getCell(String cell);
    void resetCell(Cell cell);
    void displayVersions();
    int getNumOfRows();
    int getNumOfCols();
    String getColumnLetter(int columnNumber);

}
