package debugging;

/**
 * A simple, example fibonacci sequence calculator with a twist. A simple, recursive
 * implementation.
 * TODO - fix the bugs in this code
 */
public class RecursiveFibonacci {

    /**
     * Fibonacci function with a twist.
     * Consider the normal fibonacci function: F(n) = F(n - 1) + F(n - 2).
     * That function can be represented here as F(n) = calculate(n, 2).
     * The concept here is that
     *      calculate(n, x) = calculate(n - 1, x) + calculate(n - 2, x) + calculate(n - 3, x) + ...
     *              + calculate(n - x, x);
     *
     * @param n
     *      The number to calculate the special fibonacci of.
     * @param x
     *      The number of recursive terms added to create the next term.
     * @require
     *      n >= 1
     *      x >= 1
     */
    public static int calculate(int n, int x) {
        if (n == 1) {
            return 1;
        } else {
            int output = 0;
            for (int i = 1; i <= x; ++i) {
                output += calculate(n - i, x);
            }
            return output;
        }
    }
}
