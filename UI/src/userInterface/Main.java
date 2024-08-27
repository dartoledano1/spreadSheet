package userInterface;
import engine.api.*;
import engine.impl.EngineImpl;
import engine.impl.SheetImpl;

public class Main {
    public static void main(String[] args) {

        SheetImpl sheet = new SheetImpl("my Sheet", 50, 20);
        Engine engine = new EngineImpl(sheet);
        Ui ui = new Ui(engine);
        ui.start();
    }
}