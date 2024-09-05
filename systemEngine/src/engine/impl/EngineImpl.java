package engine.impl;
import engine.api.*;
import jakarta.xml.bind.JAXBException;
import jaxb.generated.STLCell;
import jaxb.generated.STLLayout;
import jaxb.generated.STLSheet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EngineImpl implements Engine {

    private SheetReader sheetReader = null;
    private SheetWriter sheetWriter = null;
    private VersionsHandler versionsHandler;
    private FileManager fileManager;


    public void loadXMLFile(String filename) throws JAXBException {
        if (!filename.toLowerCase().endsWith(".xml")) {
            throw new IllegalArgumentException("Invalid file type. Please provide an XML file.");
        }
        STLSheet stlSheet;
        stlSheet = fileManager.loadXMLFile(filename);
        if (!checkIfValidLayoutSize(stlSheet.getSTLLayout())) {
            throw new IllegalStateException("Illegal Layout Size");
        }

        setSheet(stlSheet);
    }
    @Override
    public void setSheet(STLSheet stlSheet) {
    try {
        SheetImpl sheet = new SheetImpl(stlSheet.getName(),
                stlSheet.getSTLLayout().getRows(),
                stlSheet.getSTLLayout().getColumns(),
                stlSheet.getSTLLayout().getSTLSize().getColumnWidthUnits(),
                stlSheet.getSTLLayout().getSTLSize().getRowsHeightUnits());

        Map<Coordinate, Cell> sheetMap = sheet.getSheet();

        for (STLCell stlCell : stlSheet.getSTLCells().getSTLCell()) {
            String cellIdentity = stlCell.getColumn() + stlCell.getRow();
            if (sheet.checkIfValidCell(cellIdentity)) {
                sheet.setCell(cellIdentity, stlCell.getSTLOriginalValue());
                sheet.getCell(cellIdentity).calcEffectiveValue(sheet);
            } else
                throw new IllegalStateException("Cell " + cellIdentity + "in the sheet is out of bounds. Failed to load sheet");
        }
        sheet.setSheet(sheetMap);
        if (!sheetMap.isEmpty()) {
            sheet.getChangedCellInVersion().add(sheetMap.size());
        } else {
            sheet.getChangedCellInVersion().add(0);
        }
        SheetDTO sheetDTO = sheet.toDTO();
        sheet.getVersionHistory().add(sheetDTO);

        sheetReader = sheet;
        sheetWriter = sheet;

    } catch (Exception e) {
        throw e;
    }


    }
    public EngineImpl() {
        this.fileManager = new FileManagerImpl();
    }
    @Override
    public boolean checkIfValidCell(String s){
        return sheetReader.checkIfValidCell(s);
    }
    @Override
    public void updateCell(String cellIdentity,String value){
        try {
            sheetWriter.updateCell(cellIdentity, value);
            SheetDTO sheetDTO = sheetWriter.toDTO();
            sheetWriter.getVersionHistory().add(sheetDTO);
        } catch (Exception e) {
            throw e;
        }

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
    public int getNumOfRows(){
        return sheetReader.getSheetLayout().getNumOfRows();
    }
    @Override
    public int getNumOfCols(){
        return sheetReader.getSheetLayout().getNumOfCols();
    }
    @Override
    public String getColumnLetter(int columnNumber){
        return sheetReader.getColumnLetter(columnNumber);
    }
    @Override
    public List<Integer>  getVersionChanges(){
        return sheetReader.getChangedCellCount();
    }
    public SheetDTO getVersion(int version)  {
        return sheetReader.getVersionHistory().get(version);

    }
    public SheetReader getSheetReader() {
        return sheetReader;
    }
    public void setCellToUndefined(String cellIdentity){
        Cell cell = getCell(cellIdentity);
        sheetWriter.setCellToUndefined(cell);
    }
    public boolean checkIfValidLayoutSize(STLLayout layout){
        if(layout.getColumns() > 20){
            return false;
        }
        if(layout.getRows() > 50){
            return false;
        }
        return true;
    }


}
