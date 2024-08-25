package engine.sheetFunctions;
import engine.value.CellType;
import engine.value.EffectiveValue;

public interface Expression {

    EffectiveValue eval();
    CellType getFunctionResultType();

}
