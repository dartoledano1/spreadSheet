package engine.sheetFunctions;
import engine.api.Cell;
import engine.api.Sheet;
import engine.value.CellType;
import engine.value.EffectiveValue;
import engine.value.EffectiveValueImpl;

public class REFExpression implements Expression {
    private String ref;
    private Sheet sheet;
    private Cell REFCell;
    private Cell currentCell;

    public REFExpression(String ref, Sheet sheet, Cell currentCell) {
        this.ref = ref;
        this.sheet = sheet;
        this.REFCell = sheet.getCell(ref);
        this.currentCell = currentCell;
    }

    public CellType getFunctionResultType(){

        Object value = REFCell.getEffectiveValue();

        if(value instanceof String){
            return CellType.STRING;
        }
        else if(value instanceof Integer){
            return CellType.NUMERIC;
        }
        else if(value instanceof Double){
            return CellType.NUMERIC;
        }
        else if(value instanceof Boolean){
            return CellType.BOOLEAN;
        }
        return CellType.NUMERIC;
    }

    @Override
    public EffectiveValue eval() {

        currentCell.getDependsOn().add(REFCell);
        REFCell.getInfluencingOn().add(currentCell);
        return new EffectiveValueImpl(getFunctionResultType(), REFCell.getEffectiveValue());
    }

    public Cell getRefCell() {
        return REFCell;
    }
}
