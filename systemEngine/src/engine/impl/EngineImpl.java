package engine.impl;
import engine.api.*;

import java.io.File;

public class EngineImpl implements Engine {

    private Sheet sheet;

    public EngineImpl(Sheet sheet) {
        this.sheet=sheet;
    }
    @Override
    public File loadFromFile(String filename) {
        return null;
        //impl
    }
    @Override
    public void printSheet() {
        sheet.printSheet();
    }
    @Override
    public void displaySingleCell(String cellIdentity){
        sheet.displaySingleCell(cellIdentity);

    }
    @Override
    public boolean checkIfValidCell(String s){
        return sheet.checkIfValidCell(s);
    }
    @Override
    public boolean updateCell(String cellIdentity,String value){
       return sheet.updateCell(cellIdentity,value);
    }

    @Override
    public void updateInfluencingCells(Cell cell, Sheet sheet){
        sheet.updateInfluencingCells(cell);
    }
    @Override
    public Cell getCell(String cell){
        return sheet.getCell(cell);
    }
    @Override
    public void resetCell(Cell cell){
        sheet.resetCell(cell);
    }
    @Override
    public void displayVersions(){
        sheet.displayVersions();
    }
    public int getNumOfRows(){
        return sheet.getNumOfRows();
    }
    public int getNumOfCols(){
        return sheet.getNumOfCols();
    }
    public String getColumnLetter(int columnNumber){
        return sheet.getColumnLetter(columnNumber);
    }

}
