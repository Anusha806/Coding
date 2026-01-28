//leetcode
//Question is at the end
package graph;

import java.util.*;

public class FindPathInGraph {

    public static boolean validPath(int n, int[][] edges, int source, int destination) {

        // Edge case
        if (source == destination) return true;

        // 1. Build adjacency list
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        // 2. BFS
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();

        queue.offer(source);
        visited[source] = true;

        while (!queue.isEmpty()) {
            int node = queue.poll();

            if (node == destination) {
                return true;
            }

            for (int neighbor : graph.get(node)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }

        return false;
    }

    // 3. Main method (test case)
    public static void main(String[] args) {

        int n = 6;
        int[][] edges = {
            {0, 1},
            {0, 2},
            {3, 5},
            {5, 4},
            {4, 3}
        };

        int source = 0;
        int destination = 5;

        boolean result = validPath(n, edges, source, destination);
        System.out.println("Path exists: " + result);
    }
}
//
//
//Problem: Find if Path Exists in Graph (LeetCode 1971)
//
//You are given an undirected graph with n vertices numbered from 0 to n-1.
//The graph edges are given as a 2D array edges, where each edges[i] = [u, v] 
//represents a bi-directional edge between vertices u and v.

//Given:
//n → number of vertices
//edges → list of undirected edges
//source → starting vertex
//destination → target vertex
//Return true if there exists at least one path from source to destination, otherwise return false.

//TestCase

//Input
//n = 3
//edges = [[0,1],[1,2],[2,0]]
//source = 0
//destination = 2
//
//Output
//true
//
//Explaination:
//	The graph connections are:
//
//		0 ↔ 1
//		1 ↔ 2
//		2 ↔ 0
//
//		From node 0, we can:
//
//		go directly to 2
//
//		or go via 1 → 0 → 1 → 2
//
//		Since at least one valid path exists, the answer is true.