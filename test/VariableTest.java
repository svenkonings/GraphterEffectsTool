import org.antlr.v4.runtime.ParserRuleContext;

/**
 *
 */
public class VariableTest extends GrammarTest {

    private static final String[] VALID_SAMPLES = new String[]
            {
                    "A",
                    "ABC",
                    "Aa",
                    "A_bc",
                    "A_",
                    "A1"
            };
    private static final String[] INVALID_SAMPLES = new String[]
            {
                    "",
                    "aA",
                    "_A",
                    "#$%",
                    "a",
                    "1A",
                    "1"
            };

    @Override
    protected String[] getValidSamples() {
        return VALID_SAMPLES;
    }

    @Override
    protected String[] getInvalidSamples() {
        return INVALID_SAMPLES;
    }

    @Override
    protected ParserRuleContext parse(GraafvisParser parser) {
        return parser.variable();
    }

    @Override
    protected String getRuleName() {
        return "variable";
    }


}
