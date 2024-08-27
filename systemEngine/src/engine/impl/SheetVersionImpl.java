package engine.impl;
import engine.api.Cell;
import engine.api.Coordinate;
import engine.api.VersionsHandler;

import java.util.*;

public class SheetVersionImpl implements VersionsHandler {
    private int versionNumber;
    private Map<Coordinate, Cell> sheetState;

    public SheetVersionImpl(int versionNumber, Map<Coordinate, Cell> sheetState) {
        this.versionNumber = versionNumber;
        this.sheetState = new HashMap<>(sheetState);
    }

    @Override
    public int getVersionNumber() {
        return versionNumber;
    }
    @Override
    public Map<Coordinate, Cell> getSheetState() {
        return new HashMap<>(sheetState);
    }
    @Override
    public void setVersionNumber(int version)
    {
        this.versionNumber = version;
    }
    @Override
    public void setSheetState(Map<Coordinate, Cell> state){
        this.sheetState = new HashMap<>(state);
    }
    @Override
    public void displayVersions(){

    }
}
