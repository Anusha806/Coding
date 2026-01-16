//Question is at the end
package dp;
import java.util.Arrays;

public class UniquePaths {

    /* -------------------------------------------------
       1️⃣ RECURSION
       ------------------------------------------------- */
    public static int uniquePathsRecursion(int i, int j) {
        if (i == 0 && j == 0) return 1;
        if (i < 0 || j < 0) return 0;

        int up = uniquePathsRecursion(i - 1, j);
        int left = uniquePathsRecursion(i, j - 1);

        return up + left;
    }

    /* -------------------------------------------------
       2️⃣ MEMOIZATION (Top-Down DP)
       ------------------------------------------------- */
    public static int uniquePathsMemo(int m, int n) {
        int[][] dp = new int[m][n];
        for (int[] row : dp) Arrays.fill(row, -1);
        return memoHelper(m - 1, n - 1, dp);
    }

    private static int memoHelper(int i, int j, int[][] dp) {
        if (i == 0 && j == 0) return 1;
        if (i < 0 || j < 0) return 0;

        if (dp[i][j] != -1) return dp[i][j];

        dp[i][j] = memoHelper(i - 1, j, dp)
                 + memoHelper(i, j - 1, dp);

        return dp[i][j];
    }

    /* -------------------------------------------------
       3️⃣ TABULATION (Bottom-Up DP)
       ------------------------------------------------- */
    public static int uniquePathsTabulation(int m, int n) {
        int[][] dp = new int[m][n];

        // Base case: first row and first column
        for (int i = 0; i < m; i++) dp[i][0] = 1;
        for (int j = 0; j < n; j++) dp[0][j] = 1;

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        return dp[m - 1][n - 1];
    }

    /* -------------------------------------------------
       4️⃣ SPACE OPTIMIZATION
       ------------------------------------------------- */
    public static int uniquePathsSpaceOptimized(int m, int n) {
        int[] prev = new int[n];
        Arrays.fill(prev, 1);

        for (int i = 1; i < m; i++) {
            int[] curr = new int[n];
            curr[0] = 1;

            for (int j = 1; j < n; j++) {
                curr[j] = curr[j - 1] + prev[j];
            }

            prev = curr;
        }

        return prev[n - 1];
    }

    /* -------------------------------------------------
       MAIN METHOD (Testing)
       ------------------------------------------------- */
    public static void main(String[] args) {
        int m = 3, n = 7;

        System.out.println("Recursion: " +
                uniquePathsRecursion(m - 1, n - 1));

        System.out.println("Memoization: " +
                uniquePathsMemo(m, n));

        System.out.println("Tabulation: " +
                uniquePathsTabulation(m, n));

        System.out.println("Space Optimized: " +
                uniquePathsSpaceOptimized(m, n));
    }
}





//QUESTION :

/*
 UNIQUE PATHS I

 Problem:
 You are given a grid of size m x n.
 You start at the top-left cell (0,0)
 and want to reach the bottom-right cell (m-1, n-1).

 Rules:
 - You can move ONLY:
     1) Right
     2) Down

 Task:
 Return the total number of unique paths to reach
 the destination.

 Example:
 m = 3, n = 7
 Output = 28

 Core Idea:
 At each cell (i, j), you can come from:
 - Top cell → (i - 1, j)
 - Left cell → (i, j - 1)

 Recurrence:
 f(i, j) = f(i - 1, j) + f(i, j - 1)

 Base Case:
 - If i == 0 and j == 0 → 1 path
 - If i < 0 or j < 0 → 0 paths
*/
