//Question is at the end
//Notes : https://github.com/Anusha806/DPNOTES/blob/main/13.%20CoinChange2.pdf

package dp;
import java.util.Arrays;

public class CoinChange2 {

    /* -------------------------------------------------
       1️⃣ RECURSION
       ------------------------------------------------- */
    public static int countWaysRecursion(int ind, int amount, int[] coins) {
        if (amount == 0) return 1;
        if (ind == coins.length) return 0;

        int notTake = countWaysRecursion(ind + 1, amount, coins);

        int take = 0;
        if (coins[ind] <= amount) {
            take = countWaysRecursion(ind, amount - coins[ind], coins);
        }

        return take + notTake;
    }

    /* -------------------------------------------------
       2️⃣ MEMOIZATION (Top-Down DP)
       ------------------------------------------------- */
    public static int countWaysMemo(int amount, int[] coins) {
        int[][] dp = new int[coins.length][amount + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        return memoHelper(0, amount, coins, dp);
    }

    private static int memoHelper(int ind, int amount,
                                  int[] coins, int[][] dp) {
        if (amount == 0) return 1;
        if (ind == coins.length) return 0;

        if (dp[ind][amount] != -1) return dp[ind][amount];

        int notTake = memoHelper(ind + 1, amount, coins, dp);
        int take = 0;

        if (coins[ind] <= amount) {
            take = memoHelper(ind, amount - coins[ind], coins, dp);
        }

        dp[ind][amount] = take + notTake;
        return dp[ind][amount];
    }

    /* -------------------------------------------------
       3️⃣ TABULATION (Bottom-Up DP)
       ------------------------------------------------- */
    public static int countWaysTabulation(int amount, int[] coins) {
        int n = coins.length;
        int[][] dp = new int[n + 1][amount + 1];

        // Base case: amount = 0 → 1 way
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1;
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int amt = 1; amt <= amount; amt++) {
                int notTake = dp[i + 1][amt];
                int take = 0;

                if (coins[i] <= amt) {
                    take = dp[i][amt - coins[i]];
                }

                dp[i][amt] = take + notTake;
            }
        }

        return dp[0][amount];
    }

    /* -------------------------------------------------
       4️⃣ SPACE OPTIMIZATION
       ------------------------------------------------- */
    public static int countWaysSpaceOptimized(int amount, int[] coins) {
        int n = coins.length;
        int[] dp = new int[amount + 1];
        dp[0] = 1;

        for (int coin : coins) {
            for (int amt = coin; amt <= amount; amt++) {
                dp[amt] += dp[amt - coin];
            }
        }

        return dp[amount];
    }

    /* -------------------------------------------------
       MAIN METHOD (Testing)
       ------------------------------------------------- */
    public static void main(String[] args) {
        int[] coins = {1, 2, 5};
        int amount = 5;

        System.out.println("Recursion: " +
                countWaysRecursion(0, amount, coins));

        System.out.println("Memoization: " +
                countWaysMemo(amount, coins));

        System.out.println("Tabulation: " +
                countWaysTabulation(amount, coins));

        System.out.println("Space Optimized: " +
                countWaysSpaceOptimized(amount, coins));
    }
}


//QUESTION :

/*
 COIN CHANGE II (Count Number of Combinations)

 Problem:
 You are given an integer array coins[] representing
 different coin denominations, and an integer amount.

 You have INFINITE supply of each coin.

 Task:
 Return the number of combinations that make up the amount.

 Important:
 - Order does NOT matter
 - Same coin can be used multiple times

 Example:
 coins = {1, 2, 5}
 amount = 5

 Valid combinations:
 5
 2 + 2 + 1
 2 + 1 + 1 + 1
 1 + 1 + 1 + 1 + 1

 Output = 4

 Core Idea (Take / Not Take):
 At each index:
 1) Take the coin:
    - Coin is infinite → stay at same index
    - Reduce amount
 2) Not take the coin:
    - Move to next index

 Recurrence:
 f(ind, amount) =
     f(ind, amount - coins[ind])   // take
     +
     f(ind + 1, amount)            // not take

 Base Case:
 - If amount == 0 → 1 valid combination
 - If ind == coins.length → return 0
*/
