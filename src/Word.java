public class Word extends Token{
    private String lexeme = "";

    public static final Word equal = new Word("==", Tag.AND);
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
    public static final Word ob = new Word("{", Tag.OB);
    public static final Word cb = new Word("", Tag.CB);
    //public static final Word underline = new Word("_", Tag.UL);

    public Word (String s, int tag){
        super(tag);
        lexeme = s;
    }
  
    public String toString(){
        return "" + lexeme;
    }

}
