//Question is at the end
//Notes : https://github.com/Anusha806/DPNOTES/blob/main/16.DpOnStrings.pdf

package dp;
import java.util.Arrays;

public class LongestCommonSubsequence {

    /* -------------------------------------------------
       1️⃣ RECURSION
       ------------------------------------------------- */
    public static int lcsRecursion(int i, int j, String s1, String s2) {
        if (i < 0 || j < 0) return 0;

        if (s1.charAt(i) == s2.charAt(j)) {
            return 1 + lcsRecursion(i - 1, j - 1, s1, s2);
        }

        return Math.max(
                lcsRecursion(i - 1, j, s1, s2),
                lcsRecursion(i, j - 1, s1, s2)
        );
    }

    /* -------------------------------------------------
       2️⃣ MEMOIZATION (Top-Down DP)
       ------------------------------------------------- */
    public static int lcsMemo(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        int[][] dp = new int[n][m];

        for (int[] row : dp) Arrays.fill(row, -1);

        return memoHelper(n - 1, m - 1, s1, s2, dp);
    }

    private static int memoHelper(int i, int j,
                                  String s1, String s2,
                                  int[][] dp) {
        if (i < 0 || j < 0) return 0;

        if (dp[i][j] != -1) return dp[i][j];

        if (s1.charAt(i) == s2.charAt(j)) {
            return dp[i][j] =
                    1 + memoHelper(i - 1, j - 1, s1, s2, dp);
        }

        return dp[i][j] = Math.max(
                memoHelper(i - 1, j, s1, s2, dp),
                memoHelper(i, j - 1, s1, s2, dp)
        );
    }

    /* -------------------------------------------------
       3️⃣ TABULATION (Bottom-Up DP)
       ------------------------------------------------- */
    public static int lcsTabulation(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        int[][] dp = new int[n + 1][m + 1];

        // dp[0][*] and dp[*][0] are 0 by default

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(
                            dp[i - 1][j],
                            dp[i][j - 1]
                    );
                }
            }
        }

        return dp[n][m];
    }

    /* -------------------------------------------------
       4️⃣ SPACE OPTIMIZATION
       ------------------------------------------------- */
    public static int lcsSpaceOptimized(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();

        int[] prev = new int[m + 1];
        int[] curr = new int[m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    curr[j] = 1 + prev[j - 1];
                } else {
                    curr[j] = Math.max(prev[j], curr[j - 1]);
                }
            }
            prev = curr.clone();
        }

        return prev[m];
    }

    /* -------------------------------------------------
       MAIN METHOD (Testing)
       ------------------------------------------------- */
    public static void main(String[] args) {
        String s1 = "abcde";
        String s2 = "ace";

        System.out.println("Recursion: " +
                lcsRecursion(s1.length() - 1, s2.length() - 1, s1, s2));

        System.out.println("Memoization: " +
                lcsMemo(s1, s2));

        System.out.println("Tabulation: " +
                lcsTabulation(s1, s2));

        System.out.println("Space Optimized: " +
                lcsSpaceOptimized(s1, s2));
    }
}




//QUESTION :

/*
 LONGEST COMMON SUBSEQUENCE (LCS)

 Problem:
 You are given two strings s1 and s2.

 Task:
 Return the length of the longest subsequence that appears
 in BOTH strings in the SAME order (not necessarily contiguous).

 Example:
 s1 = "abcde"
 s2 = "ace"

 Longest Common Subsequence = "ace"
 Output = 3

 Core Idea:
 We compare characters at current indices of both strings.

 Cases:
 1) Characters MATCH:
    → Include this character in LCS
    → Move both indices back
    f(i, j) = 1 + f(i - 1, j - 1)

 2) Characters DO NOT MATCH:
    → Explore both possibilities
    f(i, j) = max(
                  f(i - 1, j),
                  f(i, j - 1)
              )

 Base Case:
 If any index < 0 → return 0
*/
