package validator;

import java.util.Arrays;
import java.util.EnumSet;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Test validator with Parameterized test.
 *
 * @author Pieter van den Hombergh {@code p.vandenhombergh@fontys.nl}
 */
public class ValidatorTest {

    @ParameterizedTest
    @CsvSource({
        "password, 8U#s",
        "SOMETHING, l#8s"
    })
    public void invalidPasswordTest(String password, String encoding) {
        
        EnumSet<Flaw> flaws = EnumSet.allOf(Flaw.class);

        for (Flaw flaw : flaws) {
            if (encoding.indexOf(flaw.getEncoding()) == (-1)) {
                flaws.remove(flaw);
            }
        }

        StringBuilder expectedError = new StringBuilder();
        for (Flaw flaw : flaws) {
            expectedError.append(flaw.getDescription()).append(" ");
        }
        
        Validator validator = new Validator();
        
        assertThatThrownBy(() -> {
            validator.validate(password);
        }).isInstanceOf(InvalidPasswordException.class)
                .hasMessage(expectedError.toString());
    }

    @ParameterizedTest
    @CsvSource({
        "Password#123, ",
        "testTEST@123, "
    })
    public void validPasswordTest(String password, String encoding) {
        Validator validator = new Validator();
        
        assertThatCode(() -> {
            validator.validate(password);
        }).doesNotThrowAnyException();
    }
}
