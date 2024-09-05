package engine.impl;

import java.io.Serializable;

public class SheetLayoutDTO implements Serializable {
    private int numOfCols;
    private int numOfRows;
    private int colsWidth;
    private int rowsHeights;

    public SheetLayoutDTO(int numOfCols, int numOfRows, int colsWidth, int rowsHeights) {
        this.numOfCols = numOfCols;
        this.numOfRows = numOfRows;
        this.colsWidth = colsWidth;
        this.rowsHeights = rowsHeights;
    }
    public int getNumOfCols() {
        return numOfCols;
    }
    public int getNumOfRows() {
        return numOfRows;
    }
    public int getColsWidth() {
        return colsWidth;
    }
    public int getRowsHeights() {
        return rowsHeights;
    }

    public void setNumOfCols(int numOfCols) {
        this.numOfCols = numOfCols;
    }
    public void setNumOfRows(int numOfRows) {
        this.numOfRows = numOfRows;
    }
    public void setColsWidth(int colsWidth) {
        this.colsWidth = colsWidth;
    }
    public void setRowsHeights(int rowsHeights) {
        this.rowsHeights = rowsHeights;
    }




}
