//Question is at the end

package dp;
import java.util.Arrays;

public class UniquePathsII {

    /* -------------------------------------------------
       1️⃣ RECURSION
       ------------------------------------------------- */
    public static int uniquePathsRecursion(int i, int j, int[][] grid) {
        if (i >= 0 && j >= 0 && grid[i][j] == 1) return 0;
        if (i == 0 && j == 0) return 1;
        if (i < 0 || j < 0) return 0;

        return uniquePathsRecursion(i - 1, j, grid)
             + uniquePathsRecursion(i, j - 1, grid);
    }

    /* -------------------------------------------------
       2️⃣ MEMOIZATION (Top-Down DP)
       ------------------------------------------------- */
    public static int uniquePathsMemo(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        if (grid[0][0] == 1) return 0;

        int[][] dp = new int[m][n];
        for (int[] row : dp) Arrays.fill(row, -1);

        return memoHelper(m - 1, n - 1, grid, dp);
    }

    private static int memoHelper(int i, int j,
                                  int[][] grid, int[][] dp) {
        if (i >= 0 && j >= 0 && grid[i][j] == 1) return 0;
        if (i == 0 && j == 0) return 1;
        if (i < 0 || j < 0) return 0;

        if (dp[i][j] != -1) return dp[i][j];

        dp[i][j] = memoHelper(i - 1, j, grid, dp)
                 + memoHelper(i, j - 1, grid, dp);

        return dp[i][j];
    }

    /* -------------------------------------------------
       3️⃣ TABULATION (Bottom-Up DP)
       ------------------------------------------------- */
    public static int uniquePathsTabulation(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];

        if (grid[0][0] == 1) return 0;

        dp[0][0] = 1;

        // First column
        for (int i = 1; i < m; i++) {
            dp[i][0] = (grid[i][0] == 1) ? 0 : dp[i - 1][0];
        }

        // First row
        for (int j = 1; j < n; j++) {
            dp[0][j] = (grid[0][j] == 1) ? 0 : dp[0][j - 1];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (grid[i][j] == 1) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }

        return dp[m - 1][n - 1];
    }

    /* -------------------------------------------------
       4️⃣ SPACE OPTIMIZATION
       ------------------------------------------------- */
    public static int uniquePathsSpaceOptimized(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[] prev = new int[n];

        prev[0] = (grid[0][0] == 1) ? 0 : 1;

        for (int i = 0; i < m; i++) {
            int[] curr = new int[n];
            curr[0] = (grid[i][0] == 1) ? 0 : prev[0];

            for (int j = 1; j < n; j++) {
                if (grid[i][j] == 1) {
                    curr[j] = 0;
                } else {
                    curr[j] = curr[j - 1] + prev[j];
                }
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
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        };

        System.out.println("Recursion: " +
                uniquePathsRecursion(
                        grid.length - 1,
                        grid[0].length - 1,
                        grid));

        System.out.println("Memoization: " +
                uniquePathsMemo(grid));

        System.out.println("Tabulation: " +
                uniquePathsTabulation(grid));

        System.out.println("Space Optimized: " +
                uniquePathsSpaceOptimized(grid));
    }
}




//QUESTION :

/*
 UNIQUE PATHS II (With Obstacles)

 Problem:
 You are given an m x n grid.
 Some cells have obstacles.

 grid[i][j] = 1 → obstacle
 grid[i][j] = 0 → free cell

 You start at the top-left cell (0,0)
 and want to reach the bottom-right cell (m-1, n-1).

 Rules:
 - You can move ONLY:
     1) Right
     2) Down
 - You cannot pass through obstacle cells

 Task:
 Return the total number of unique paths.

 Example:
 grid =
 [
   [0, 0, 0],
   [0, 1, 0],
   [0, 0, 0]
 ]

 Output = 2

 Core Idea:
 At each cell (i, j):
 - If obstacle → 0 ways
 - Else → ways from top + ways from left

 Recurrence:
 f(i, j) =
     0                                if obstacle
     f(i - 1, j) + f(i, j - 1)        otherwise

 Base Case:
 - If start cell is obstacle → return 0
 - f(0,0) = 1
*/
