//Question is at the end
package dp;
import java.util.Arrays;

public class MinimumPathSum {

    /* -------------------------------------------------
       1️⃣ RECURSION
       ------------------------------------------------- */
    public static int minPathSumRecursion(int i, int j, int[][] grid) {
        if (i == 0 && j == 0) return grid[0][0];
        if (i < 0 || j < 0) return Integer.MAX_VALUE;

        int up = minPathSumRecursion(i - 1, j, grid);
        int left = minPathSumRecursion(i, j - 1, grid);

        return grid[i][j] + Math.min(up, left);
    }

    /* -------------------------------------------------
       2️⃣ MEMOIZATION (Top-Down DP)
       ------------------------------------------------- */
    public static int minPathSumMemo(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];

        for (int[] row : dp) Arrays.fill(row, -1);

        return memoHelper(m - 1, n - 1, grid, dp);
    }

    private static int memoHelper(int i, int j,
                                  int[][] grid, int[][] dp) {
        if (i == 0 && j == 0) return grid[0][0];
        if (i < 0 || j < 0) return Integer.MAX_VALUE;

        if (dp[i][j] != -1) return dp[i][j];

        dp[i][j] = grid[i][j] + Math.min(
                memoHelper(i - 1, j, grid, dp),
                memoHelper(i, j - 1, grid, dp)
        );

        return dp[i][j];
    }

    /* -------------------------------------------------
       3️⃣ TABULATION (Bottom-Up DP)
       ------------------------------------------------- */
    public static int minPathSumTabulation(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];

        dp[0][0] = grid[0][0];

        // First column
        for (int i = 1; i < m; i++) {
            dp[i][0] = grid[i][0] + dp[i - 1][0];
        }

        // First row
        for (int j = 1; j < n; j++) {
            dp[0][j] = grid[0][j] + dp[0][j - 1];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = grid[i][j] +
                        Math.min(dp[i - 1][j], dp[i][j - 1]);
            }
        }

        return dp[m - 1][n - 1];
    }

    /* -------------------------------------------------
       4️⃣ SPACE OPTIMIZATION
       ------------------------------------------------- */
    public static int minPathSumSpaceOptimized(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[] prev = new int[n];
        prev[0] = grid[0][0];

        // First row
        for (int j = 1; j < n; j++) {
            prev[j] = grid[0][j] + prev[j - 1];
        }

        for (int i = 1; i < m; i++) {
            int[] curr = new int[n];
            curr[0] = grid[i][0] + prev[0];

            for (int j = 1; j < n; j++) {
                curr[j] = grid[i][j] +
                        Math.min(curr[j - 1], prev[j]);
            }

            prev = curr;
        }

        return prev[n - 1];
    }

    /* -------------------------------------------------
       MAIN METHOD (Testing)
       ------------------------------------------------- */
    public static void main(String[] args) {
        int[][] grid = {
                {1, 3, 1},
                {1, 5, 1},
                {4, 2, 1}
        };

        System.out.println("Recursion: " +
                minPathSumRecursion(
                        grid.length - 1,
                        grid[0].length - 1,
                        grid));

        System.out.println("Memoization: " +
                minPathSumMemo(grid));

        System.out.println("Tabulation: " +
                minPathSumTabulation(grid));

        System.out.println("Space Optimized: " +
                minPathSumSpaceOptimized(grid));
    }
}






//QUESTION :

/*
 MINIMUM PATH SUM

 Problem:
 You are given an m x n grid filled with non-negative numbers.
 Each cell grid[i][j] represents the cost of that cell.

 You start at the top-left cell (0,0)
 and want to reach the bottom-right cell (m-1,n-1).

 Rules:
 - You can move ONLY:
     1) Right
     2) Down

 Task:
 Return the minimum path sum from start to end.

 Example:
 grid =
 [
   [1, 3, 1],
   [1, 5, 1],
   [4, 2, 1]
 ]

 Path: 1 → 3 → 1 → 1 → 1
 Output = 7

 Core Idea:
 At each cell, choose the minimum cost path
 coming from top or left.

 Recurrence:
 f(i, j) = grid[i][j] + min(
                f(i - 1, j),
                f(i, j - 1)
            )

 Base Case:
 f(0,0) = grid[0][0]
*/

