package engine.api;

public interface Coordinate {
    int getRow();
    int getColumn();
    void setRow(int row);
    void setColumn(int column);
    boolean equals(Object obj);
    int hashCode();
}
