//Leetcode 56 - Merge Intervals
package basic;

import java.util.*;

public class MergeIntervals {

    public static int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        List<int[]> result = new ArrayList<>();
        int[] current = intervals[0];
        result.add(current);
        for (int[] interval : intervals) {
            if (interval[0] <= current[1]) {
                current[1] = Math.max(current[1], interval[1]);
            }
            else {
                current = interval;
                result.add(current);
            }
        }
        return result.toArray(new int[result.size()][]);
    }

    public static void main(String[] args) {
        int[][] intervals = {{1,3},{2,6},{8,10},{15,18}};
        int[][] result = merge(intervals);
        for(int[] i : result){
            System.out.println(Arrays.toString(i));
        }
    }
}



//Question 
//
//You are given an array of intervals where each interval is represented as [start, end]. Some intervals may overlap.
//
//Your task is to merge all overlapping intervals and return a list of non-overlapping intervals that cover all the intervals in the input.
//
//Example:
//
//Input
//
//[[1,3],[2,6],[8,10],[15,18]]
//
//Output
//
//[[1,6],[8,10],[15,18]]
//
//Because [1,3] and [2,6] overlap and can be merged into [1,6].

//###############################################################################

//Key Idea

//Sort intervals by start time
//
//Compare the current interval with the previous merged interval
//
//Two cases:
//
//Case 1 — Overlap
//
//current.start <= previous.end
//
//Merge:
//
//previous.end = max(previous.end, current.end)
//
//Case 2 — No Overlap
//
//Add the interval to result.