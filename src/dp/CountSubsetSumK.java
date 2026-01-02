//Question is at the end
//Notes : https://github.com/Anusha806/DPNOTES/blob/main/10.CountSubsetWithSumK.pdf
package dp;

import java.util.Arrays;

public class CountSubsetSumK {

    /* -------------------------------------------------
       1️⃣ RECURSION
       ------------------------------------------------- */
    public static int countSubsetsRecursion(int index, int target, int[] arr) {
        if (target == 0) return 1;
        if (index == 0) {
            return arr[0] == target ? 1 : 0;
        }

        int notPick = countSubsetsRecursion(index - 1, target, arr);
        int pick = 0;

        if (arr[index] <= target) {
            pick = countSubsetsRecursion(index - 1, target - arr[index], arr);
        }

        return pick + notPick;
    }

    /* -------------------------------------------------
       2️⃣ MEMOIZATION (Top-Down DP)
       ------------------------------------------------- */
    public static int countSubsetsMemo(int index, int target, int[] arr) {
        int[][] dp = new int[index + 1][target + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        return memoHelper(index, target, arr, dp);
    }

    private static int memoHelper(int index, int target,
                                  int[] arr, int[][] dp) {
        if (target == 0) return 1;
        if (index == 0) {
            return arr[0] == target ? 1 : 0;
        }

        if (dp[index][target] != -1) return dp[index][target];

        int notPick = memoHelper(index - 1, target, arr, dp);
        int pick = 0;

        if (arr[index] <= target) {
            pick = memoHelper(index - 1, target - arr[index], arr, dp);
        }

        dp[index][target] = pick + notPick;
        return dp[index][target];
    }

    /* -------------------------------------------------
       3️⃣ TABULATION (Bottom-Up DP)
       ------------------------------------------------- */
    public static int countSubsetsTabulation(int[] arr, int K) {
        int n = arr.length;
        int[][] dp = new int[n][K + 1];

        // Base case: sum = 0
        for (int i = 0; i < n; i++) {
            dp[i][0] = 1;
        }

        // First element
        if (arr[0] <= K) {
            dp[0][arr[0]] = 1;
        }

        for (int i = 1; i < n; i++) {
            for (int target = 0; target <= K; target++) {
                int notPick = dp[i - 1][target];
                int pick = 0;

                if (arr[i] <= target) {
                    pick = dp[i - 1][target - arr[i]];
                }

                dp[i][target] = pick + notPick;
            }
        }

        return dp[n - 1][K];
    }

    /* -------------------------------------------------
       4️⃣ SPACE OPTIMIZATION
       ------------------------------------------------- */
    public static int countSubsetsSpaceOptimized(int[] arr, int K) {
        int n = arr.length;
        int[] prev = new int[K + 1];
        prev[0] = 1;

        if (arr[0] <= K) {
            prev[arr[0]] = 1;
        }

        for (int i = 1; i < n; i++) {
            int[] curr = new int[K + 1];
            curr[0] = 1;

            for (int target = 0; target <= K; target++) {
                int notPick = prev[target];
                int pick = 0;

                if (arr[i] <= target) {
                    pick = prev[target - arr[i]];
                }

                curr[target] = pick + notPick;
            }

            prev = curr;
        }

        return prev[K];
    }

    /* -------------------------------------------------
       MAIN METHOD (Testing)
       ------------------------------------------------- */
    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        int K = 3;
        int n = arr.length;

        System.out.println("Recursion: " +
                countSubsetsRecursion(n - 1, K, arr));

        System.out.println("Memoization: " +
                countSubsetsMemo(n - 1, K, arr));

        System.out.println("Tabulation: " +
                countSubsetsTabulation(arr, K));

        System.out.println("Space Optimized: " +
                countSubsetsSpaceOptimized(arr, K));
    }
}



//QUESTION :


/*
 COUNT SUBSETS WITH SUM = K

 Problem:
 You are given an array arr[] of N non-negative integers
 and an integer K.

 Task:
 Count the number of subsets whose sum is exactly equal to K.

 Note:
 - Each element can be picked at most once
 - Order does NOT matter
 - Empty subset is allowed only when K = 0

 Example:
 arr = {1, 2, 3}
 K = 3

 Valid subsets:
 {1, 2}
 {3}

 Output = 2

 Core Idea (Pick / Not Pick):
 At each index:
 1) Pick the element → reduce target
 2) Not pick the element → move ahead

 Recurrence:
 f(i, target) =
     f(i - 1, target - arr[i])   // pick
     +
     f(i - 1, target)            // not pick
*/
