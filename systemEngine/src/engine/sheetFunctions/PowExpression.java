package engine.sheetFunctions;

import engine.value.CellType;
import engine.value.EffectiveValue;
import engine.value.EffectiveValueImpl;

public class PowExpression implements Expression {

    private final Expression base , exponent;

    public PowExpression(Expression base, Expression exponent) {
        this.base = base;
        this.exponent = exponent;
    }
    public CellType getFunctionResultType(){
        return CellType.NUMERIC;
    }

    public EffectiveValue eval()
    {
        EffectiveValue baseValue = base.eval();
        EffectiveValue exponentValue = exponent.eval();

        double result = Math.pow(baseValue.extractValueWithExpectation(Double.class),  exponentValue.extractValueWithExpectation(Double.class));
        return new EffectiveValueImpl(CellType.NUMERIC, result);

    }
}
