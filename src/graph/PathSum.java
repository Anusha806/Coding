//Question is at the end 
//Leetcode 112 - PATHSUM
package graph;

public class PathSum {
	static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
	
	public static boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;

        if (root.left == null && root.right == null) {
            return targetSum == root.val;
//to exit recursion we change the targetsum to val when we are at the end of path	
        }
        	
//Subtract current node’s value from targetSum
//Try left subtree , right subtree
//If either returns true, we return true

        return hasPathSum(root.left, targetSum - root.val) ||
               hasPathSum(root.right, targetSum - root.val);
    }
	
	public static void main(String[] args) {

        /*
            Constructing the tree:
                    5
                   / \
                  4   8
                 /   / \
                11  13  4
               /  \       \
              7    2       1
        */

	        TreeNode root = new TreeNode(5,
	                new TreeNode(4,
	                        new TreeNode(11,
	                                new TreeNode(7),
	                                new TreeNode(2)
	                        ),
	                        null
	                ),
	                new TreeNode(8,
	                        new TreeNode(13),
	                        new TreeNode(4,
	                                null,
	                                new TreeNode(1)
	                        )
	                )
	        );
	        
//	        So when you write:
//	        	new TreeNode(5, leftNode, rightNode)
//	        	It means:
//	        	val = 5
//	        	left = leftNode
//	        	right = rightNode
//				new treeNode(4) == LEFTSUBTREE of root(5)
// 				new treeNode(8) == RIGHTSUBTREE of root(5)

        int targetSum = 22;

        boolean result = hasPathSum(root, targetSum);
        System.out.println(result);  // Output: true
    }
	
}


//Given the root of a binary tree and an integer targetSum, return true if the tree has a root-to-leaf path such that adding up all the values along the path equals targetSum.
//A leaf is a node with no children.
//Example 1
//Input: root = [1,2,3], targetSum = 5
//Output: false
//Explanation: There are two root-to-leaf paths in the tree:
//(1 --> 2): The sum is 3.
//(1 --> 3): The sum is 4.
//There is no root-to-leaf path with sum = 5.
//Example 2
//Input: root = [5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum = 22
//Output: true
//Explanation: The root-to-leaf path with the target sum is shown.