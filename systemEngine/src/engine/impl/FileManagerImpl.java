package engine.impl;
import engine.api.FileManager;
import engine.api.SheetWriter;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jaxb.generated.STLSheet;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;


public class FileManagerImpl implements FileManager {

    public STLSheet loadXMLFile(String xmlFilePath) throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(STLSheet.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        File file = new File(xmlFilePath);

        return (STLSheet) jaxbUnmarshaller.unmarshal(file);
    }

}
