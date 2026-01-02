//Question is at the end
//Notes : https://github.com/Anusha806/DPNOTES/blob/main/11.01Knapsack.pdf

package dp;
import java.util.Arrays;

public class Knapsack01 {

    /* -------------------------------------------------
       1️⃣ RECURSION
       ------------------------------------------------- */
    public static int knapsackRecursion(int index, int W,
                                        int[] weights, int[] values) {
        if (index == 0) {
            if (weights[0] <= W) return values[0];
            return 0;
        }

        int notPick = knapsackRecursion(index - 1, W, weights, values);

        int pick = Integer.MIN_VALUE;
        if (weights[index] <= W) {
            pick = values[index]
                    + knapsackRecursion(index - 1, W - weights[index],
                                         weights, values);
        }

        return Math.max(pick, notPick);
    }

    /* -------------------------------------------------
       2️⃣ MEMOIZATION (Top-Down DP)
       ------------------------------------------------- */
    public static int knapsackMemo(int index, int W,
                                   int[] weights, int[] values) {
        int[][] dp = new int[index + 1][W + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        return memoHelper(index, W, weights, values, dp);
    }

    private static int memoHelper(int index, int W,
                                  int[] weights, int[] values,
                                  int[][] dp) {
        if (index == 0) {
            if (weights[0] <= W) return values[0];
            return 0;
        }

        if (dp[index][W] != -1) return dp[index][W];

        int notPick = memoHelper(index - 1, W, weights, values, dp);

        int pick = Integer.MIN_VALUE;
        if (weights[index] <= W) {
            pick = values[index]
                    + memoHelper(index - 1, W - weights[index],
                                 weights, values, dp);
        }

        dp[index][W] = Math.max(pick, notPick);
        return dp[index][W];
    }

    /* -------------------------------------------------
       3️⃣ TABULATION (Bottom-Up DP)
       ------------------------------------------------- */
    public static int knapsackTabulation(int[] weights, int[] values, int W) {
        int n = weights.length;
        int[][] dp = new int[n][W + 1];

        // Base case: first item
        for (int w = weights[0]; w <= W; w++) {
            dp[0][w] = values[0];
        }

        for (int i = 1; i < n; i++) {
            for (int w = 0; w <= W; w++) {
                int notPick = dp[i - 1][w];
                int pick = Integer.MIN_VALUE;

                if (weights[i] <= w) {
                    pick = values[i] + dp[i - 1][w - weights[i]];
                }

                dp[i][w] = Math.max(pick, notPick);
            }
        }

        return dp[n - 1][W];
    }

    /* -------------------------------------------------
       4️⃣ SPACE OPTIMIZATION
       ------------------------------------------------- */
    public static int knapsackSpaceOptimized(int[] weights,
                                             int[] values, int W) {
        int n = weights.length;
        int[] prev = new int[W + 1];

        // Base case
        for (int w = weights[0]; w <= W; w++) {
            prev[w] = values[0];
        }

        for (int i = 1; i < n; i++) {
            int[] curr = new int[W + 1];
            for (int w = 0; w <= W; w++) {
                int notPick = prev[w];
                int pick = Integer.MIN_VALUE;

                if (weights[i] <= w) {
                    pick = values[i] + prev[w - weights[i]];
                }

                curr[w] = Math.max(pick, notPick);
            }
            prev = curr;
        }

        return prev[W];
    }

    /* -------------------------------------------------
       MAIN METHOD (Testing)
       ------------------------------------------------- */
    public static void main(String[] args) {
        int[] weights = {3, 4, 5};
        int[] values = {30, 50, 60};
        int W = 8;
        int n = weights.length;

        System.out.println("Recursion: " +
                knapsackRecursion(n - 1, W, weights, values));

        System.out.println("Memoization: " +
                knapsackMemo(n - 1, W, weights, values));

        System.out.println("Tabulation: " +
                knapsackTabulation(weights, values, W));

        System.out.println("Space Optimized: " +
                knapsackSpaceOptimized(weights, values, W));
    }
}



//QUESTION :

/*
 0/1 KNAPSACK PROBLEM

 Problem:
 You are given:
 - weights[] → weight of each item
 - values[]  → value of each item
 - W         → maximum capacity of the knapsack
 - n         → number of items

 Constraint:
 - Each item can be chosen at most ONCE (0 or 1 time)
 - Total weight must not exceed W

 Task:
 Maximize the total value inside the knapsack.

 Example:
 weights = {3, 4, 5}
 values  = {30, 50, 60}
 W = 8

 Best choice:
 Pick items with weight 3 and 5
 Total value = 30 + 60 = 90

 Core Idea (Pick / Not Pick):
 At each index:
 1) Pick the item → add its value and reduce capacity
 2) Not pick the item → move to previous index

 Recurrence:
 f(i, W) =
     max(
         values[i] + f(i - 1, W - weights[i]),  // pick
         f(i - 1, W)                              // not pick
     )
*/

