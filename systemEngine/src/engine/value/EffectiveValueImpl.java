package engine.value;

public class EffectiveValueImpl implements EffectiveValue {
    CellType type;
    Object value;

    public EffectiveValueImpl(CellType type, Object value) {
        this.type = type;
        this.value = value;

    }
    public CellType getType() {
        return type;
    }
    public void setType(CellType type) {this.type = type;}
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }

    public <T> T extractValueWithExpectation(Class<T> expectedType) {
        if (expectedType.isInstance(value)) {
            return expectedType.cast(value);
        } else {
            throw new IllegalArgumentException(
                    "Expected type: " + expectedType.getName() + ", but found: " + (value != null ? value.getClass().getName() : "null")
            );
        }
    }

}
