package engine.sheetFunctions;

import engine.value.CellType;
import engine.value.EffectiveValue;
import engine.value.EffectiveValueImpl;

public class ConcatExpression implements Expression {
    private final Expression left, right;

    public ConcatExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    public CellType getFunctionResultType(){
        return CellType.STRING;
    }
    public EffectiveValue eval(){
        EffectiveValue leftValue = left.eval();
        EffectiveValue rightValue = right.eval();

        String result = leftValue.extractValueWithExpectation(String.class) + rightValue.extractValueWithExpectation(String.class);
        return new EffectiveValueImpl(CellType.STRING, result);
    }
}
