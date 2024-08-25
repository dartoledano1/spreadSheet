package userInterface;
import engine.api.*;
import engine.api.Sheet;
import engine.impl.EngineImpl;
import engine.impl.SheetImpl;

public class Main {
    public static void main(String[] args) {

        Sheet sheet = new SheetImpl("my Sheet" , 50 , 20);
        Engine engine = new EngineImpl(sheet);
        Ui ui = new Ui(engine);
        ui.start();
        sheet.printSheet();
    }
}