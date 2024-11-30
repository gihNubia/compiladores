package lexic;

public class Token {
    public final Tag tag;
    public Token (Tag t){
        tag = t;
    }
    public String toString(){
        return "[ " + this.getTagString() + ", " + this.getValueString() + " ]";
    }
    public String getTagString() {
        return String.valueOf(tag);
    }
    public String getValueString() {
        return "";
    }
}

