//Leetcode 229 , Explanation is at the end
package basic;

import java.util.*;

public class MajorityElementII {

    // Method to find all elements appearing more than n/3 times
    public static List<Integer> majorityElement(int[] nums) {

        int cand1 = 0, cand2 = 0;
        int count1 = 0, count2 = 0;

        // ---------- FIRST PASS ----------
        // Find possible candidates using cancellation logic
        for (int i = 0; i < nums.length; i++) {

            if (nums[i] == cand1) {
                count1++;
            }
            else if (nums[i] == cand2) {
                count2++;
            }
            else if (count1 == 0) {
                cand1 = nums[i];
                count1 = 1;
            }
            else if (count2 == 0) {
                cand2 = nums[i];
                count2 = 1;
            }
            else {
                count1--;
                count2--;
            }
        }

        // ---------- SECOND PASS ----------
        // Verify actual counts
        count1 = 0;
        count2 = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == cand1) count1++;
            else if (nums[i] == cand2) count2++;
        }

        List<Integer> result = new ArrayList<>();

        if (count1 > nums.length / 3) result.add(cand1);
        if (count2 > nums.length / 3) result.add(cand2);

        return result;
    }

    // ---------- MAIN METHOD ----------
    public static void main(String[] args) {

        int[] nums = {1, 2, 3, 1, 2, 1, 1};

        List<Integer> ans = majorityElement(nums);

        System.out.println("Majority Elements (> n/3): " + ans);
    }
}

/*
====================== EXPLANATION ======================

PROBLEM:
Find all elements that appear more than n/3 times in the array.

KEY OBSERVATION:

Earlier problem: > n/2 → only ONE majority possible
Now: > n/3 → can be AT MOST TWO elements

Why “at most TWO”? (very important)

Let’s say:
n = 9
n/3 = 3
Can 3 different numbers each appear more than 3 times?

Impossible
3 × 4 = 12 > 9

So:

There can be 0, 1, or 2 majority elements, but never 3

That’s why the algorithm tracks two candidates.

--------------------------------------------------------
ALGORITHM USED:
Extended Boyer-Moore Voting Algorithm

--------------------------------------------------------
WHY TWO CANDIDATES?
- For n/2 → 1 candidate
- For n/3 → 2 candidates

--------------------------------------------------------
Rule 1: Support
If num == candidate1 → count1++
Else if num == candidate2 → count2++

Rule 2: Empty slot
Else if count1 == 0 → set candidate1 = num, count1 = 1
Else if count2 == 0 → set candidate2 = num, count2 = 1

Rule 3: Cancel
Else → count1-- and count2--


FIRST PASS (CANDIDATE SELECTION):

Rules:
1. If current number matches candidate1 → count1++
2. Else if it matches candidate2 → count2++
3. Else if count1 == 0 → assign candidate1
4. Else if count2 == 0 → assign candidate2
5. Else → decrement both counts (cancellation)

Meaning:
- Same number supports a candidate
- Different number cancels both candidates
- This removes groups of 3 different elements

--------------------------------------------------------
SECOND PASS (VERIFICATION):

Why needed?
- First pass gives only POSSIBLE candidates
- Some may not actually occur more than n/3 times

So:
- Count actual frequency of both candidates
- Add only those with count > n/3

--------------------------------------------------------
TIME & SPACE COMPLEXITY:
- Time: O(n)
- Space: O(1)

--------------------------------------------------------
FINAL IDEA:
Elements appearing more than n/3 times cannot be fully canceled,
so they survive the cancellation process.

========================================================
*/