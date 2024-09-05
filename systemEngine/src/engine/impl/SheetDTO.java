package engine.impl;
import engine.api.Cell;
import engine.api.Coordinate;
import engine.api.SheetLayout;
import engine.api.VersionsHandler;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SheetDTO implements Serializable {

    private Map<Coordinate, Cell> sheetMap;
    private String sheetName = "example";
    private int version;
    private SheetLayout layout;
    private List<Integer> changedCellInVersion;

    //constructor
    SheetDTO(Map<Coordinate, Cell> sheetMap , String sheetName, int version, SheetLayout layout, List<Integer> changedCell) {
        this.sheetMap = sheetMap;
        this.sheetName = sheetName;
        this.version = version;
        this.layout = layout;
        this.changedCellInVersion = changedCell;
    }
    //get
    public Map<Coordinate, Cell> getSheetMap() {
        return sheetMap;
    }
    public String getSheetName() {
        return sheetName;
    }
    public int getVersion() {
        return version;
    }
    public SheetLayout getLayout() {
        return layout;
    }
    public List<Integer>  getChangedCellInVersion() {
        return changedCellInVersion;
    }
    public void setSheetMap(Map<Coordinate, Cell> sheetMap) {
        this.sheetMap = sheetMap;
    }
    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }
    public void setVersion(int version) {
        this.version = version;
    }
    public void setLayout(SheetLayout layout) {
        this.layout = layout;
    }
    public void setChangedCellInVersion(List<Integer>  changedCellInVersion) {
        this.changedCellInVersion = changedCellInVersion;
    }
    public Map<Integer, Integer> calculateMaxColWidths() {
        Map<Integer, Integer> maxColWidths = new HashMap<>();

        for (int col = 1; col <= layout.getNumOfCols(); col++) {
            maxColWidths.put(col, layout.getColsWidth());
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


}
