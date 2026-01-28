//Question is at the end
//Leetcode 46
package basic;

import java.util.*;

public class Permutation {

    static List<List<Integer>> result = new ArrayList<>();

    public static void permute(int[] nums, int index) {

        // BASE CASE
        if (index == nums.length) {

            List<Integer> temp = new ArrayList<>();
            for (int num : nums) {
                temp.add(num);
            }

            result.add(temp);
            return;
        }

        // FIX ONE POSITION AND SWAP
        for (int i = index; i < nums.length; i++) {
            swap(nums, index, i);
            permute(nums, index + 1);
            swap(nums, index, i); // BACKTRACK
        }
    }

    static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {

        int[] nums = {1, 2, 3};

        permute(nums, 0);

        // PRINT RESULT
        System.out.println(result);
    }
}




//QUESTION :
//Given an array nums of distinct integers, return all the possible permutations. You can return the answer in any order.

//Example 1:
//
//Input: nums = [1,2,3]
//Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]