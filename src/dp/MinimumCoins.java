//Question is at the end
//Notes : https://github.com/Anusha806/DPNOTES/blob/main/12.%20MinimumCoins.pdf

package dp;
import java.util.Arrays;

public class MinimumCoins {

    private static final int INF = (int) 1e9;

    /* -------------------------------------------------
       1️⃣ RECURSION
       ------------------------------------------------- */
    public static int minCoinsRecursion(int ind, int T, int[] coins) {
        if (ind == 0) {
            if (T % coins[0] == 0) return T / coins[0];
            return INF;
        }

        int notTake = minCoinsRecursion(ind - 1, T, coins);

        int take = INF;
        if (coins[ind] <= T) {
            take = 1 + minCoinsRecursion(ind, T - coins[ind], coins);
        }

        return Math.min(take, notTake);
    }

    /* -------------------------------------------------
       2️⃣ MEMOIZATION (Top-Down DP)
       ------------------------------------------------- */
    public static int minCoinsMemo(int ind, int T, int[] coins) {
        int[][] dp = new int[ind + 1][T + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        int ans = memoHelper(ind, T, coins, dp);
        return ans >= INF ? -1 : ans;
    }

    private static int memoHelper(int ind, int T,
                                  int[] coins, int[][] dp) {
        if (ind == 0) {
            if (T % coins[0] == 0) return T / coins[0];
            return INF;
        }

        if (dp[ind][T] != -1) return dp[ind][T];

        int notTake = memoHelper(ind - 1, T, coins, dp);

        int take = INF;
        if (coins[ind] <= T) {
            take = 1 + memoHelper(ind, T - coins[ind], coins, dp);
        }

        dp[ind][T] = Math.min(take, notTake);
        return dp[ind][T];
    }

    /* -------------------------------------------------
       3️⃣ TABULATION (Bottom-Up DP)
       ------------------------------------------------- */
    public static int minCoinsTabulation(int[] coins, int T) {
        int n = coins.length;
        int[][] dp = new int[n][T + 1];

        // Base case
        for (int t = 0; t <= T; t++) {
            if (t % coins[0] == 0)
                dp[0][t] = t / coins[0];
            else
                dp[0][t] = INF;
        }

        for (int i = 1; i < n; i++) {
            for (int t = 0; t <= T; t++) {
                int notTake = dp[i - 1][t];
                int take = INF;

                if (coins[i] <= t) {
                    take = 1 + dp[i][t - coins[i]];
                }

                dp[i][t] = Math.min(take, notTake);
            }
        }

        int ans = dp[n - 1][T];
        return ans >= INF ? -1 : ans;
    }

    /* -------------------------------------------------
       4️⃣ SPACE OPTIMIZATION
       ------------------------------------------------- */
    public static int minCoinsSpaceOptimized(int[] coins, int T) {
        int n = coins.length;
        int[] prev = new int[T + 1];

        // Base case
        for (int t = 0; t <= T; t++) {
            if (t % coins[0] == 0)
                prev[t] = t / coins[0];
            else
                prev[t] = INF;
        }

        for (int i = 1; i < n; i++) {
            int[] curr = new int[T + 1];
            for (int t = 0; t <= T; t++) {
                int notTake = prev[t];
                int take = INF;

                if (coins[i] <= t) {
                    take = 1 + curr[t - coins[i]];
                }

                curr[t] = Math.min(take, notTake);
            }
            prev = curr;
        }

        int ans = prev[T];
        return ans >= INF ? -1 : ans;
    }

    /* -------------------------------------------------
       MAIN METHOD (Testing)
       ------------------------------------------------- */
    public static void main(String[] args) {
        int[] coins = {1, 2, 5};
        int T = 11;
        int n = coins.length;

        int recAns = minCoinsRecursion(n - 1, T, coins);
        System.out.println("Recursion: " +
                (recAns >= INF ? -1 : recAns));

        System.out.println("Memoization: " +
                minCoinsMemo(n - 1, T, coins));

        System.out.println("Tabulation: " +
                minCoinsTabulation(coins, T));

        System.out.println("Space Optimized: " +
                minCoinsSpaceOptimized(coins, T));
    }
}




//QUESTION :

/*
 MINIMUM COINS PROBLEM (Coin Change - Minimum Coins)

 Problem:
 You are given an array coins[] where each element represents
 a coin denomination, and an integer T (target).

 You have INFINITE supply of each coin.

 Task:
 Find the minimum number of coins required to make sum T.
 If it is not possible, return -1.

 Example:
 coins = {1, 2, 5}
 T = 11

 Output:
 3  → (5 + 5 + 1)

 Core Idea (Take / Not Take):
 At each index:
 1) Take the coin:
    - Coin is infinite → stay at same index
    - Reduce target by coin value
 2) Not take the coin:
    - Move to previous index

 Recurrence:
 f(ind, T) =
     min(
         1 + f(ind, T - coins[ind]),   // take
         f(ind - 1, T)                  // not take
     )

 Base Case:
 If ind == 0:
   - If T % coins[0] == 0 → return T / coins[0]
   - Else → return large value (impossible)
*/
