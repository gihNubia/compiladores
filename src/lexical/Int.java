package lexical;

public class Int extends Token{
    int value;
    public Int(int value) {
        super(Tag.INT);
        this.value = value;
    }

    public String getValueString() {
        return "" + value;
    }
}
