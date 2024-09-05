package engine.api;

import engine.impl.SheetDTO;
import engine.impl.SheetImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SheetWriter {

    //sheet reader functions
    Map<Coordinate, Cell> getSheet();
    int getVersion();
    String getSheetName();
    Cell getCell(String cell);
    SheetLayout getSheetLayout();
    List<SheetDTO> getVersionHistory();
    List<Integer> getChangedCellInVersion();


    //set functions
    void setSheet(Map<Coordinate, Cell> sheet);
    void setVersion(int version);
    void setSheetName(String sheetName);
    void setCell(String cell, String originalValue);
    void setChangedCell(List<Integer>  changedCellCount);
    void setVersionHistory(List<SheetDTO> versionHistory);

    //changes cell identity to col and row coordinate
    Coordinate parseCoordiante(String s);

    //file and versions related functions
    boolean isValidSheet();
    void fromDTO(SheetDTO dto);
    SheetDTO toDTO();


   //update cell functions
    Boolean hasCycle(Cell cell, Set<Cell> visited, Set<Cell> recursionStack , Cell sourceCell);
    void resetCell(Cell cell);
    void updateCell(String cellIdentity,String value);
    int updateInfluencingCells(Cell cell, int changedCellCount);
    void setCellToUndefined(Cell cell);


}
