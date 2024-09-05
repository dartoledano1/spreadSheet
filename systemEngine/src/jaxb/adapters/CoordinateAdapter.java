package jaxb.adapters;
import engine.api.Coordinate;
import engine.impl.CoordinateImpl;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class CoordinateAdapter extends XmlAdapter <CoordinateImpl, Coordinate> {

    @Override
    public CoordinateImpl marshal(Coordinate v) throws Exception {
        // Converts the Coordinate interface to the CoordinateImpl class
        if (v instanceof CoordinateImpl) {
            return (CoordinateImpl) v;
        }
        // If not already an implementation, create a new one (adjust as necessary)
        return new CoordinateImpl(v.getRow(), v.getColumn());
    }
    @Override
    public Coordinate unmarshal(CoordinateImpl v) throws Exception {
        // Converts the CoordinateImpl class back to the Coordinate interface
        return v;
    }
}
