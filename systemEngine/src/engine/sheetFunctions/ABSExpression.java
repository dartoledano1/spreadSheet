package engine.sheetFunctions;

import engine.value.CellType;
import engine.value.EffectiveValue;
import engine.value.EffectiveValueImpl;

public class ABSExpression implements Expression {

    private Expression number;
    public ABSExpression(Expression number) {
        this.number = number;
    }
    public CellType getFunctionResultType(){
        return CellType.NUMERIC;
    }

    @Override
    public EffectiveValue eval() {

        EffectiveValue value = number.eval();
        double result = value.extractValueWithExpectation(Double.class);
        return new EffectiveValueImpl(CellType.NUMERIC, Math.abs(result));

    }
}
