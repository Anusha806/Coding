//Question is at the end
//Notes : https://github.com/Anusha806/DPNOTES/blob/main/3.FrogJump.pdf

package dp;
import java.util.Arrays;
public class FrogJump {

    /* -------------------------------------------------
       1️⃣ RECURSION (Brute Force)
       ------------------------------------------------- */
    public static int frogJumpRecursion(int index, int[] heights) {
        if (index == 0) return 0;

        int oneStep = frogJumpRecursion(index - 1, heights)
                + Math.abs(heights[index] - heights[index - 1]);

        int twoStep = Integer.MAX_VALUE;
        if (index > 1) {
            twoStep = frogJumpRecursion(index - 2, heights)
                    + Math.abs(heights[index] - heights[index - 2]);
        }

        return Math.min(oneStep, twoStep);
    }

    /* -------------------------------------------------
       2️⃣ MEMOIZATION (Top-Down DP)
       ------------------------------------------------- */
    public static int frogJumpMemo(int index, int[] heights) {
        int[] dp = new int[index + 1];
        Arrays.fill(dp, -1);
        return memoHelper(index, heights, dp);
    }

    private static int memoHelper(int index, int[] heights, int[] dp) {
        if (index == 0) return 0;

        if (dp[index] != -1) return dp[index];

        int oneStep = memoHelper(index - 1, heights, dp)
                + Math.abs(heights[index] - heights[index - 1]);

        int twoStep = Integer.MAX_VALUE;
        if (index > 1) {
            twoStep = memoHelper(index - 2, heights, dp)
                    + Math.abs(heights[index] - heights[index - 2]);
        }

        dp[index] = Math.min(oneStep, twoStep);
        return dp[index];
    }

    /* -------------------------------------------------
       3️⃣ TABULATION (Bottom-Up DP)
       ------------------------------------------------- */
    public static int frogJumpTabulation(int[] heights) {
        int n = heights.length;
        int[] dp = new int[n];

        dp[0] = 0;

        for (int i = 1; i < n; i++) {
            int oneStep = dp[i - 1]
                    + Math.abs(heights[i] - heights[i - 1]);

            int twoStep = Integer.MAX_VALUE;
            if (i > 1) {
                twoStep = dp[i - 2]
                        + Math.abs(heights[i] - heights[i - 2]);
            }

            dp[i] = Math.min(oneStep, twoStep);
        }

        return dp[n - 1];
    }

    /* -------------------------------------------------
       4️⃣ SPACE OPTIMIZATION
       ------------------------------------------------- */
    public static int frogJumpSpaceOptimized(int[] heights) {
        int n = heights.length;

        int prev2 = 0; // dp[i-2]
        int prev1 = 0; // dp[i-1]

        for (int i = 1; i < n; i++) {
            int oneStep = prev1
                    + Math.abs(heights[i] - heights[i - 1]);

            int twoStep = Integer.MAX_VALUE;
            if (i > 1) {
                twoStep = prev2
                        + Math.abs(heights[i] - heights[i - 2]);
            }

            int curr = Math.min(oneStep, twoStep);
            prev2 = prev1;
            prev1 = curr;
        }

        return prev1;
    }

    /* -------------------------------------------------
       MAIN METHOD (Testing)
       ------------------------------------------------- */
    public static void main(String[] args) {
        int[] heights = {10, 20, 30, 10};
        int n = heights.length;

        System.out.println("Recursion: " +
                frogJumpRecursion(n - 1, heights));

        System.out.println("Memoization: " +
                frogJumpMemo(n - 1, heights));

        System.out.println("Tabulation: " +
                frogJumpTabulation(heights));

        System.out.println("Space Optimized: " +
                frogJumpSpaceOptimized(heights));
    }
}


//QUESTION :

/*
 FROG JUMP PROBLEM (Dynamic Programming)

 Problem:
 A frog is standing at index 0 of a staircase with n stones.
 Each stone has a given height: heights[i].

 The frog wants to reach the last stone (index n-1).

 Rules:
 - From stone i, the frog can jump to:
     1) stone i + 1
     2) stone i + 2
 - The energy cost of a jump from i to j is:
     |heights[i] - heights[j]|

 Task:
 Find the minimum total energy required to reach the last stone.

 Example:
 heights = {10, 20, 30, 10}

 Explanation:
 0 -> 1 (cost = |10 - 20| = 10)
 1 -> 3 (cost = |20 - 10| = 10)
 Total minimum energy = 20

 DP Relation:
 dp[i] = min(
     dp[i - 1] + |heights[i] - heights[i - 1]|,
     dp[i - 2] + |heights[i] - heights[i - 2]|
 )
*/