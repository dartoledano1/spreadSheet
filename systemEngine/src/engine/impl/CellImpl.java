package engine.impl;

import engine.api.Cell;
import engine.api.Coordinate;
import engine.api.SheetReader;
import engine.api.SheetWriter;
import engine.sheetFunctions.Expression;
import engine.sheetFunctions.FunctionParser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CellImpl implements Cell {
    String originalValue ;
    Object effectiveValue ;
    int lastVersionChange;
    Coordinate coordinate;
    private Set<Cell> dependsOn;
    private Set<Cell> influencingOn;
    private Cell previousVal;
    private String cellName;

    public CellImpl(int row, int col, String originalValue, int lastVersionChange,String cellName) {
        this.originalValue = originalValue;
        this.lastVersionChange = lastVersionChange;
        this.coordinate = new CoordinateImpl(row, col);
        this.dependsOn = new HashSet<Cell>();
        this.influencingOn = new HashSet<Cell>();
        this.cellName = cellName;
        this.previousVal = this;
    }
    public void updatePreviousVal(){
        previousVal.setOriginalValue(originalValue);
        previousVal.setLastVersion(lastVersionChange);
        previousVal.setEffectiveValue(effectiveValue);
    }

    @Override
    public String getName() {
        return this.cellName;
    }

    @Override
    public Cell getPreviousVal(){
        return previousVal;
    }
    public Set<Cell> getInfluencingOn() {
        return this.influencingOn;
    }
    public Set<Cell> getDependsOn() {
        return this.dependsOn;
    }

    public Object getEffectiveValue() {
        return this.effectiveValue;
    }

    public String getOriginalValue() {
        return this.originalValue;
    }

    public int getLastVersion() {
        return this.lastVersionChange;
    }

    @Override
    public void setName(String name) {
        this.cellName = name;
    }
    @Override
    public void setPreviousVal(Cell previousVal) {
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

    public void setLastVersion(int lastVersion) {
        this.lastVersionChange = lastVersion;
    }

    public void setOriginalValue(String originalValue) {
        this.originalValue = originalValue;
    }

    public void setEffectiveValue(Object effectiveValue) {
        this.effectiveValue = effectiveValue;
    }

    public void calcEffectiveValue(SheetWriter sheet) {
        Expression expression = FunctionParser.parseExpression(originalValue, sheet, this);

            if (expression == null) {
                throw new NullPointerException("Expression is not set for this cell.");
            }
            this.effectiveValue = expression.eval().getValue();


    }
    public void printDependsOn(){
        for (Cell cell : dependsOn) {
            System.out.println((cell.getName()));
        }

    }
    public void printInfluencingOn(){
        for(Cell cell:this.influencingOn){
            System.out.println(cell.getName());
        }
    }
    public void cancelDependencies(){
        List<Cell> dependsOnCellsCopy = new ArrayList<>(this.getDependsOn());

        for(Cell dependsOnCell : dependsOnCellsCopy) {
            dependsOnCell.getInfluencingOn().remove(this);
        }
        this.getDependsOn().clear();
    }
    @Override
    public void displaySingleCell() {
        System.out.println("-Cell " + this.getName() + " info-");
        if(this!=null){
            System.out.println("Original value: " + this.getOriginalValue());
            System.out.println("Effective value: " + this.getEffectiveValue());
            System.out.println("Last version changed: " + this.getLastVersion());
            System.out.println("depend on:");
            this.printDependsOn();
            System.out.println("influencing on:");
            this.printInfluencingOn();
        }
        else{
            System.out.println("Cell " +this.getName()+ " is Empty");
        }

    }


}

