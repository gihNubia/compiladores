public class Float_c extends Token {
    double value;
    public Float_c(double value) {
        super(Tag.FLOAT_C);
        this.value = value;
    }

    public String toString() {
        return "" + value;
    }

}
