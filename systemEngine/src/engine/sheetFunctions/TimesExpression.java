package engine.sheetFunctions;

import engine.value.CellType;
import engine.value.EffectiveValue;
import engine.value.EffectiveValueImpl;

public class TimesExpression implements Expression {
    private final Expression left, right;
    public TimesExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    public CellType getFunctionResultType(){
        return CellType.NUMERIC;
    }
    public EffectiveValue eval(){
        EffectiveValue LeftValue = left.eval();
        EffectiveValue RightValue = right.eval();

        double result = LeftValue.extractValueWithExpectation(Double.class) * RightValue.extractValueWithExpectation(Double.class);
        return new EffectiveValueImpl(CellType.NUMERIC, result);
    }

}
