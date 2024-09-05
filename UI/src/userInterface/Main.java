package userInterface;
import engine.api.*;
import engine.impl.EngineImpl;
import engine.impl.FileManagerImpl;
import engine.impl.SheetImpl;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws JAXBException, IOException, ClassNotFoundException {

        Engine engine = new EngineImpl();
        Ui ui = new Ui(engine);
        ui.start();
    }
}