import org.antlr.v4.runtime.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public abstract class GrammarTest {

    /**
     * Flags
     */

    private static final boolean SHOW_PROCESS = true;

    /**
     * Token samples
     */

    protected static final List<String> VALID_STRING_SAMPLES = Arrays.asList
            (
                    "\"a\"",
                    "\"\"",
                    "\"123\"",
                    "\"abc\"",
                    "\"a1b\"",
                    "\"a%^&#\"",
                    "\"a_b\""
            );
    protected static final List<String> VALID_NUMBER_SAMPLES = Arrays.asList
            (
                    "1",
                    "0123",
                    "0",
                    "921345156224167352"
            );
    protected static final List<String> VALID_ID_SAMPLES = Arrays.asList
            (
                    "a",
                    "a_b",
                    "aAa",
                    "a123",
                    "a_"
            );
    protected static final List<String> VALID_NUM_OP_SAMPLES = Arrays.asList
            (
                    "-",
                    "+",
                    "%",
                    "*",
                    "/",
                    "^"
            );
    protected static final List<String> VALID_EQ_OP_SAMPLES = Arrays.asList
            (
                    "==",
                    "<",
                    ">="
            );
    protected static final List<String> VALID_BOOL_OP_SAMPLES = Arrays.asList
            (
                    ",",
                    "and",
                    "or"
            );

    /**
     * Abstract methods
     */

    protected abstract List<String> getValidSamples();

    protected abstract List<String> getInvalidSamples();

    protected abstract ParserRuleContext parse(GraafvisParser parser);

    protected abstract String getRuleName();

    /**
     * Methods
     */

    @Test
    public void test() {
        for (String sample : getValidSamples()) {
            GraafvisParser parser = getGraafvisParser(sample);
            parse(parser);
            myAssertEquals(getRuleName(), sample, 0, countErrors(parser));
        }
        for (String sample : getInvalidSamples()) {
            GraafvisParser parser = getGraafvisParser(sample);
            parse(parser);
            myAssertNotEquals(getRuleName(), sample, 0, countErrors(parser));
        }
    }

    private static void myAssertEquals(String type, String sample, int expected, int actual) {
        if (SHOW_PROCESS) {
            System.out.printf("Parsed %s: %s. Expected %d errors. Found %d\n\r", type, sample, expected, actual);
        }
        Assert.assertEquals(sample, expected, actual);
    }

    private static void myAssertNotEquals(String type, String sample, int unexpected, int actual) {
        if (SHOW_PROCESS) {
            System.out.printf("Parsed %s: %s. Did not expect %d errors. Found %d\n\r", type, sample, unexpected, actual);
        }
        Assert.assertNotEquals(sample, unexpected, actual);
    }

    private static GraafvisParser getGraafvisParser(String text) {
        CharStream stream = new ANTLRInputStream(text);
        GraafvisLexer lexer = new GraafvisLexer(stream);
        TokenStream tokenStream = new CommonTokenStream(lexer);
        GraafvisParser parser = new GraafvisParser(tokenStream);
        ErrorListener errorListener = new ErrorListener();
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
        return parser;
    }

    private static int countErrors(GraafvisParser parser) {
        int count = 0;
        for (ANTLRErrorListener listener : parser.getErrorListeners()) {
            if (listener instanceof ErrorListener) {
                count += ((ErrorListener) listener).getErrors().size();
            }
        }
        return count;
    }


}
