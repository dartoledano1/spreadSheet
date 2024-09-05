package engine.impl;

import engine.api.Cell;
import engine.api.Coordinate;
import engine.api.SheetWriter;
import engine.sheetFunctions.Expression;
import engine.sheetFunctions.FunctionParser;
import engine.value.CellType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CellImpl implements Cell, Serializable {
    String originalValue = " " ;
    Object effectiveValue = " ";
    int lastVersionChange;
    Coordinate coordinate;
    private Set<Cell> dependsOn;
    private Set<Cell> influencingOn;
    private CellImpl previousVal;
    private String cellName;
    private CellType cellType;

    public CellImpl(int row, int col, String originalValue, int lastVersionChange,String cellName) {
        this.originalValue = originalValue;
        this.lastVersionChange = lastVersionChange;
        this.coordinate = new CoordinateImpl(row, col);
        this.dependsOn = new HashSet<Cell>();
        this.influencingOn = new HashSet<Cell>();
        this.cellName = cellName.toUpperCase();
        this.previousVal = this;
    }

    public CellImpl(CellImpl other) {
        if(this!=other) {
            this.originalValue = other.originalValue;
            this.effectiveValue = other.effectiveValue; // Assuming this is immutable, otherwise, copy it as needed
            this.lastVersionChange = other.lastVersionChange;
            this.coordinate = new CoordinateImpl(other.coordinate.getRow(), other.getCoordinate().getColumn()); // Assuming CoordinateImpl has a copy constructor
            this.dependsOn = new HashSet<>();
            this.cellName = other.cellName;
            this.cellType =  other.cellType;
        }
    }
    public void updatePreviousVal(){
        this.previousVal = new CellImpl(this);
    }


    public CellType getCellType() {
        return cellType;
    }

    @Override
    public String getName() {
        return this.cellName.toUpperCase();
    }
    @Override
    public CellImpl getPreviousVal(){
        return previousVal;
    }
    @Override
    public Set<Cell> getInfluencingOn() {
        return this.influencingOn;
    }
    @Override
    public Set<Cell> getDependsOn() {
        return this.dependsOn;
    }
    @Override
    public Object getEffectiveValue() {
        return this.effectiveValue;
    }
    @Override
    public String getOriginalValue() {
        return this.originalValue;
    }
    @Override
    public int getLastVersion() {
        return this.lastVersionChange;
    }
    @Override
    public Coordinate getCoordinate() {return this.coordinate;}

    @Override
    public void setName(String name) {
        this.cellName = name;
    }
    @Override
    public void setPreviousVal(CellImpl previousVal) {
        this.previousVal = previousVal;
    }
    @Override
    public void setDependsOn(Set<Cell> dependsOn) {
        this.dependsOn = dependsOn;
    }
    @Override
    public void setInfluencingOn(Set<Cell> influencingOn) {
        this.influencingOn = influencingOn;
    }
    @Override
    public void setLastVersion(int lastVersion) {
        this.lastVersionChange = lastVersion;
    }
    @Override
    public void setOriginalValue(String originalValue) {
        this.originalValue = originalValue;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    @Override
    public void setEffectiveValue(Object effectiveValue) {
        this.effectiveValue = effectiveValue;
    }
    @Override
    public boolean calcEffectiveValue(SheetWriter sheet) {

        Expression expression = FunctionParser.parseExpression(originalValue, sheet, this);
        if(expression == null){
            return false;
        }
        else {
            this.effectiveValue = expression.eval().getValue();
            if(this.effectiveValue instanceof String){
                this.cellType = CellType.STRING;
            }
            else if(this.effectiveValue instanceof Integer){
                this.cellType =  CellType.NUMERIC;
            }
            else if(this.effectiveValue instanceof Double){
                this.cellType =  CellType.NUMERIC;
            }
            else if(this.effectiveValue instanceof Boolean){
                this.cellType =  CellType.BOOLEAN;
            }
            this.cellType =  CellType.NUMERIC;
            return true;
        }

    }
    public void copyCell(CellImpl other){
        this.originalValue = other.originalValue;
        this.effectiveValue = other.effectiveValue; // Assuming this is immutable, otherwise, copy it as needed
        this.lastVersionChange = other.lastVersionChange;
    }
    @Override
    public void cancelDependencies(){
        List<Cell> dependsOnCellsCopy = new ArrayList<>(this.getDependsOn());

        for(Cell dependsOnCell : dependsOnCellsCopy) {
            dependsOnCell.getInfluencingOn().remove(this);
        }
        this.getDependsOn().clear();

    }
    public CellDTO toDTO() {
        CoordinateDTO coordinateDTO = new CoordinateDTO(this.coordinate.getRow(), this.coordinate.getColumn());
        return new CellDTO(this.originalValue,
                this.effectiveValue,
                this.lastVersionChange,
                coordinateDTO,
                this.dependsOn,
                this.influencingOn,
                this.previousVal,
                this.cellName);

    }

    public CellImpl fromDTO(CellDTO dto) {
        return new CellImpl(dto.getCoordinate().getRow(),
                dto.getCoordinate().getCol(),
                dto.getOriginalValue(),
                dto.getLastVersionChange(),
                dto.getCellName());
    }
    public void setCellToUndefined(){
        this.originalValue = "Undefined";
        this.effectiveValue = "Undefined";
    }
}

