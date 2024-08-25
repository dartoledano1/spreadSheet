package engine.sheetFunctions;

import engine.value.CellType;
import engine.value.EffectiveValue;
import engine.value.EffectiveValueImpl;

public class IndendityExpression implements Expression {

    private final Object value;
    private final CellType celltype;

    public IndendityExpression(Object value, CellType celltype) {
        this.value = value;
        this.celltype = celltype;
    }
    public CellType getFunctionResultType(){
        return celltype;
    }

    public EffectiveValue eval(){
        return new EffectiveValueImpl(celltype, value);
    }
}
