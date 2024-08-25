package engine.value;

public interface EffectiveValue {

    CellType getType();
    Object getValue();
    void setValue(Object value);
    void setType(CellType type);
    public <T> T extractValueWithExpectation(Class<T> expectedType);


}
