package engine.api;
import engine.impl.SheetImpl;
import jakarta.xml.bind.JAXBException;
import jaxb.generated.STLSheet;

public interface FileManager {
    STLSheet loadXMLFile(String XmlFilePath) throws JAXBException;
}
