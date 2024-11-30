package lexic;

public class InvalidToken extends Token{
    String value;
    public InvalidToken(String value) {
        super(Tag.ERROR);
        this.value = value;
    }

    public String getValueSting(){
        return "Invalid Token: \"" + value + "\"";
    }
}