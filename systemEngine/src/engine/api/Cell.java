package engine.api;

import java.util.Set;

public interface Cell {

    Object getEffectiveValue();
    String getOriginalValue();
    int getLastVersion();
    Set<Cell> getDependsOn();
    Set<Cell> getInfluencingOn();
    String getName();
    Cell getPreviousVal();
    void displaySingleCell();
    void setLastVersion(int lastVersion);
    void setOriginalValue(String originalValue);
    void setEffectiveValue(Object effectiveValue);
    void setName(String name);
    void setPreviousVal(Cell previousVal);
    void calcEffectiveValue(SheetWriter sheet);
    void setDependsOn(Set<Cell> dependsOn);
    void setInfluencingOn(Set<Cell> influencingOn);
    void printDependsOn();
    void printInfluencingOn();
    void cancelDependencies();
    void updatePreviousVal();



}
