package lexic;

public class Literal extends Token{
    String value;
    public Literal(String value) {
        super(Tag.LITERAL);
        this.value = value;
    }

    public String toString(){
        return "" + value;
    }
}
