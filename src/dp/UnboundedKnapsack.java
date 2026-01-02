//Question is at the end
//Notes : https://github.com/Anusha806/DPNOTES/blob/main/14.UnboundKnapsack.pdf

package dp;

import java.util.Arrays;

public class UnboundedKnapsack {

    /* -------------------------------------------------
       1️⃣ RECURSION
       ------------------------------------------------- */
    public static int unboundedRecursion(int ind, int W,
                                         int[] weights, int[] values) {
        if (ind == 0) {
            return (W / weights[0]) * values[0];
        }

        int notTake = unboundedRecursion(ind - 1, W, weights, values);

        int take = Integer.MIN_VALUE;
        if (weights[ind] <= W) {
            take = values[ind]
                    + unboundedRecursion(ind, W - weights[ind],
                                          weights, values);
        }

        return Math.max(take, notTake);
    }

    /* -------------------------------------------------
       2️⃣ MEMOIZATION (Top-Down DP)
       ------------------------------------------------- */
    public static int unboundedMemo(int ind, int W,
                                    int[] weights, int[] values) {
        int[][] dp = new int[ind + 1][W + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        return memoHelper(ind, W, weights, values, dp);
    }

    private static int memoHelper(int ind, int W,
                                  int[] weights, int[] values,
                                  int[][] dp) {
        if (ind == 0) {
            return (W / weights[0]) * values[0];
        }

        if (dp[ind][W] != -1) return dp[ind][W];

        int notTake = memoHelper(ind - 1, W, weights, values, dp);

        int take = Integer.MIN_VALUE;
        if (weights[ind] <= W) {
            take = values[ind]
                    + memoHelper(ind, W - weights[ind],
                                 weights, values, dp);
        }

        dp[ind][W] = Math.max(take, notTake);
        return dp[ind][W];
    }

    /* -------------------------------------------------
       3️⃣ TABULATION (Bottom-Up DP)
       ------------------------------------------------- */
    public static int unboundedTabulation(int[] weights,
                                          int[] values, int W) {
        int n = weights.length;
        int[][] dp = new int[n][W + 1];

        // Base case
        for (int w = 0; w <= W; w++) {
            dp[0][w] = (w / weights[0]) * values[0];
        }

        for (int i = 1; i < n; i++) {
            for (int w = 0; w <= W; w++) {
                int notTake = dp[i - 1][w];
                int take = Integer.MIN_VALUE;

                if (weights[i] <= w) {
                    take = values[i] + dp[i][w - weights[i]];
                }

                dp[i][w] = Math.max(take, notTake);
            }
        }

        return dp[n - 1][W];
    }

    /* -------------------------------------------------
       4️⃣ SPACE OPTIMIZATION
       ------------------------------------------------- */
    public static int unboundedSpaceOptimized(int[] weights,
                                              int[] values, int W) {
        int n = weights.length;
        int[] dp = new int[W + 1];

        // Base case
        for (int w = 0; w <= W; w++) {
            dp[w] = (w / weights[0]) * values[0];
        }

        for (int i = 1; i < n; i++) {
            for (int w = 0; w <= W; w++) {
                if (weights[i] <= w) {
                    dp[w] = Math.max(
                            dp[w],
                            values[i] + dp[w - weights[i]]
                    );
                }
            }
        }

        return dp[W];
    }

    /* -------------------------------------------------
       MAIN METHOD (Testing)
       ------------------------------------------------- */
    public static void main(String[] args) {
        int[] weights = {2, 4, 6};
        int[] values = {5, 11, 13};
        int W = 10;
        int n = weights.length;

        System.out.println("Recursion: " +
                unboundedRecursion(n - 1, W, weights, values));

        System.out.println("Memoization: " +
                unboundedMemo(n - 1, W, weights, values));

        System.out.println("Tabulation: " +
                unboundedTabulation(weights, values, W));

        System.out.println("Space Optimized: " +
                unboundedSpaceOptimized(weights, values, W));
    }
}




//QUESTION :

/*
 UNBOUNDED KNAPSACK PROBLEM

 Problem:
 You are given:
 - weights[] → weight of each item
 - values[]  → value of each item
 - W         → maximum capacity of the knapsack

 Constraint:
 - Each item has INFINITE supply (can be chosen multiple times)
 - Total weight must not exceed W

 Task:
 Maximize the total value inside the knapsack.

 Difference from 0/1 Knapsack:
 - After picking an item, we can PICK IT AGAIN
 - Hence, after pick → stay at SAME index

 Example:
 weights = {2, 4, 6}
 values  = {5, 11, 13}
 W = 10

 Best choice:
 4 + 4 + 2 → value = 11 + 11 + 5 = 27

 Core Idea (Take / Not Take):
 f(i, W) =
     max(
         values[i] + f(i, W - weights[i]),  // take (stay at i)
         f(i - 1, W)                         // not take
     )
*/

