package debugging;

/**
 * A fibonacci calculator. Tries to cache values for improved performance.
 */
public class CachedFibonacci {

    /* A brief explanation of what on earth is going on here...
     *
     * When calculating a fibonacci number recursively, you end up doing the same calculation again
     * and again and again. For example,
     * Fib(4)   = Fib(2) + Fib(3)
     *          = Fib(2) + Fib(2) + Fib(1)
     * To calculate the 4th fibonacci number, you calculate the second fibonacci number twice.
     * (This gets exponentially worse as you go above 4) Now obviously the second fibonacci number
     * is going to be exactly the same each time you calculate it, so why bother doing all that
     * work over and over again?
     *
     * Enter caching...
     *
     * This class uses a (hand written) LRU cache to store the last few calculations of fibonacci
     * numbers it did. Every time you ask it for a fibonacci number, it will first check if that
     * number is within the cache. If it's within the cache, we can return it immediately without
     * doing the work to calculate it. It it's not in the cache, we actually need to figure it out,
     * but then we can put it in the cache so that we don't need to calculate it again next time.
     *
     * But what is LRU?
     *
     * When you're caching the return value of a function, there comes a point where you need to
     * stop. The int type can go from -2^31 to 2^31 - 1, and we don't have enough ram to store
     * every one of those fibonacci numbers. Because we keep adding things to the cache every time
     * we do a calculation, we also need to start removing things from the cache so it doesn't
     * explode. LRU - short for Least Recently Used - is a caching heuristic that says when we
     * need to remove a number from the cache, we go for the number that was looked up last. The
     * idea is that if it hasn't been looked up in a while, it probably won't be looked up again
     * soon.
     *
     * How does this implementation of an LRU cache work?
     *
     * Our cache is stored as two arrays (of the same length). The cacheKeys array contains the
     * last few inputs given to our calculate function. The cacheValues array is defined so that
     *
     * for all i such that 0 <= i < cacheKeys.size
     *      cacheValues[i] = calculate(cacheKeys[i])
     *
     * Because the fibonacci sequence is only defined for positive numbers, we will use -1 to
     * signify any extra space in the cache that hasn't been used yet. This is in place of null,
     * because ints are primitive types and therefore cannot be null.
     *
     * In the cacheKeys array, we say that the most recently used keys are at the start of the
     * array, and the least recently used keys are at the end.
     */

    private int[] cacheKeys;
    private int[] cacheValues;

    /**
     * Creates a new cached fibonacci calculator.
     *
     * @param size
     *      The size of the cache to use.
     */
    public CachedFibonacci(int size) {
        cacheKeys = new int[size];
        cacheValues = new int[size];

        for (int i = 0; i < size; ++i) {
            cacheKeys[i] = -1;
            cacheValues[i] = -1;
        }
    }

    /**
     * Calculates the nth fibonacci number.
     *
     * @param n
     *      Which fibonacci number we want.
     * @return
     *      The nth fibonacci number.
     * @require
     *      n >= 1
     */
    public int calculate(int n) {
        // firstly, do the base cases
        if (n == 1 || n == 2) {
            return 1;
        }

        // when we are done, n and Fib(n) need to be in the cache - whether they were before or not
        // we defined index so that they will be at cacheKeys[index] and cacheValues[index],
        // respectively
        int index = -1;

        // secondly, look within the cache for n
        for (int i = 0; i < cacheKeys.length; ++i) {
            if (cacheKeys[i] == n) {
                // we found it - save the position we found it at
                index = i;
                break;
            }
        }

        if (index != -1) {
            // we found it in the cache, but because it has been "used" we need to shuffle it to
            // the start of the array so that it doesn't get deleted when we run out of room
            int key = cacheKeys[index], value = cacheValues[index];
            for (int i = index - 1; i >= 0; --i) {
                cacheKeys[i + 1] = cacheKeys[i];
                cacheValues[i + 1] = cacheValues[i];
            }
            cacheKeys[0] = key;
            cacheValues[0] = value;
        } else {
            // we didn't find it in the cache - we're going to need to calculate it
            int output = calculate(n - 1) + calculate(n - 2);

            // because it's just been "used" it'll need to go at the front of the cache, so make
            // room - this will delete the value at the end if the cache is too full
            for (int i = cacheKeys.length - 2; i >= 0; --i) {
                cacheKeys[i + 1] = cacheKeys[i];
                cacheValues[i + 1] = cacheValues[i];
            }

            // now that we've made space for it in the cache, put it in the cache and act like we
            // just found it there
            cacheKeys[0] = n;
            cacheValues[0] = output;
            index = 0;
        }

        // we either found or "found" n in the cache at position index, so return the value at that
        // same position
        return cacheValues[index];
    }
}
