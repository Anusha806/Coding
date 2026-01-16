//Question is at the end
package dp;

import java.util.Arrays;

public class CherryPickup1 {

    private static final int NEG_INF = (int) -1e9;

    /* -------------------------------------------------
       MAIN FUNCTION
       ------------------------------------------------- */
    public static int cherryPickup(int[][] grid) {
        int n = grid.length;
        int[][][] dp = new int[n][n][n];

        for (int[][] layer : dp)
            for (int[] row : layer)
                Arrays.fill(row, Integer.MIN_VALUE);

        int ans = dfs(0, 0, 0, grid, dp);
        return Math.max(ans, 0);
    }

    /* -------------------------------------------------
       DFS + MEMOIZATION
       ------------------------------------------------- */
    private static int dfs(int r1, int c1, int r2,
                           int[][] grid, int[][][] dp) {

        int n = grid.length;
        int c2 = r1 + c1 - r2;

        // Out of bounds or thorn
        if (r1 >= n || c1 >= n || r2 >= n || c2 >= n
                || grid[r1][c1] == -1
                || grid[r2][c2] == -1) {
            return NEG_INF;
        }

        // Reached end
        if (r1 == n - 1 && c1 == n - 1) {
            return grid[r1][c1];
        }

        if (dp[r1][c1][r2] != Integer.MIN_VALUE) {
            return dp[r1][c1][r2];
        }

        int cherries = 0;

        // Same cell → count once
        if (r1 == r2 && c1 == c2) {
            cherries = grid[r1][c1];
        } else {
            cherries = grid[r1][c1] + grid[r2][c2];
        }

        int maxNext = Math.max(
                Math.max(
                        dfs(r1 + 1, c1, r2 + 1, grid, dp),
                        dfs(r1, c1 + 1, r2, grid, dp)
                ),
                Math.max(
                        dfs(r1 + 1, c1, r2, grid, dp),
                        dfs(r1, c1 + 1, r2 + 1, grid, dp)
                )
        );

        cherries += maxNext;
        dp[r1][c1][r2] = cherries;
        return cherries;
    }

    /* -------------------------------------------------
       MAIN METHOD (Testing)
       ------------------------------------------------- */
    public static void main(String[] args) {
        int[][] grid = {
                {0, 1, -1},
                {1, 0, -1},
                {1, 1,  1}
        };

        System.out.println("Cherry Pickup I: " +
                cherryPickup(grid));
    }
}

//QUESTION :

/*
 CHERRY PICKUP I (LeetCode 741)

 Problem:
 You are given an n x n grid where:
 - 1  → cell has a cherry
 - 0  → empty cell
 - -1 → thorn (blocked cell)

 You start at (0,0) and want to reach (n-1,n-1),
 moving only:
   - Right
   - Down

 After reaching the end, you return back to (0,0),
 moving only:
   - Left
   - Up

 Rules:
 - When a cherry is picked, it disappears
 - You cannot step on blocked cells
 - Maximize total cherries collected

 Key Insight:
 Instead of thinking as "go + return",
 think of TWO people starting from (0,0)
 and moving to (n-1,n-1) at the SAME TIME.

 Both move only Right or Down.

 If both land on the same cell, count cherry only once.

 State Definition:
 dp[r1][c1][r2] → max cherries collected when:
   person1 is at (r1, c1)
   person2 is at (r2, c2)

 Since:
 r1 + c1 = r2 + c2
 → c2 = r1 + c1 - r2

 Final Answer:
 max(dp[0][0][0], 0)
*/

