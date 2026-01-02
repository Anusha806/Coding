//Question is at the end
//Notes : https://github.com/Anusha806/DPNOTES/blob/main/7.SubsetSumEqualsK.pdf
package dp;
import java.util.Arrays;

public class SubsetSumEqualsK {

    /* -------------------------------------------------
       1️⃣ RECURSION
       ------------------------------------------------- */
    public static boolean subsetSumRecursion(int index, int target, int[] arr) {
        if (target == 0) return true;
        if (index == 0) return arr[0] == target;

        boolean notPick = subsetSumRecursion(index - 1, target, arr);
        boolean pick = false;

        if (arr[index] <= target) {
            pick = subsetSumRecursion(index - 1, target - arr[index], arr);
        }

        return pick || notPick;
    }

    /* -------------------------------------------------
       2️⃣ MEMOIZATION (Top-Down DP)
       ------------------------------------------------- */
    public static boolean subsetSumMemo(int index, int target, int[] arr) {
        Boolean[][] dp = new Boolean[index + 1][target + 1];
        return memoHelper(index, target, arr, dp);
    }

    private static boolean memoHelper(int index, int target,
                                      int[] arr, Boolean[][] dp) {
        if (target == 0) return true;
        if (index == 0) return arr[0] == target;

        if (dp[index][target] != null) return dp[index][target];

        boolean notPick = memoHelper(index - 1, target, arr, dp);
        boolean pick = false;

        if (arr[index] <= target) {
            pick = memoHelper(index - 1, target - arr[index], arr, dp);
        }

        dp[index][target] = pick || notPick;
        return dp[index][target];
    }

    /* -------------------------------------------------
       3️⃣ TABULATION (Bottom-Up DP)
       ------------------------------------------------- */
    public static boolean subsetSumTabulation(int[] arr, int K) {
        int n = arr.length;
        boolean[][] dp = new boolean[n][K + 1];

        // Base case: target 0 is always achievable
        for (int i = 0; i < n; i++) {
            dp[i][0] = true;
        }

        // Base case: first element
        if (arr[0] <= K) {
            dp[0][arr[0]] = true;
        }

        for (int i = 1; i < n; i++) {
            for (int target = 1; target <= K; target++) {
                boolean notPick = dp[i - 1][target];
                boolean pick = false;

                if (arr[i] <= target) {
                    pick = dp[i - 1][target - arr[i]];
                }

                dp[i][target] = pick || notPick;
            }
        }

        return dp[n - 1][K];
    }

    /* -------------------------------------------------
       4️⃣ SPACE OPTIMIZATION
       ------------------------------------------------- */
    public static boolean subsetSumSpaceOptimized(int[] arr, int K) {
        int n = arr.length;
        boolean[] prev = new boolean[K + 1];
        prev[0] = true;

        if (arr[0] <= K) {
            prev[arr[0]] = true;
        }

        for (int i = 1; i < n; i++) {
            boolean[] curr = new boolean[K + 1];
            curr[0] = true;

            for (int target = 1; target <= K; target++) {
                boolean notPick = prev[target];
                boolean pick = false;

                if (arr[i] <= target) {
                    pick = prev[target - arr[i]];
                }

                curr[target] = pick || notPick;
            }

            prev = curr;
        }

        return prev[K];
    }

    /* -------------------------------------------------
       MAIN METHOD (Testing)
       ------------------------------------------------- */
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4};
        int K = 4;
        int n = arr.length;

        System.out.println("Recursion: " +
                subsetSumRecursion(n - 1, K, arr));

        System.out.println("Memoization: " +
                subsetSumMemo(n - 1, K, arr));

        System.out.println("Tabulation: " +
                subsetSumTabulation(arr, K));

        System.out.println("Space Optimized: " +
                subsetSumSpaceOptimized(arr, K));
    }
}




//QUESTION :

/*
 SUBSET SUM EQUALS K

 Problem:
 You are given an array arr[] of N positive integers
 and an integer K.

 Task:
 Check whether there exists a subset (subsequence)
 whose sum is exactly equal to K.

 Note:
 - You can either pick or not pick an element
 - Order does NOT matter
 - Each element can be used at most once

 Example:
 arr = {1, 2, 3, 4}
 K = 4

 Possible subsets:
 {4}
 {1, 3}

 Output:
 true

 Core Idea (Pick / Not Pick):
 At each index, we have two choices:
 1) Pick the current element → reduce target
 2) Not pick the current element → move ahead

 Recurrence:
 f(i, target) =
     f(i - 1, target - arr[i])  // pick
     OR
     f(i - 1, target)           // not pick
*/