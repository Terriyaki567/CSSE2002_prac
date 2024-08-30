package debugging;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CachedFibonacciTest {

    private CachedFibonacci cache;

    @Before
    public void setup() {
        // seems like a good default cache size
        cache = new CachedFibonacci(20);
    }

    @Test
    public void baseCase() {
        // make sure that the caching doesn't mess with the easy stuff
        assertEquals(1, cache.calculate(1));
        assertEquals(1, cache.calculate(2));
    }

    @Test
    public void basic() {
        assertEquals(2, cache.calculate(3));
        assertEquals(3, cache.calculate(4));
    }

    @Test
    public void big() {
        assertEquals(233, cache.calculate(13));
    }
}
