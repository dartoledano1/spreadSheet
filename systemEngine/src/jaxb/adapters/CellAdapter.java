package jaxb.adapters;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import engine.api.Cell;
import engine.impl.CellImpl;
import engine.api.Coordinate;

public class CellAdapter extends XmlAdapter<CellImpl, Cell> {

    @Override
    public CellImpl marshal(Cell cell) throws Exception {
        if (cell instanceof CellImpl) {
            return (CellImpl) cell;
        }
        return new CellImpl(cell.getCoordinate().getRow(), cell.getCoordinate().getRow(), cell.getOriginalValue(), cell.getLastVersion(), cell.getName());
    }

    @Override
    public Cell unmarshal(CellImpl v) throws Exception {
        return v;  // Simply return the concrete implementation
    }
}
