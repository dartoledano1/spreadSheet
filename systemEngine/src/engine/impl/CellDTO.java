package engine.impl;
import engine.api.Cell;
import engine.api.Coordinate;

import java.io.Serializable;
import java.util.Set;

public class CellDTO implements Serializable {
    String originalValue;
    Object effectiveValue;
    int lastVersionChange;
    CoordinateDTO coordinate;
    private Set<Cell> dependsOn;
    private Set<Cell> influencingOn;
    private Cell previousVal;
    private String cellName;

    public CellDTO(String originalValue,
                   Object effectiveValue,
                   int lastVersionChange,
                   CoordinateDTO coordinate,
                   Set<Cell> dependsOn,
                   Set<Cell> influencingOn,
                   Cell previousVal,
                   String cellName){
        this.originalValue = originalValue;
        this.effectiveValue = effectiveValue;
        this.lastVersionChange = lastVersionChange;
        this.coordinate = coordinate;
        this.dependsOn = dependsOn;
        this.influencingOn = influencingOn;
        this.previousVal = previousVal;
        this.cellName = cellName;
    }
    public String getOriginalValue(){
        return originalValue;
    }
    public Object getEffectiveValue(){
        return effectiveValue;
    }
    public int getLastVersionChange(){
        return lastVersionChange;
    }
    public CoordinateDTO getCoordinate(){
        return coordinate;
    }
    public Set<Cell> getDependsOn(){
        return dependsOn;
    }
    public Set<Cell> getInfluencingOn(){
        return influencingOn;
    }
    public Cell getPreviousVal(){
        return previousVal;
    }
    public String getCellName(){
        return cellName;
    }

    public void setOriginalValue(String originalValue){
        this.originalValue = originalValue;
    }
    public void setEffectiveValue(Object effectiveValue){
        this.effectiveValue = effectiveValue;
    }
    public void setLastVersionChange(int lastVersionChange){
        this.lastVersionChange = lastVersionChange;
    }
    public void setCoordinate(CoordinateDTO coordinate){
        this.coordinate = coordinate;
    }
    public void setDependsOn(Set<Cell> dependsOn){
        this.dependsOn = dependsOn;
    }
    public void setInfluencingOn(Set<Cell> influencingOn){
        this.influencingOn = influencingOn;
    }
    public void setPreviousVal(Cell previousVal){
        this.previousVal = previousVal;
    }
    public void setCellName(String cellName){
        this.cellName = cellName;
    }


}
