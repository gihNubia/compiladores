package lexical;

public enum Tag {
    START,
    EXIT,
    INT,
    FLOAT,
    STRING,
    IF,
    THEN,
    END,
    ELSE,
    DO,
    WHILE,
    SCAN,
    PRINT,

    AT, // =
    EQ, // ==
    GT, // >
    LT, // <
    GE, // >=
    LE, // <=
    NE, // !=
    PLUS, // +
    MINUS, // -
    OR, // ||
    MULTIPLY, // *
    DIVIDE, // /
    MODULUS, // %
    AND, // &&
    SC, // ;
    CL, // ,
    NT, // !
    OP, // (
    CP, // )

    ID,
    FLOAT_C,
    LITERAL,

    // ERROR
    ERROR;
}
