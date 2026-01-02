//Question is at the end
//Notes : https://github.com/Anusha806/DPNOTES/blob/main/5.HouseRobber1.pdf

package dp;
import java.util.Arrays;

public class HouseRobber1 {

    /* -------------------------------------------------
       1️⃣ RECURSION (Brute Force)
       ------------------------------------------------- */
    public static int robRecursion(int index, int[] nums) {
        if (index < 0) return 0;
        if (index == 0) return nums[0];

        int pick = nums[index] + robRecursion(index - 2, nums);
        int notPick = robRecursion(index - 1, nums);

        return Math.max(pick, notPick);
    }

    /* -------------------------------------------------
       2️⃣ MEMOIZATION (Top-Down DP)
       ------------------------------------------------- */
    public static int robMemo(int index, int[] nums) {
        int[] dp = new int[index + 1];
        Arrays.fill(dp, -1);
        return memoHelper(index, nums, dp);
    }

    private static int memoHelper(int index, int[] nums, int[] dp) {
        if (index < 0) return 0;
        if (index == 0) return nums[0];

        if (dp[index] != -1) return dp[index];

        int pick = nums[index] + memoHelper(index - 2, nums, dp);
        int notPick = memoHelper(index - 1, nums, dp);

        dp[index] = Math.max(pick, notPick);
        return dp[index];
    }

    /* -------------------------------------------------
       3️⃣ TABULATION (Bottom-Up DP)
       ------------------------------------------------- */
    public static int robTabulation(int[] nums) {
        int n = nums.length;
        if (n == 1) return nums[0];

        int[] dp = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        for (int i = 2; i < n; i++) {
            int pick = nums[i] + dp[i - 2];
            int notPick = dp[i - 1];
            dp[i] = Math.max(pick, notPick);
        }

        return dp[n - 1];
    }

    /* -------------------------------------------------
       4️⃣ SPACE OPTIMIZATION
       ------------------------------------------------- */
    public static int robSpaceOptimized(int[] nums) {
        int n = nums.length;
        if (n == 1) return nums[0];

        int prev2 = nums[0];                     // dp[i-2]
        int prev1 = Math.max(nums[0], nums[1]);  // dp[i-1]

        for (int i = 2; i < n; i++) {
            int pick = nums[i] + prev2;
            int notPick = prev1;
            int curr = Math.max(pick, notPick);

            prev2 = prev1;
            prev1 = curr;
        }

        return prev1;
    }

    /* -------------------------------------------------
       MAIN METHOD (Testing)
       ------------------------------------------------- */
    public static void main(String[] args) {
        int[] nums = {2, 7, 9, 3, 1};
        int n = nums.length;

        System.out.println("Recursion: " +
                robRecursion(n - 1, nums));

        System.out.println("Memoization: " +
                robMemo(n - 1, nums));

        System.out.println("Tabulation: " +
                robTabulation(nums));

        System.out.println("Space Optimized: " +
                robSpaceOptimized(nums));
    }
}




//QUESTION :

/*
 HOUSE ROBBER I (Maximum Sum of Non-Adjacent Elements)

 Problem:
 You are given an array of integers nums[].
 Each element represents the amount of money in a house.

 Constraint:
 - You cannot rob two adjacent houses.

 Task:
 Return the maximum amount of money you can rob without
 robbing two adjacent houses.

 Example:
 nums = {2, 7, 9, 3, 1}
 Output = 12

 Explanation:
 Pick houses with values: 2 + 9 + 1 = 12

 Core Idea (Pick / Not Pick):
 At each index, you have two choices:
 1) Pick the current element:
    → add nums[i] and skip previous (i - 2)
 2) Not pick the current element:
    → move to previous index (i - 1)

 Recurrence:
 f(i) = max(
     nums[i] + f(i - 2),   // pick
     f(i - 1)              // not pick
 )
*/
