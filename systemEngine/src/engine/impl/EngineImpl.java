package engine.impl;
import engine.api.*;

import java.io.File;

public class EngineImpl implements Engine {

    private SheetReader sheetReader;
    private SheetWriter sheetWriter;
    private VersionsHandler versionsHandler;


    public EngineImpl(SheetImpl sheet) {
        this.sheetReader=sheet;
        this.sheetWriter=sheet;
    }
    @Override
    public File loadFromFile(String filename) {
        return null;
        //impl
    }
    @Override
    public void printSheet() {
        sheetReader.printSheet();
    }
    @Override
    public void displaySingleCell(String cellIdentity){

        Cell cell = sheetReader.getCell(cellIdentity);
        if (cell != null) {
            cell.displaySingleCell();
        }

    }
    @Override
    public boolean checkIfValidCell(String s){
        return sheetReader.checkIfValidCell(s);
    }
    @Override
    public boolean updateCell(String cellIdentity,String value){
       return sheetWriter.updateCell(cellIdentity,value);
    }

    @Override
    public Cell getCell(String cell){
        return sheetReader.getCell(cell);
    }
    @Override
    public void resetCell(Cell cell){
        sheetWriter.resetCell(cell);
    }
    @Override
    public void displayVersions(){
        versionsHandler.displayVersions();
    }
    public int getNumOfRows(){
        return sheetReader.getNumOfRows();
    }
    public int getNumOfCols(){
        return sheetReader.getNumOfCols();
    }
    public String getColumnLetter(int columnNumber){
        return sheetReader.getColumnLetter(columnNumber);
    }

}
