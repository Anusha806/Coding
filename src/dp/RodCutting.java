//Question is at the end
//Notes : https://github.com/Anusha806/DPNOTES/blob/main/15.RodCuttingProblem.pdf

package dp;
import java.util.Arrays;

public class RodCutting {

    /* -------------------------------------------------
       1️⃣ RECURSION
       ------------------------------------------------- */
    public static int rodCutRecursion(int ind, int len, int[] price) {
        if (ind == 0) {
            return len * price[0];
        }

        int notTake = rodCutRecursion(ind - 1, len, price);

        int take = Integer.MIN_VALUE;
        int rodLength = ind + 1;

        if (rodLength <= len) {
            take = price[ind]
                    + rodCutRecursion(ind, len - rodLength, price);
        }

        return Math.max(take, notTake);
    }

    /* -------------------------------------------------
       2️⃣ MEMOIZATION (Top-Down DP)
       ------------------------------------------------- */
    public static int rodCutMemo(int ind, int len, int[] price) {
        int[][] dp = new int[ind + 1][len + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        return memoHelper(ind, len, price, dp);
    }

    private static int memoHelper(int ind, int len,
                                  int[] price, int[][] dp) {
        if (ind == 0) {
            return len * price[0];
        }

        if (dp[ind][len] != -1) return dp[ind][len];

        int notTake = memoHelper(ind - 1, len, price, dp);

        int take = Integer.MIN_VALUE;
        int rodLength = ind + 1;

        if (rodLength <= len) {
            take = price[ind]
                    + memoHelper(ind, len - rodLength, price, dp);
        }

        dp[ind][len] = Math.max(take, notTake);
        return dp[ind][len];
    }

    /* -------------------------------------------------
       3️⃣ TABULATION (Bottom-Up DP)
       ------------------------------------------------- */
    public static int rodCutTabulation(int[] price, int N) {
        int[][] dp = new int[N][N + 1];

        // Base case: only length 1 pieces
        for (int len = 0; len <= N; len++) {
            dp[0][len] = len * price[0];
        }

        for (int i = 1; i < N; i++) {
            for (int len = 0; len <= N; len++) {
                int notTake = dp[i - 1][len];
                int take = Integer.MIN_VALUE;
                int rodLength = i + 1;

                if (rodLength <= len) {
                    take = price[i] + dp[i][len - rodLength];
                }

                dp[i][len] = Math.max(take, notTake);
            }
        }

        return dp[N - 1][N];
    }

    /* -------------------------------------------------
       4️⃣ SPACE OPTIMIZATION
       ------------------------------------------------- */
    public static int rodCutSpaceOptimized(int[] price, int N) {
        int[] dp = new int[N + 1];

        // Base case
        for (int len = 0; len <= N; len++) {
            dp[len] = len * price[0];
        }

        for (int i = 1; i < N; i++) {
            for (int len = 0; len <= N; len++) {
                int rodLength = i + 1;
                if (rodLength <= len) {
                    dp[len] = Math.max(
                            dp[len],
                            price[i] + dp[len - rodLength]
                    );
                }
            }
        }

        return dp[N];
    }

    /* -------------------------------------------------
       MAIN METHOD (Testing)
       ------------------------------------------------- */
    public static void main(String[] args) {
        int[] price = {2, 5, 7, 8, 10};
        int N = 5;

        System.out.println("Recursion: " +
                rodCutRecursion(N - 1, N, price));

        System.out.println("Memoization: " +
                rodCutMemo(N - 1, N, price));

        System.out.println("Tabulation: " +
                rodCutTabulation(price, N));

        System.out.println("Space Optimized: " +
                rodCutSpaceOptimized(price, N));
    }
}




//QUESTION :

/*
 ROD CUTTING PROBLEM

 Problem:
 You are given a rod of length N and an array price[]
 where price[i] represents the price of a rod piece
 of length (i + 1).

 You can cut the rod into any number of pieces.

 Task:
 Maximize the total price obtainable by cutting the rod
 and selling the pieces.

 Note:
 - Each cut length can be used INFINITELY
 - Lengths are 1-based (price[0] → length 1)

 Example:
 N = 5
 price = {2, 5, 7, 8, 10}

 Best cuts:
 2 + 3 → 5 + 7 = 12
 OR
 1 + 2 + 2 → 2 + 5 + 5 = 12

 Output = 12

 Core Idea (Take / Not Take):
 This is the same as UNBOUNDED KNAPSACK

 Recurrence:
 f(i, len) =
     max(
         price[i] + f(i, len - (i + 1)),  // take (stay at i)
         f(i - 1, len)                    // not take
     )
*/