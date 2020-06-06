import org.assertj.core.api.ThrowableAssert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class CalculatorTest {

    private Calculator calculator;
    private NumberProvider numberProvider;

    @Before
    public void setUp() {
        this.numberProvider = Mockito.mock(NumberProvider.class);
        this.calculator = new Calculator(10, numberProvider);
    }

    @Test
    public void add_whenGive5_shouldReturn15() {

        int b = 5;

        int sum = this.calculator.add(b);

        assertThat(sum).isEqualTo(15);
    }

    @Test
    public void divide_whenGive2_shouldReturn5() {

        int b = 2;

        float quotient = this.calculator.divide(b);

        assertThat(quotient).isEqualTo(5);
    }

    @Test
    public void divide_whenGive0_shouldThrowIllegalArgumentException() {

        int b = 0;

        ThrowableAssert.ThrowingCallable method = () -> this.calculator.divide(b);
        assertThatIllegalArgumentException().isThrownBy(method);
    }

    @Test
    public void addToNumber_whenProvide1_shouldReturn11() {

        Mockito.when(this.numberProvider.provideNumber()).thenReturn(1);

        int sum = this.calculator.addToNumber();

        assertThat(sum).isEqualTo(11);
    }

}
