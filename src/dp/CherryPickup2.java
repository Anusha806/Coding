//Question is at the end
package dp;
import java.util.Arrays;

public class CherryPickup2 {

    /* -------------------------------------------------
       1️⃣ RECURSION + MEMOIZATION (Top-Down DP)
       ------------------------------------------------- */
    public static int cherryPickupMemo(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[][][] dp = new int[m][n][n];
        for (int[][] layer : dp)
            for (int[] row : layer)
                Arrays.fill(row, -1);

        return dfs(0, 0, n - 1, grid, dp);
    }

    private static int dfs(int row, int c1, int c2,
                           int[][] grid, int[][][] dp) {
        int m = grid.length;
        int n = grid[0].length;

        // Out of bounds
        if (c1 < 0 || c1 >= n || c2 < 0 || c2 >= n) {
            return Integer.MIN_VALUE;
        }

        // Last row
        if (row == m - 1) {
            if (c1 == c2) return grid[row][c1];
            return grid[row][c1] + grid[row][c2];
        }

        if (dp[row][c1][c2] != -1) {
            return dp[row][c1][c2];
        }

        int max = Integer.MIN_VALUE;

        // Try all 3 × 3 moves
        for (int d1 = -1; d1 <= 1; d1++) {
            for (int d2 = -1; d2 <= 1; d2++) {
                int next = dfs(row + 1, c1 + d1, c2 + d2, grid, dp);
                max = Math.max(max, next);
            }
        }

        int cherries;
        if (c1 == c2) {
            cherries = grid[row][c1];
        } else {
            cherries = grid[row][c1] + grid[row][c2];
        }

        dp[row][c1][c2] = cherries + max;
        return dp[row][c1][c2];
    }

    /* -------------------------------------------------
       2️⃣ TABULATION (Bottom-Up DP)
       ------------------------------------------------- */
    public static int cherryPickupTabulation(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[][][] dp = new int[m][n][n];

        // Base case: last row
        for (int c1 = 0; c1 < n; c1++) {
            for (int c2 = 0; c2 < n; c2++) {
                if (c1 == c2)
                    dp[m - 1][c1][c2] = grid[m - 1][c1];
                else
                    dp[m - 1][c1][c2] =
                            grid[m - 1][c1] + grid[m - 1][c2];
            }
        }

        for (int row = m - 2; row >= 0; row--) {
            for (int c1 = 0; c1 < n; c1++) {
                for (int c2 = 0; c2 < n; c2++) {
                    int max = Integer.MIN_VALUE;

                    for (int d1 = -1; d1 <= 1; d1++) {
                        for (int d2 = -1; d2 <= 1; d2++) {
                            int nc1 = c1 + d1;
                            int nc2 = c2 + d2;

                            if (nc1 >= 0 && nc1 < n
                                    && nc2 >= 0 && nc2 < n) {
                                max = Math.max(
                                        max,
                                        dp[row + 1][nc1][nc2]
                                );
                            }
                        }
                    }

                    int cherries =
                            (c1 == c2)
                            ? grid[row][c1]
                            : grid[row][c1] + grid[row][c2];

                    dp[row][c1][c2] = cherries + max;
                }
            }
        }

        return dp[0][0][n - 1];
    }

    /* -------------------------------------------------
       3️⃣ SPACE OPTIMIZATION (2D DP)
       ------------------------------------------------- */
    public static int cherryPickupSpaceOptimized(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[][] prev = new int[n][n];

        // Base case
        for (int c1 = 0; c1 < n; c1++) {
            for (int c2 = 0; c2 < n; c2++) {
                prev[c1][c2] =
                        (c1 == c2)
                        ? grid[m - 1][c1]
                        : grid[m - 1][c1] + grid[m - 1][c2];
            }
        }

        for (int row = m - 2; row >= 0; row--) {
            int[][] curr = new int[n][n];

            for (int c1 = 0; c1 < n; c1++) {
                for (int c2 = 0; c2 < n; c2++) {
                    int max = Integer.MIN_VALUE;

                    for (int d1 = -1; d1 <= 1; d1++) {
                        for (int d2 = -1; d2 <= 1; d2++) {
                            int nc1 = c1 + d1;
                            int nc2 = c2 + d2;

                            if (nc1 >= 0 && nc1 < n
                                    && nc2 >= 0 && nc2 < n) {
                                max = Math.max(
                                        max,
                                        prev[nc1][nc2]
                                );
                            }
                        }
                    }

                    int cherries =
                            (c1 == c2)
                            ? grid[row][c1]
                            : grid[row][c1] + grid[row][c2];

                    curr[c1][c2] = cherries + max;
                }
            }
            prev = curr;
        }

        return prev[0][n - 1];
    }

    /* -------------------------------------------------
       MAIN METHOD (Testing)
       ------------------------------------------------- */
    public static void main(String[] args) {
        int[][] grid = {
                {3, 1, 1},
                {2, 5, 1},
                {1, 5, 5},
                {2, 1, 1}
        };

        System.out.println("Memoization: " +
                cherryPickupMemo(grid));

        System.out.println("Tabulation: " +
                cherryPickupTabulation(grid));

        System.out.println("Space Optimized: " +
                cherryPickupSpaceOptimized(grid));
    }
}

//QUESTION :

/*
 CHERRY PICKUP II (LeetCode 1463)

 Problem:
 You are given an m x n grid where grid[i][j] represents
 the number of cherries in cell (i, j).

 Two robots start at:
 - Robot 1 at (0, 0)
 - Robot 2 at (0, n-1)

 Both robots move from the top row to the bottom row.

 Rules:
 - From cell (i, j), a robot can move to:
     (i+1, j-1), (i+1, j), (i+1, j+1)
 - Robots cannot go outside the grid
 - If both robots land on the same cell,
   cherries are counted ONLY ONCE

 Task:
 Return the maximum number of cherries
 both robots can collect.

 Core Idea:
 DP with 3 states:
 dp[row][col1][col2] → maximum cherries collected
 when robot1 is at (row, col1)
 and robot2 is at (row, col2)
*/