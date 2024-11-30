package lexic;

public class Word extends Token{
    private String lexeme = "";

    public static final Word equal = new Word("==", Tag.EQ);
    public static final Word gt = new Word(">", Tag.GT);
    public static final Word ge = new Word(">=", Tag.GE);
    public static final Word lt = new Word("<", Tag.LT);
    public static final Word le = new Word("<=", Tag.LE);
    public static final Word ne = new Word("!=", Tag.NE);
    public static final Word plus = new Word("+", Tag.PLUS);
    public static final Word minus = new Word("-", Tag.MINUS);
    public static final Word or = new Word("||", Tag.OR);
    public static final Word multiply = new Word("*", Tag.MULTIPLY);
    public static final Word divide = new Word("/", Tag.DIVIDE);
    public static final Word modulus = new Word("%", Tag.MODULUS);
    public static final Word and = new Word("&&", Tag.AND);
    public static final Word at = new Word("=", Tag.AT);
    public static final Word cl = new Word(",", Tag.CL);
    public static final Word sc = new Word(";", Tag.SC);
    public static final Word nt = new Word("!", Tag.NT);
    public static final Word op = new Word("(", Tag.OP);
    public static final Word cp = new Word(")", Tag.CP);

    public Word (String s, int tag){
        super(tag);
        lexeme = s;
    }
  
    public String toString(){
        return "" + lexeme;
    }

    public String getLexeme() {
        return lexeme;
    }
}
