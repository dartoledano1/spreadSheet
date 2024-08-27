package engine.api;
import java.util.Map;

public interface VersionsHandler {
    int getVersionNumber();
    Map<Coordinate, Cell> getSheetState();
    void setVersionNumber(int version);
    void setSheetState(Map<Coordinate, Cell> state);
    void displayVersions();
}
