//Question is at the end 
//Notes : https://github.com/Anusha806/DPNOTES/blob/main/4.FrogJumpWithKSteps.pdf
package dp;
import java.util.Arrays;
public class FrogJumpKSteps {

    /* -------------------------------------------------
       1️⃣ RECURSION (Brute Force)
       ------------------------------------------------- */
    public static int frogJumpRecursion(int index, int[] heights, int k) {
        if (index == 0) return 0;

        int minEnergy = Integer.MAX_VALUE;

        for (int j = 1; j <= k; j++) {
            if (index - j >= 0) {
                int energy = frogJumpRecursion(index - j, heights, k)
                        + Math.abs(heights[index] - heights[index - j]);
                minEnergy = Math.min(minEnergy, energy);
            }
        }

        return minEnergy;
    }

    /* -------------------------------------------------
       2️⃣ MEMOIZATION (Top-Down DP)
       ------------------------------------------------- */
    public static int frogJumpMemo(int index, int[] heights, int k) {
        int[] dp = new int[index + 1];
        Arrays.fill(dp, -1);
        return memoHelper(index, heights, k, dp);
    }

    private static int memoHelper(int index, int[] heights, int k, int[] dp) {
        if (index == 0) return 0;

        if (dp[index] != -1) return dp[index];

        int minEnergy = Integer.MAX_VALUE;

        for (int j = 1; j <= k; j++) {
            if (index - j >= 0) {
                int energy = memoHelper(index - j, heights, k, dp)
                        + Math.abs(heights[index] - heights[index - j]);
                minEnergy = Math.min(minEnergy, energy);
            }
        }

        dp[index] = minEnergy;
        return dp[index];
    }

    /* -------------------------------------------------
       3️⃣ TABULATION (Bottom-Up DP)
       ------------------------------------------------- */
    public static int frogJumpTabulation(int[] heights, int k) {
        int n = heights.length;
        int[] dp = new int[n];
        dp[0] = 0;

        for (int i = 1; i < n; i++) {
            int minEnergy = Integer.MAX_VALUE;

            for (int j = 1; j <= k; j++) {
                if (i - j >= 0) {
                    int energy = dp[i - j]
                            + Math.abs(heights[i] - heights[i - j]);
                    minEnergy = Math.min(minEnergy, energy);
                }
            }

            dp[i] = minEnergy;
        }

        return dp[n - 1];
    }

    /* -------------------------------------------------
       4️⃣ SPACE OPTIMIZATION
       -------------------------------------------------
       Not effectively possible beyond O(n) because
       dp[i] depends on previous k states.
       ------------------------------------------------- */

    /* -------------------------------------------------
       MAIN METHOD (Testing)
       ------------------------------------------------- */
    public static void main(String[] args) {
        int[] heights = {10, 30, 40, 50, 20};
        int k = 3;
        int n = heights.length;

        System.out.println("Recursion: " +
                frogJumpRecursion(n - 1, heights, k));

        System.out.println("Memoization: " +
                frogJumpMemo(n - 1, heights, k));

        System.out.println("Tabulation: " +
                frogJumpTabulation(heights, k));
    }
}




//QUESTION :

/*
 FROG JUMP WITH K STEPS (Dynamic Programming)

 Problem:
 A frog is standing at index 0 on a staircase with n stones.
 Each stone has a given height: heights[i].

 The frog wants to reach the last stone (index n - 1).

 Rules:
 - From stone i, the frog can jump to any stone from:
       i + 1, i + 2, ..., i + k
 - The energy cost of jumping from stone i to j is:
       |heights[i] - heights[j]|

 Task:
 Find the minimum total energy required to reach the last stone.

 Example:
 heights = {10, 30, 40, 50, 20}
 k = 3

 Explanation:
 The frog can jump up to 3 steps at a time and must choose
 the path with minimum total energy.

 DP Relation:
 dp[i] = min over all j from 1 to k:
         dp[i - j] + |heights[i] - heights[i - j]|
*/

