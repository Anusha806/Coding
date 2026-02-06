//LEETCODE - 31
//Question and explaination are at the end
package basic;
import java.util.*;
public class NextPermutation {

    public static void nextPermutation(int[] nums) {

        int n = nums.length;

        // STEP 1: Find the pivot (first index from right where nums[i] < nums[i+1])
        int i = n - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        // STEP 2: If pivot exists, find the smallest number greater than pivot (from right)
        if (i >= 0) {
            int j = n - 1;
            while (nums[j] <= nums[i]) {
                j--;
            }
            // STEP 3: Swap pivot and successor
            swap(nums, i, j);
        }

        // STEP 4: Reverse the right part to get the smallest order
        reverse(nums, i + 1, n - 1);
    }

    // Helper method to swap
    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // Helper method to reverse array from left to right
    private static void reverse(int[] nums, int left, int right) {
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }

    // Main method to test
    public static void main(String[] args) {
        int[] nums = {2, 3, 1};
        nextPermutation(nums);
        System.out.println(Arrays.toString(nums));
    }
}



//Question:

//Given an array of integers, rearrange it into the next lexicographically greater permutation.
//If such a permutation does not exist, rearrange it into the smallest possible order (ascending).
//The operation must be done in-place.


//Lexicographical order is the same way:
//words are arranged in a dictionary
//numbers are compared from left to right
//The first different position decides which one is bigger.

//Compare:

//[1, 3, 2]
//[1, 2, 3]

//Check from left:
//Index 0: 1 == 1 → same
//Index 1: 3 > 2 → stop
//So [1, 3, 2] is lexicographically greater than [1, 2, 3].



//Test Case
//Input
//[1, 2, 3]
//Output
//[1, 3, 2]

//Detailed Step-by-Step Explanation

//We want the next permutation that is just bigger than [1, 2, 3].

//Step 1: Find the pivot (scan from RIGHT to LEFT)

//We scan from the end to find the first position i such that:

//nums[i] < nums[i + 1]
//
//Check:
//2 < 3 → true

//So:
//Pivot index = 1
//Pivot value = 2

//This means:
//We can make the permutation bigger by increasing 2.


//Step 2: Find the smallest number greater than the pivot
//
//Look at the elements to the right of the pivot:
//Right side = [3]
//Smallest number greater than 2 is 3


//Step 3: Swap pivot and that number

//Before swap:
//[1, 2, 3]
//After swap:
//[1, 3, 2]


//Step 4: Reverse the right part
//Elements to the right of the pivot position are already in smallest order,
//so reversing does not change anything here.

//Final Answer
//[1, 3, 2]

//This is the next lexicographically greater permutation of [1, 2, 3].
