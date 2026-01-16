//Question is at the end
package dp;
import java.util.Arrays;

public class GeeksTraining {

    /* -------------------------------------------------
       1️⃣ RECURSION
       ------------------------------------------------- */
    public static int trainingRecursion(int day, int last, int[][] arr) {
        if (day == 0) {
            int max = 0;
            for (int task = 0; task < 3; task++) {
                if (task != last) {
                    max = Math.max(max, arr[0][task]);
                }
            }
            return max;
        }

        int max = 0;
        for (int task = 0; task < 3; task++) {
            if (task != last) {
                int points =
                        arr[day][task]
                        + trainingRecursion(day - 1, task, arr);
                max = Math.max(max, points);
            }
        }
        return max;
    }

    /* -------------------------------------------------
       2️⃣ MEMOIZATION (Top-Down DP)
       ------------------------------------------------- */
    public static int trainingMemo(int[][] arr) {
        int n = arr.length;
        int[][] dp = new int[n][4];
        for (int[] row : dp) Arrays.fill(row, -1);

        return memoHelper(n - 1, 3, arr, dp);
    }

    private static int memoHelper(int day, int last,
                                  int[][] arr, int[][] dp) {
        if (dp[day][last] != -1) return dp[day][last];

        if (day == 0) {
            int max = 0;
            for (int task = 0; task < 3; task++) {
                if (task != last) {
                    max = Math.max(max, arr[0][task]);
                }
            }
            return dp[day][last] = max;
        }

        int max = 0;
        for (int task = 0; task < 3; task++) {
            if (task != last) {
                int points =
                        arr[day][task]
                        + memoHelper(day - 1, task, arr, dp);
                max = Math.max(max, points);
            }
        }

        dp[day][last] = max;
        return max;
    }

    /* -------------------------------------------------
       3️⃣ TABULATION (Bottom-Up DP)
       ------------------------------------------------- */
    public static int trainingTabulation(int[][] arr) {
        int n = arr.length;
        int[][] dp = new int[n][4];

        // Base case: day 0
        dp[0][0] = Math.max(arr[0][1], arr[0][2]);
        dp[0][1] = Math.max(arr[0][0], arr[0][2]);
        dp[0][2] = Math.max(arr[0][0], arr[0][1]);
        dp[0][3] = Math.max(arr[0][0],
                     Math.max(arr[0][1], arr[0][2]));

        for (int day = 1; day < n; day++) {
            for (int last = 0; last < 4; last++) {
                dp[day][last] = 0;
                for (int task = 0; task < 3; task++) {
                    if (task != last) {
                        int points =
                                arr[day][task]
                                + dp[day - 1][task];
                        dp[day][last] =
                                Math.max(dp[day][last], points);
                    }
                }
            }
        }

        return dp[n - 1][3];
    }

    /* -------------------------------------------------
       4️⃣ SPACE OPTIMIZATION
       ------------------------------------------------- */
    public static int trainingSpaceOptimized(int[][] arr) {
        int n = arr.length;
        int[] prev = new int[4];

        // Base case
        prev[0] = Math.max(arr[0][1], arr[0][2]);
        prev[1] = Math.max(arr[0][0], arr[0][2]);
        prev[2] = Math.max(arr[0][0], arr[0][1]);
        prev[3] = Math.max(arr[0][0],
                    Math.max(arr[0][1], arr[0][2]));

        for (int day = 1; day < n; day++) {
            int[] curr = new int[4];
            for (int last = 0; last < 4; last++) {
                curr[last] = 0;
                for (int task = 0; task < 3; task++) {
                    if (task != last) {
                        int points =
                                arr[day][task] + prev[task];
                        curr[last] =
                                Math.max(curr[last], points);
                    }
                }
            }
            prev = curr;
        }

        return prev[3];
    }

    /* -------------------------------------------------
       MAIN METHOD (Testing)
       ------------------------------------------------- */
    public static void main(String[] args) {
        int[][] arr = {
                {10, 40, 70},
                {20, 50, 80},
                {30, 60, 90}
        };

        System.out.println("Recursion: " +
                trainingRecursion(arr.length - 1, 3, arr));

        System.out.println("Memoization: " +
                trainingMemo(arr));

        System.out.println("Tabulation: " +
                trainingTabulation(arr));

        System.out.println("Space Optimized: " +
                trainingSpaceOptimized(arr));
    }
}




//QUESTION 


/*
 GEEK'S TRAINING PROBLEM (Ninja Training – GFG)

 Problem:
 Geek attends a training program for n days.

 Each day, he can perform one of the following activities:
 0 → Running
 1 → Fighting
 2 → Learning Practice

 Merit points are given in a 2D array arr[i][j]:
 arr[i][0] → Running points on day i
 arr[i][1] → Fighting points on day i
 arr[i][2] → Learning points on day i

 Constraint:
 - Geek cannot perform the SAME activity on two consecutive days.

 Task:
 Maximize the total merit points over n days.

 Core Idea:
 At each day, choose an activity different from the previous day
 that gives maximum total points.

 State Definition:
 f(day, last) → maximum points achievable from day 0 to day
                if activity 'last' was done on the previous day

 We use last = 3 to represent "no previous activity"
 (used for the first day).

 Recurrence:
 f(day, last) = max over all activities task ≠ last:
                arr[day][task] + f(day - 1, task)

 Base Case:
 day == 0:
   choose the best activity ≠ last
*/
