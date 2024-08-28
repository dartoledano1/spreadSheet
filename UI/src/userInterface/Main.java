package userInterface;
import engine.api.*;
import engine.impl.EngineImpl;
import engine.impl.FileManagerImpl;
import engine.impl.SheetImpl;
import jakarta.xml.bind.JAXBException;

public class Main {
    public static void main(String[] args) throws JAXBException {
        String filePath = "C:\\Users\\dar\\academit\\Java\\testFiles";
        FileManager file = new FileManagerImpl();
        SheetImpl sheet = file.loadFile(filePath);
        Engine engine = new EngineImpl(sheet);
        Ui ui = new Ui(engine);
        ui.start();
    }
}