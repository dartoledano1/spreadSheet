package engine.api;

import engine.impl.CellDTO;
import engine.impl.CellImpl;
import engine.value.CellType;

import java.util.Set;

public interface Cell {

    Object getEffectiveValue();
    String getOriginalValue();
    int getLastVersion();
    Set<Cell> getDependsOn();
    Set<Cell> getInfluencingOn();
    String getName();
    CellImpl getPreviousVal();
    Coordinate getCoordinate();
    CellType getCellType();
    void copyCell(CellImpl other);

    void setLastVersion(int lastVersion);
    void setOriginalValue(String originalValue);
    void setEffectiveValue(Object effectiveValue);
    void setName(String name);
    void setPreviousVal(CellImpl previousVal);
    boolean calcEffectiveValue(SheetWriter sheet);
    void setDependsOn(Set<Cell> dependsOn);
    void setInfluencingOn(Set<Cell> influencingOn);
    void cancelDependencies();
    void updatePreviousVal();
    void setCellToUndefined();
    void setCellType(CellType cellType);
    CellDTO toDTO();
    CellImpl fromDTO(CellDTO dto);
}
