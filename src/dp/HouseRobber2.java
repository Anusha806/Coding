//Question is at the end 
//Notes : https://github.com/Anusha806/DPNOTES/blob/main/6.HouseRobber2.pdf
package dp;
import java.util.Arrays;

public class HouseRobber2 {

    /* -------------------------------------------------
       Helper: House Robber I logic (linear)
       ------------------------------------------------- */
    private static int robLinear(int[] nums, int start, int end) {
        int prev2 = 0;
        int prev1 = 0;

        for (int i = start; i <= end; i++) {
            int pick = nums[i] + prev2;
            int notPick = prev1;

            int curr = Math.max(pick, notPick);
            prev2 = prev1;
            prev1 = curr;
        }

        return prev1;
    }

    /* -------------------------------------------------
       1️⃣ MAIN SOLUTION (Optimized)
       ------------------------------------------------- */
    public static int rob(int[] nums) {
        int n = nums.length;

        if (n == 1) return nums[0];

        int case1 = robLinear(nums, 0, n - 2); // leave last
        int case2 = robLinear(nums, 1, n - 1); // leave first

        return Math.max(case1, case2);
    }

    /* -------------------------------------------------
       2️⃣ RECURSION (Educational)
       ------------------------------------------------- */
    public static int robRecursion(int[] nums) {
        int n = nums.length;
        if (n == 1) return nums[0];

        int case1 = robRecHelper(nums, 0, n - 2);
        int case2 = robRecHelper(nums, 1, n - 1);

        return Math.max(case1, case2);
    }

    private static int robRecHelper(int[] nums, int index, int end) {
        if (index > end) return 0;

        int pick = nums[index] + robRecHelper(nums, index + 2, end);
        int notPick = robRecHelper(nums, index + 1, end);

        return Math.max(pick, notPick);
    }

    /* -------------------------------------------------
       3️⃣ MEMOIZATION (Top-Down DP)
       ------------------------------------------------- */
    public static int robMemo(int[] nums) {
        int n = nums.length;
        if (n == 1) return nums[0];

        int[] dp1 = new int[n];
        int[] dp2 = new int[n];
        Arrays.fill(dp1, -1);
        Arrays.fill(dp2, -1);

        int case1 = memoHelper(nums, 0, n - 2, dp1);
        int case2 = memoHelper(nums, 1, n - 1, dp2);

        return Math.max(case1, case2);
    }

    private static int memoHelper(int[] nums, int index, int end, int[] dp) {
        if (index > end) return 0;

        if (dp[index] != -1) return dp[index];

        int pick = nums[index] + memoHelper(nums, index + 2, end, dp);
        int notPick = memoHelper(nums, index + 1, end, dp);

        dp[index] = Math.max(pick, notPick);
        return dp[index];
    }

    /* -------------------------------------------------
       4️⃣ TABULATION (Bottom-Up DP)
       ------------------------------------------------- */
    public static int robTabulation(int[] nums) {
        int n = nums.length;
        if (n == 1) return nums[0];

        return Math.max(
                robTabHelper(nums, 0, n - 2),
                robTabHelper(nums, 1, n - 1)
        );
    }

    private static int robTabHelper(int[] nums, int start, int end) {
        int len = end - start + 1;
        int[] dp = new int[len];

        dp[0] = nums[start];

        if (len > 1) {
            dp[1] = Math.max(nums[start], nums[start + 1]);
        }

        for (int i = 2; i < len; i++) {
            dp[i] = Math.max(
                    nums[start + i] + dp[i - 2],
                    dp[i - 1]
            );
        }

        return dp[len - 1];
    }

    /* -------------------------------------------------
       MAIN METHOD (Testing)
       ------------------------------------------------- */
    public static void main(String[] args) {
        int[] nums = {2, 3, 2};

        System.out.println("Optimized: " + rob(nums));
        System.out.println("Recursion: " + robRecursion(nums));
        System.out.println("Memoization: " + robMemo(nums));
        System.out.println("Tabulation: " + robTabulation(nums));
    }
}



//QUESTION :

/*
 HOUSE ROBBER II (Circular Houses)

 Problem:
 You are given an array nums[] where each element represents
 the money in a house.

 Difference from House Robber I:
 - Houses are arranged in a CIRCLE
 - First house and last house are adjacent

 Constraint:
 - You cannot rob two adjacent houses

 Task:
 Return the maximum amount of money you can rob.

 Key Idea:
 Since first and last houses are adjacent, you cannot rob both.
 So split the problem into two linear cases:

 Case 1: Leave the last house
         → apply House Robber I on nums[0 .. n-2]

 Case 2: Leave the first house
         → apply House Robber I on nums[1 .. n-1]

 Final Answer:
 max(case1, case2)
*/
