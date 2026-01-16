//package dp;
//import java.util.*;
//
//public class MaxTreasureWithCheckpoints {
//
//    public static int Maxtreasure(
//            int i, int j,
//            int[][] grid,
//            int c, int[][] checkpoints,
//            int n, int m,
//            int visited,
//            int b1, int b2, int b3   // boost buckets
//    ) {
//
//        // Out of bounds
//        if (i >= n || j >= m) return Integer.MIN_VALUE;
//
//        // Check if current cell is a checkpoint
//        boolean isCheckpoint = false;
//        for (int k = 0; k < c; k++) {
//            if (checkpoints[k][0] == i + 1 && checkpoints[k][1] == j + 1) {
//                isCheckpoint = true;
//                break;
//            }
//        }
//
//        int activeBoosts = b1 + b2 + b3;
//        int currValue = grid[i][j];
//
//        // ✅ Apply boost if active
//        if (activeBoosts > 0) {
//            currValue *= (1 << activeBoosts);
//        }
//
//        // Destination
//        if (i == n - 1 && j == m - 1) {
//            if (visited + (isCheckpoint ? 1 : 0) == c)
//                return currValue;
//            return Integer.MIN_VALUE;
//        }
//
//        // ⏳ Move time forward (boosts decay)
//        int nb1 = b2;
//        int nb2 = b3;
//        int nb3 = 0;
//
//        // 🎯 Activate boost AFTER collecting checkpoint cell
//        int newVisited = visited;
//        if (isCheckpoint) {
//            newVisited++;
//            nb3++;   // new boost with 3 moves
//        }
//
//        int down = Maxtreasure(
//                i + 1, j, grid, c, checkpoints, n, m,
//                newVisited, nb1, nb2, nb3
//        );
//
//        int right = Maxtreasure(
//                i, j + 1, grid, c, checkpoints, n, m,
//                newVisited, nb1, nb2, nb3
//        );
//
//        int bestNext = Math.max(down, right);
//        if (bestNext == Integer.MIN_VALUE) return Integer.MIN_VALUE;
//
//        return currValue + bestNext;
//    }
//
//    public static void main(String[] args) {
//
//        int N = 5, M = 5;
//
//        int[][] grid = {
//                {7, 5, 13, 15, 13},
//                {17, 15, 16, 3, 10},
//                {4, 12, 13, 9, 16},
//                {8, 14, 7, 1, 17},
//                {18, 12, 11, 1, 14}
//        };
//
//        int C = 3;
//
//        int[][] checkpoints = {
//                {2, 1},
//                {3, 1},
//                {3, 5}
//        };
//
//        int result = Maxtreasure(0, 0, grid, C, checkpoints, N, M,
//                                 0, 0, 0, 0);
//        System.out.println(result);
//    }
//}



package dp;
import java.util.*;

public class MaxTreasureWithCheckpoints {

    static int N, M, C;
    static int[][] grid;
    static int[][] checkpoints;

    // dp[row][col][mask][b1][b2][b3]
    static Integer[][][][][][] dp;

    static int solve(int i, int j, int mask, int b1, int b2, int b3) {

        // Out of bounds
        if (i >= N || j >= M) return Integer.MIN_VALUE;

        // Memo check
        if (dp[i][j][mask][b1][b2][b3] != null)
            return dp[i][j][mask][b1][b2][b3];

        // Check if current cell is a checkpoint
        int checkpointIndex = -1;
        for (int k = 0; k < C; k++) {
            if (checkpoints[k][0] == i + 1 && checkpoints[k][1] == j + 1) {
                checkpointIndex = k;
                break;
            }
        }

        int currValue = grid[i][j];

        // Apply boost from PREVIOUS checkpoints
        int activeBoosts = b1 + b2 + b3;
        if (activeBoosts > 0) {
            currValue *= (1 << activeBoosts);
        }

        // Destination cell
        if (i == N - 1 && j == M - 1) {

            int finalMask = mask;
            if (checkpointIndex != -1)
                finalMask |= (1 << checkpointIndex);

            if (finalMask == (1 << C) - 1)
                return dp[i][j][mask][b1][b2][b3] = currValue;

            return dp[i][j][mask][b1][b2][b3] = Integer.MIN_VALUE;
        }

        // ⏳ Advance time: shift boost buckets
        int nb1 = b2;
        int nb2 = b3;
        int nb3 = 0;

        int newMask = mask;

        // Activate new boost AFTER collecting checkpoint cell
        if (checkpointIndex != -1 && (mask & (1 << checkpointIndex)) == 0) {
            newMask |= (1 << checkpointIndex);
            nb3++;  // new boost lasts 3 moves
        }

        int down = solve(i + 1, j, newMask, nb1, nb2, nb3);
        int right = solve(i, j + 1, newMask, nb1, nb2, nb3);

        int bestNext = Math.max(down, right);
        int ans = (bestNext == Integer.MIN_VALUE)
                ? Integer.MIN_VALUE
                : currValue + bestNext;

        dp[i][j][mask][b1][b2][b3] = ans;
        return ans;
    }

    public static void main(String[] args) {

        N = 5;
        M = 5;

        grid = new int[][]{
                {7, 5, 13, 15, 13},
                {17, 15, 16, 3, 10},
                {4, 12, 13, 9, 16},
                {8, 14, 7, 1, 17},
                {18, 12, 11, 1, 14}
        };

        checkpoints = new int[][]{
                {2, 1},
                {3, 1},
                {3, 5}
        };

        C = checkpoints.length;

        // Initialize DP
        dp = new Integer[N][M][1 << C][C + 1][C + 1][C + 1];

        int result = solve(0, 0, 0, 0, 0, 0);
        System.out.println("Maximum Treasure = " + result);
    }
}
