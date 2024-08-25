package engine.impl;
import engine.api.Coordinate;

import java.util.Objects;

public class CoordinateImpl implements Coordinate {
    private int row;
    private int col;

    public CoordinateImpl(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public int getRow (){
        return row;
    }

    @Override
    public int getColumn (){
        return col;
    }
    @Override
    public void setRow(int row){
        this.row = row;
    }
    @Override
    public void setColumn(int column){
        this.col = column;
    }
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CoordinateImpl that = (CoordinateImpl) obj;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }


}
