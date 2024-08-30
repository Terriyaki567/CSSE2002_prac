package debugging;

import static org.junit.Assert.*;
import org.junit.Test;

public class RecursiveFibonacciTest {

    @Test
    public void returnsOne() {
        // when we call calculate with x = 1, the function boils down to F(n) = F(n - 1)
        // so it'll all end up being 1
        assertEquals(1, RecursiveFibonacci.calculate(20, 1));
    }

    @Test
    public void fibonacci() {
        // when we call calculate with x = 2, it's a fibonacci function
        assertEquals(233, RecursiveFibonacci.calculate(13, 2));
    }
}
