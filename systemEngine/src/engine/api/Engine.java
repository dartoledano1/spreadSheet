package engine.api;

import engine.impl.SheetDTO;
import engine.impl.SheetImpl;
import jakarta.xml.bind.JAXBException;
import jaxb.generated.STLSheet;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Engine {
    boolean checkIfValidCell(String s);
    void updateCell(String cellIdentity,String value);
    Cell getCell(String cell);
    void resetCell(Cell cell);
    int getNumOfRows();
    int getNumOfCols();
    String getColumnLetter(int columnNumber);
    List<Integer> getVersionChanges();
    SheetReader getSheetReader();

    //jaxb generate functions
    void loadXMLFile(String filename) throws JAXBException;
    void setSheet(STLSheet stlSheet);
    SheetDTO getVersion(int version);
    void setCellToUndefined(String cellIdentity);



}
