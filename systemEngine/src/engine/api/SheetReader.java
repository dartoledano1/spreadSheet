package engine.api;

import engine.impl.SheetDTO;
import engine.impl.SheetImpl;

import java.util.List;
import java.util.Map;

public interface SheetReader {
    //get
    Map<Coordinate, Cell> getSheet();
    int getVersion();
    String getSheetName();
    public SheetLayout getSheetLayout();
    Cell getCell(String cell);
    String getColumnLetter(int columnNumber);
    List<Integer>  getChangedCellCount();
    List<SheetDTO> getVersionHistory();


    boolean checkIfValidCell(String s);
    Coordinate parseCoordiante(String s);
    SheetDTO toDTO();




}
