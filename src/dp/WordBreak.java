//Leetcode 139

package dp;

import java.util.*;

public class WordBreak {

    public static void main(String[] args) {

        WordBreak obj = new WordBreak();

        String s1 = "leetcode";
        List<String> wordDict1 = Arrays.asList("leet", "code");
        System.out.println(obj.wordBreak(s1, wordDict1)); // true

        String s2 = "catsandog";
        List<String> wordDict2 = Arrays.asList("cats", "dog", "sand", "and", "cat");
        System.out.println(obj.wordBreak(s2, wordDict2)); // false
    }

    public boolean wordBreak(String s, List<String> wordDict) {

        // Convert list to set for faster lookup
        Set<String> wordSet = new HashSet<>(wordDict);

        // dp[i] = true if substring s[0..i-1] can be segmented
        boolean[] dp = new boolean[s.length() + 1];

        dp[0] = true; // empty string is always valid

        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {

                // If prefix is valid AND remaining substring exists in dictionary
                if (dp[j] && wordSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[s.length()];
    }
}