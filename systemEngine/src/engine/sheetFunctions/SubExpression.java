package engine.sheetFunctions;

import engine.value.CellType;
import engine.value.EffectiveValue;
import engine.value.EffectiveValueImpl;

public class SubExpression implements Expression {
    private final Expression source, startInedx, endIndex;
    public SubExpression(Expression source, Expression startInedx, Expression endInedx) {
        this.source = source;
        this.startInedx = startInedx;
        this.endIndex = endInedx;
    }

    public CellType getFunctionResultType(){
        return CellType.STRING;
    }

    @Override
    public EffectiveValue eval() {
        EffectiveValue sourceValue = source.eval();
        EffectiveValue startValue = startInedx.eval();
        EffectiveValue endValue = endIndex.eval();

        String str = sourceValue.extractValueWithExpectation(String.class);
        int startIndex = startValue.extractValueWithExpectation(Integer.class);
        int endIndex = endValue.extractValueWithExpectation(Integer.class);

        String result = str.substring(startIndex, endIndex);

        return new EffectiveValueImpl(CellType.STRING, result);

    }
}
