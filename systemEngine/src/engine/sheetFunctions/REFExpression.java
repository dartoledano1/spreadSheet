package engine.sheetFunctions;
import engine.api.Cell;
import engine.api.SheetReader;
import engine.api.SheetWriter;
import engine.value.CellType;
import engine.value.EffectiveValue;
import engine.value.EffectiveValueImpl;

public class REFExpression implements Expression {
    private String ref;
    private SheetWriter sheet;
    private Cell REFCell;
    private Cell currentCell;

    public REFExpression(String ref, SheetWriter sheet, Cell currentCell) {
        this.ref = ref.toUpperCase();
        this.sheet = sheet;
        if(sheet.getCell(ref.toUpperCase()) == null){
            addEmptyCell();
        }
        this.REFCell = sheet.getCell(ref.toUpperCase());
        this.currentCell = currentCell;
    }

    public CellType getFunctionResultType(){

        Object value = REFCell.getEffectiveValue();

        if(value instanceof String){
            if(((String) value).equalsIgnoreCase("undefined")){
                return CellType.UNDEFINED;
            }
            else {
                return CellType.STRING;
            }
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

    public void addEmptyCell(){
        String value = " ";
        sheet.setCell(ref,value);
        sheet.getCell(ref).setEffectiveValue(value);
    }

    public Cell getRefCell() {
        return REFCell;
    }
}
