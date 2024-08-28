package engine.impl;

import engine.api.SheetLayout;

public class SheetLayoutImp implements SheetLayout {
    private int numOfCols;
    private int numOfRows;
    private int[] colsWidth;
    private int[] rowsHeights;

    public SheetLayoutImp(int numOfCols, int numOfRows, int[] colsWidth, int[] rowsHeights) {
        this.numOfCols = numOfCols;
        this.numOfRows = numOfRows;
        this.colsWidth = colsWidth;
        this.rowsHeights = rowsHeights;
    }
    @Override
    public int getNumOfRows(){
        return numOfRows;
    }
    @Override
    public int getNumOfCols(){
        return numOfCols;
    }

    @Override
    public int[] getColsWidth(){
        return colsWidth;
    }
    @Override
    public int[] getRowsHeight(){
        return rowsHeights;
    }

    @Override
    public void  setNumOfRows(int numOfRows){
        this.numOfRows = numOfRows;
    }
    @Override
    public void setNumOfColumns(int numOfCols){
        this.numOfCols = numOfCols;
    }


}
