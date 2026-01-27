//Leetcode 2965 

package basic;
import java.util.*;

public class FindMissingAndRepeatedValues {

    public int[] findMissingAndRepeatedValues(int[][] grid) {
        int n = grid.length;
        int size = n * n;

        int[] freq = new int[size + 1];
        int repeated = -1;
        int missing = -1;

        // Count frequency
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                freq[grid[i][j]]++;
            }
        }

        // Find repeated and missing
        for (int num = 1; num <= size; num++) {
            if (freq[num] == 2) {
                repeated = num;
            } else if (freq[num] == 0) {
                missing = num;
            }
        }

        return new int[]{repeated, missing};
    }

    public static void main(String[] args) {
        FindMissingAndRepeatedValues obj = new FindMissingAndRepeatedValues();

        int[][] grid = {
            {1, 3},
            {2, 2}
        };

        int[] result = obj.findMissingAndRepeatedValues(grid);
        System.out.println(Arrays.toString(result)); // [2, 4]
    }
}
