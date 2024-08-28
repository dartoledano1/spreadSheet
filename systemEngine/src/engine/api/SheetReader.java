package engine.api;

import java.util.List;
import java.util.Map;

public interface SheetReader {
    Map<Coordinate, Cell> getSheet();
    int getVersion();
    String getSheetName();
    List<VersionsHandler> getVersionHistory();
    public SheetLayout getSheetLayout();
    Cell getCell(String cell);
    void printSheet();
    boolean checkIfValidCell(String s);
    String getColumnLetter(int columnNumber);
    Coordinate parseCoordiante(String s);

}
