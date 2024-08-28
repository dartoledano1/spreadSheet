package engine.impl;
import engine.api.FileManager;
import engine.api.SheetWriter;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;


public class FileManagerImpl implements FileManager {

    public SheetImpl loadFile(String xmlFilePath) throws JAXBException {

        try {

            File file = new File(xmlFilePath);
            JAXBContext jaxbContext = JAXBContext.newInstance(SheetImpl.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (SheetImpl) jaxbUnmarshaller.unmarshal(file);


        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
