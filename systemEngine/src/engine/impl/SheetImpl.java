package engine.impl;
import engine.api.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import javax.xml.*;

public class SheetImpl implements SheetReader,SheetWriter {

    private Map<Coordinate, Cell> sheetMap;
    private String sheetName;
    private int version;
    private SheetLayout layout;
    private List<Integer> changedCellInVersion = new ArrayList<>();
    private List<SheetDTO> versionHistory;

    public SheetImpl(String sheetName, int numRows, int numCols, int colsWidth, int rowsHeight) {
        this.sheetMap = new HashMap<>();
        this.sheetName = sheetName;
        this.version = 1;
        this.layout = new SheetLayoutImp(numRows, numCols, colsWidth, rowsHeight);
        this.versionHistory = new ArrayList<>();
        this.versionHistory.add(this.toDTO());
    }
    public SheetImpl(SheetImpl other) {
        if(this!=other) {
            this.sheetMap = new HashMap<>(other.sheetMap);
            this.sheetName = sheetName;
            this.version = other.version;
            this.layout = new SheetLayoutImp(other.getSheetLayout().getNumOfRows()
                    , other.getSheetLayout().getNumOfCols()
                    , other.getSheetLayout().getColsWidth()
                    , other.getSheetLayout().getRowsHeight());
            this.versionHistory = new ArrayList<>(other.versionHistory);
        }
    }

    @Override
    public List<Integer> getChangedCellInVersion(){
        return changedCellInVersion;
    }
    @Override
    public List<SheetDTO> getVersionHistory() {
        return versionHistory;
    }

    @Override
    public SheetLayout getSheetLayout() {
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
    public Cell getCell(String cell) {
        return sheetMap.get(parseCoordiante(cell));
    }

    @Override
    public List<Integer> getChangedCellCount() {
        return changedCellInVersion;
    }

    @Override
    public void setVersionHistory(List<SheetDTO> versionHistory) {
        this.versionHistory = versionHistory;
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
            Cell newCell = new CellImpl(newCoordinate.getRow(), newCoordinate.getColumn(), originalValue, 1, cell);
            sheetMap.put(newCoordinate, newCell);
        } else {
            sheetMap.get(newCoordinate).setPreviousVal((CellImpl) sheetMap.get(newCoordinate));
            sheetMap.get(newCoordinate).setOriginalValue(originalValue);
        }
    }

    @Override
    public void setChangedCell(List<Integer> changedCellCount) {
        changedCellInVersion = changedCellCount;
    }

    @Override
    public boolean isValidSheet() {
        return true;
        //impl
    }

    @Override
    public Coordinate parseCoordiante(String cell) {
        int col = cell.charAt(0) - 'A' + 1;
        int row = Integer.parseInt(cell.substring(1));
        return new CoordinateImpl(row, col);
    }

    public void updateCell(String cellIdentity, String value) {

        Cell cell = getCell(cellIdentity);
        int changedCells = 0;
        try {
            setCell(cellIdentity, value);
            cell = getCell(cellIdentity);
            cell.updatePreviousVal();
            cell.calcEffectiveValue(this);
            Set<Cell> visited = new HashSet<>();
            Set<Cell> recursionStack = new HashSet<>();
            if (hasCycle(cell, visited, recursionStack, cell)) {
                throw new IllegalStateException("Circular dependency detected involving cell " + cellIdentity);
            } else {
                changedCells = updateInfluencingCells(cell, changedCells);
                changedCellInVersion.add(changedCells+1);
                cell.setLastVersion(cell.getLastVersion() + 1);
            }

        } catch (Exception e) {
            updateInfluencingCells(cell, changedCells);
            throw e;
        }
    }

    public int updateInfluencingCells(Cell cell, int changedCellCount) {
        List<Cell> influencedCellsCopy = new ArrayList<>(cell.getInfluencingOn());

        for (Cell influencedCell : influencedCellsCopy) {
            try {
                influencedCell.calcEffectiveValue(this);
                influencedCell.setLastVersion(version);
                changedCellCount++;
                updateInfluencingCells(influencedCell, changedCellCount);
            } catch (Exception e) {
                influencedCell.setCellToUndefined();
                influencedCell.cancelDependencies();
                throw new ArithmeticException("Cell " + influencedCell.getName()+ " is now undefined");
            }
        }
        return changedCellCount;
    }

    public boolean checkIfValidCell(String s) {
        s = s.toUpperCase();
        if(s.length() > 2 ){
            return false;
        }
        int col = s.charAt(0) - 'A' + 1;
        int row = Integer.parseInt(s.substring(1));
        String rowLetter = String.valueOf(layout.getNumOfRows() + 'A');

        if (row > layout.getNumOfRows() || row < 1) {
            return false;
        }
        if (col > layout.getNumOfCols() || col < 1) {
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

    public Boolean hasCycle(Cell cell, Set<Cell> visited, Set<Cell> recursionStack, Cell depend) {
        if (recursionStack.contains(cell)) {
            removeCircularDependency(cell, depend);
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


    public SheetDTO toDTO() {
        Map<Coordinate, Cell> sheetMapCopy = new HashMap<>();
        if(!sheetMap.isEmpty()) {
            for (Map.Entry<Coordinate, Cell> entry : sheetMap.entrySet()) {
                sheetMapCopy.put(entry.getKey(), new CellImpl((CellImpl) entry.getValue())); // Deep copy of each Cell
            }
        }
        List<Integer> changedCellInVersionCopy = new ArrayList<>(this.changedCellInVersion);

        return new SheetDTO(sheetMapCopy, this.sheetName, this.version, this.layout, changedCellInVersionCopy);
    }

    public void fromDTO(SheetDTO dto) {
        this.sheetName = dto.getSheetName();
        this.version = dto.getVersion();
        this.sheetMap = new HashMap<>();
        for (Map.Entry<Coordinate, Cell> entry : dto.getSheetMap().entrySet()) {
            this.sheetMap.put(entry.getKey(), new CellImpl((CellImpl) entry.getValue())); // Deep copy of each Cell
        }
        this.changedCellInVersion = new ArrayList<>(dto.getChangedCellInVersion());
        this.layout = dto.getLayout(); // Deep copy if necessary
    }


    public void setCellToUndefined(Cell cell) {
        cell.setCellToUndefined();
    }

}



