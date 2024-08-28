package engine.api;
import engine.impl.SheetImpl;
import jakarta.xml.bind.JAXBException;

public interface FileManager {
    SheetImpl loadFile(String XmlFilePath) throws JAXBException;
}
