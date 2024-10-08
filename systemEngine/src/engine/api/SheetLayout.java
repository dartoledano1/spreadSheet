package engine.api;

import engine.impl.SheetLayoutDTO;

public interface SheetLayout {
    //gets
    int getNumOfRows();
    int getNumOfCols();
    int getColsWidth();
    int getRowsHeight();

    //sets
    void setNumOfRows(int numOfRows);
    void setNumOfColumns(int numOfCols);
}
