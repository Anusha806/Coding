//Question is at the end

package dp;
import java.util.*;
public class ClimbingStairs {
    /* -------------------------------------------------
       1️⃣ RECURSION (Brute Force)
       ------------------------------------------------- */
    public static int climbStairsRecursion(int n) {
        if (n == 0 || n == 1) return 1;
        return climbStairsRecursion(n - 1) + climbStairsRecursion(n - 2);
    }
    /* -------------------------------------------------
       2️⃣ MEMOIZATION (Top-Down DP)
       ------------------------------------------------- */
    public static int climbStairsMemo(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);
        return memoHelper(n, dp);
    }
    private static int memoHelper(int n, int[] dp) {
        if (n == 0 || n == 1) return 1;

        if (dp[n] != -1) return dp[n];

        dp[n] = memoHelper(n - 1, dp) + memoHelper(n - 2, dp);
        return dp[n];
    }
    /* -------------------------------------------------
       3️⃣ TABULATION (Bottom-Up DP)
       ------------------------------------------------- */
    public static int climbStairsTabulation(int n) {
        if (n == 0 || n == 1) return 1;

        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }
    /* -------------------------------------------------
       4️⃣ SPACE OPTIMIZATION
       ------------------------------------------------- */
    public static int climbStairsSpaceOptimized(int n) {
        if (n == 0 || n == 1) return 1;

        int prev2 = 1;
        int prev1 = 1;

        for (int i = 2; i <= n; i++) {
            int curr = prev1 + prev2;
            prev2 = prev1;
            prev1 = curr;
        }

        return prev1;
    }

    /* -------------------------------------------------
       MAIN METHOD (Testing)
       ------------------------------------------------- */
    public static void main(String[] args) {
        int n = 5;
        System.out.println("Recursion: " + climbStairsRecursion(n));
        System.out.println("Memoization: " + climbStairsMemo(n));
        System.out.println("Tabulation: " + climbStairsTabulation(n));
        System.out.println("Space Optimized: " + climbStairsSpaceOptimized(n));
    }
}


//QUESTION :

	/*
	 LEETCODE 70: CLIMBING STAIRS

	 Problem:
	 You are climbing a staircase with n steps.
	 Each time, you can climb either 1 step or 2 steps.

	 Task:
	 Return the total number of distinct ways to reach the top.

	 Example:
	 n = 3
	 Ways:
	 1 + 1 + 1
	 1 + 2
	 2 + 1
	 Output = 3

	 Key Observation:
	 To reach step n:
	 - You can come from step (n - 1)
	 - You can come from step (n - 2)

	 So,
	 ways(n) = ways(n - 1) + ways(n - 2)

	 This is a classic Dynamic Programming problem
	 and is equivalent to the Fibonacci sequence.
	*/



