//Leetcode 169 , Explanation is at the end

package basic;

public class MajorityElementI {

    // Method to find element appearing more than n/2 times
    public static int majorityElement(int[] nums) {

        int candidate = 0;
        int count = 0;

        for (int i = 0; i < nums.length; i++) {

            if (count == 0) {
            	candidate = nums[i];
            }

            if (nums[i] == candidate) {
                count++;
            }
            else {
                count--;
            }
        }
        return candidate;
    }

    // ---------------- MAIN METHOD ----------------
    public static void main(String[] args) {

        int[] nums = {2, 2, 1, 1, 1, 2, 2};

        int ans = majorityElement(nums);

        System.out.println("Majority Element (> n/2): " + ans);
    }
}

/*
====================== SIMPLE EXPLANATION ======================

PROBLEM:
Find the element that appears MORE THAN n/2 times.

---------------------------------------------------------------
VERY IMPORTANT FACT:
There can be ONLY ONE such element.
- If you see 2 different numbers you remove both because both cannot be majority
Example : 
[5,7] --> Both cannot be majority
[2,2,1] --> 2,1 will cancel each other , and the other number will be majority

---------------------------------------------------------------
WHAT IS candidate?
candidate is our current GUESS for majority element.

WHAT IS count?
count is NOT frequency.
count means:
"How much extra support this candidate has
after canceling opposite numbers."

---------------------------------------------------------------
CORE IDEA (SUPPORT & CANCEL):

1) Same number → SUPPORT
   If num == candidate → count++

2) Different number → CANCEL
   If num != candidate → count--

This means:
One candidate number and one different number
cancel each other.

---------------------------------------------------------------
WHY DOES THIS WORK?

The majority element appears MORE than all other elements combined.

So even after all possible cancellations,
it will still have EXTRA occurrences left.

---------------------------------------------------------------
WHAT DOES count == 0 MEAN?

count == 0 DOES NOT mean candidate is wrong.

It only means:
"So far, supporters and opposers are equal."

So we start fresh and choose a new candidate.

---------------------------------------------------------------
WHY NO SECOND PASS?

Because the problem GUARANTEES
that a majority element (> n/2) always exists.

So the final candidate MUST be the answer.

---------------------------------------------------------------
ONE LINE TO REMEMBER:
Cancel different numbers in pairs.
The element that cannot be fully canceled
is the majority element.

===============================================================
*/