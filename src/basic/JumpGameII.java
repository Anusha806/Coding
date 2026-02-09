//Leetcode 45 - Jump Game II
package basic;

public class JumpGameII {

    public static void main(String[] args) {

        int[] nums = {2, 3, 1, 1, 4};

        JumpGameII obj = new JumpGameII();
        int result = obj.jump(nums);

        System.out.println("Minimum jumps: " + result);
    }

    // This method starts the recursion from index 0
    public int jump(int[] nums) {
        return solve(nums, 0);
    }

    // Recursive helper method
    private int solve(int[] nums, int index) {

        // Base case:
        // If we reach or cross the last index,
        // no more jumps are required
        if (index >= nums.length - 1) {
            return 0;
        }

        // Stores the minimum jumps needed from this index
        int minJumps = Integer.MAX_VALUE;

        // Try all possible jump lengths from current index
        for (int step = 1; step <= nums[index]; step++) {

            // Calculate the next index we can jump to
            int next = index + step;

            // Check bounds
            if (next < nums.length) {

                // Recursively find jumps needed from the next index
                int jumps = solve(nums, next);

                // If a valid path exists, update minimum jumps
                if (jumps != Integer.MAX_VALUE) {
                    minJumps = Math.min(minJumps, jumps + 1);
                }
            }
        }

        // Return the minimum jumps needed from this index
        return minJumps;
    }
}
