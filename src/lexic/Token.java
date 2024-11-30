package lexic;

public class Token {
    public final int tag;
    public Token (int t){
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

