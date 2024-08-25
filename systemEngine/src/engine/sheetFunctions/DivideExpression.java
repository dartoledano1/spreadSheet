package engine.sheetFunctions;

import engine.value.CellType;
import engine.value.EffectiveValue;
import engine.value.EffectiveValueImpl;

public class DivideExpression implements Expression {
    private final Expression left,right;
    public DivideExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;

    }
    public CellType getFunctionResultType(){
        return CellType.NUMERIC;
    }
    @Override
    public EffectiveValue eval() {
        EffectiveValue LeftValue = left.eval();
        EffectiveValue RightValue = right.eval();

        double rightValue = RightValue.extractValueWithExpectation(Double.class);

        if (rightValue == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        } else {
            double result = LeftValue.extractValueWithExpectation(Double.class) + RightValue.extractValueWithExpectation(Double.class);
            return new EffectiveValueImpl(CellType.NUMERIC, result);
        }
    }


}
