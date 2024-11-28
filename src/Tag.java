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

            AT = 20, // =
            EQ = 21, // ==
            GT = 22, // >
            LT = 23, // <
            GE = 24, // >=
            LE = 25, // <=
            NE = 26, // !=
            PLUS = 27, // +
            MINUS = 28, // -
            OR = 29, // ||
            MULTIPLY = 30, // *
            DIVIDE = 31, // /
            MODULUS = 32, // %
            AND = 33, // &&
            SC = 38, // ;
            CL= 39, // ,
            NT = 40, // !

            INTEGER_C = 50,
            ID = 51,
            FLOAT_C = 51,
            CHAR = 52,
            LETTER = 53,
            DIGIT = 54,
            LITERAL = 55,
            CONST = 56;

}