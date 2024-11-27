import javax.sound.sampled.BooleanControl;

public class Tag {

    public final static int
            START = 1,
            EXIT = 2,
            INT = 3,
            FLOAT = 4,
            STRING = 5,
            IF = 6,
            THEN = 7,
            END = 8,
            ELSE = 9,
            DO = 10,
            WHILE = 11,
            SCAN = 12,
            PRINT = 13,

            EQ = 14, // ==
            GT = 15, // >
            LT = 16, // <
            GE = 17, // >=
            LE = 18, // <=
            NE = 19, // !=
            PLUS = 20, // +
            MINUS = 21, // -
            OR = 22, // ||
            MULTIPLY = 23, // *
            DIVIDE = 25, // /
            MODULUS = 26, // %
            AND = 27, // &&
            OB = 28, // {
            CB = 29, // }
            POINT = 30, // .
            UL = 31, // _

            INTEGER_C = 32,
            ID = 33,
            FLOAT_C = 34,
            CHAR = 35,
            LETTER = 36,
            DIGIT = 37,
            LITERAL = 38,
            CONST = 39;

}