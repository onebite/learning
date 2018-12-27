package com.practice.algorithm.problem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lxl
 * @Date 2018/11/6 9:54
 */
public class Solution {

    public int[] plusOne(int[] digits) {
        int n = digits.length;
        for(int i=n-1;i >=0;i--){
            if (digits[i] < 9){
                digits[i]++;
                return digits;
            }else{
                digits[i] = 0;
            }
        }
        int[] nd = new int[n+1];
        nd[0] = 1;
        return nd;
    }

    public boolean isNumber(String s) {
        s = s.trim();
        if (s.length() == 0) return false;

        if (s.charAt(0) == '+' || s.charAt(0) == '-') {
            s = s.substring(1);
        }

        int pose = s.indexOf("e") >= 0 ? s.indexOf("e") : s.indexOf("E");
        if (pose >= 0) {
            String poste = s.substring(pose + 1);
            if (poste.length() == 0) return false;
            if (poste.charAt(0) == '+' || poste.charAt(0) == '-') {
                poste = poste.substring(1);
            }
            if (!isPureDigit(poste)) return false;
            s = s.substring(0, pose);
        }

        int posdot = s.indexOf(".");
        if (posdot >= 0) {
            String predot = s.substring(0, posdot);
            String postdot = s.substring(posdot + 1);
            if (predot.isEmpty()) return isPureDigit(postdot);
            if (postdot.isEmpty()) return isPureDigit(predot);
            return isPureDigit(predot) && isPureDigit(postdot);
        }

        return isPureDigit(s);
    }

    public boolean isPureDigit(String s) {
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) return false;
        }
        return true;
    }


    public int minPathSum(int[][] grid) {
        if(grid == null || grid.length == 0){
            return 0;
        }
        int m = grid.length;
        int n = grid[0].length;

        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];

        //left side only one way,then sum
        for (int i=1;i < m;i++){
            dp[i][0] = dp[i-1][0] + grid[i][0];
        }
        for(int i=1;i < n;i++){
            dp[0][i] = dp[0][i-1] + grid[0][i];
        }

        for(int i=1;i < m;i++){
            for(int j=1;j < n;j++){
                dp[i][j] = Math.min(dp[i-1][j],dp[i][j-1]) + grid[i][j];
            }
        }

        return dp[m-1][n-1];
    }

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if(obstacleGrid==null||obstacleGrid.length==0){
            return 0;
        }

        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;

        if(obstacleGrid[0][0]==1||obstacleGrid[m-1][n-1]==1){
            return 0;
        }


        int[][] dp = new int[m][n];
        dp[0][0]=1;

        //left column
        for(int i=1; i<m; i++){
            if(obstacleGrid[i][0]==1){
                dp[i][0] = 0;
            }else{
                dp[i][0] = dp[i-1][0];
            }
        }

        //top row
        for(int i=1; i<n; i++){
            if(obstacleGrid[0][i]==1){
                dp[0][i] = 0;
            }else{
                dp[0][i] = dp[0][i-1];
            }
        }

        //fill up cells inside
        for(int i=1; i<m; i++){
            for(int j=1; j<n; j++){
                if(obstacleGrid[i][j]==1){
                    dp[i][j]=0;
                }else{
                    dp[i][j]=dp[i-1][j]+dp[i][j-1];
                }

            }
        }

        return dp[m-1][n-1];
    }

    public int uniquePaths_dp(int m, int n) {
        if(m == 0 || n == 0){
            return 0;
        }
        if(m == 1 || n == 1){
            return 1;
        }
        int[][] dp = new int[m][n];
        //left or side only 1 way
        for(int i=0;i < m;i++){
            dp[i][0] = 1;
        }
        for(int i=0;i < n;i++){
            dp[0][i] = 1;
        }
        //the others can only enter from left or top side
        for(int i=1;i < m;i++){
            for(int j=1;j < n;j++){
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }

        return dp[m-1][n-1];
    }

    public int uniquePaths(int m, int n) {
        if(m == 0 || n == 0){
            return 0;
        }
        if(m == 1 || n == 1){
            return 1;
        }

        return uniquePaths(n-1,m) + uniquePaths(n,m-1);
    }

    public int[][] generateMatrix(int n) {
        if(n < 1){
            return new int[n][n];
        }
        int rowBegin = 0,rowEnd = n - 1;
        int colBegin = 0,colEnd = n - 1;
        int[][] maxtrix = new int[n][n];
        int num = 1;
        while(rowBegin <= rowEnd && colBegin <= colEnd){
            for(int i=colBegin;i <= colEnd;i++){
                maxtrix[rowBegin][i] = num++;
            }
            rowBegin++;
            for(int i=rowBegin;i <= rowEnd;i++){
                maxtrix[i][colEnd] = num++;
            }
            colEnd--;
            if(rowEnd >= rowBegin){
                for(int i=colEnd;i >= colBegin;i--){
                    maxtrix[rowEnd][i] = num++;
                }
            }
            rowEnd--;
            if(colEnd >= colBegin){
                for(int i=rowEnd;i >= rowBegin;i--){
                    maxtrix[i][colBegin] = num++;
                }
            }
            colBegin++;
        }

        return maxtrix;
    }

    /**
     * Input:
     * [
     * [ 1, 2, 3 ],
     * [ 4, 5, 6 ],
     * [ 7, 8, 9 ]
     * ]
     Output: [1,2,3,6,9,8,7,4,5]
     * @param matrix
     * @return
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> orders = new ArrayList<>();
        if(matrix.length < 1 || matrix[0].length < 1){
            return orders;
        }
        int rowBegin = 0,rowEnd = matrix.length - 1;
        int colBegin = 0,colEnd = matrix[0].length - 1;
        while(rowBegin <= rowEnd && colBegin <= colEnd){
            for(int i=colBegin;i <= colEnd;i++){
                orders.add(matrix[rowBegin][i]);
            }
            rowBegin++;
            for(int i=rowBegin;i <= rowEnd;i++){
                orders.add(matrix[i][colEnd]);
            }
            colEnd--;
            if(rowEnd - rowBegin >= 0){
                for(int i=colEnd;i >= colBegin;i--){
                    orders.add(matrix[rowEnd][i]);
                }
            }
            rowEnd--;
            if(colEnd - colBegin >= 0){
                for(int i=rowEnd;i >= rowBegin;i--){
                    orders.add(matrix[i][colBegin]);
                }
            }
            colBegin++;
        }

        return orders;
    }

    public List<Integer> spiralOrder2(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if(matrix.length < 1 || matrix[0].length < 1){
            return result;
        }
        printTopRight(result,matrix,0,0,matrix.length - 1);
        return result;
    }

    private void printTopRight(List<Integer> orders,int[][] matrix,int x1,int y1,int y2){
        int x2 = matrix[y1].length - x1 - 1;
        for(int i=x1;i <= x2;i++){
            orders.add(matrix[y1][i]);
        }
        for(int j=y1+1;j <= y2;j++){
            orders.add(matrix[j][matrix[j].length - x1 - 1]);
        }
        if( matrix[y2].length - 2*x1 > 1){
            printBottomLeft(orders,matrix,x1,y1+1,y2);
        }
    }


    private void printBottomLeft(List<Integer> orders,int[][] matrix,int x1,int y1,int y2){
        int x2 = matrix[y1].length - x1 - 2;
        for(int i=matrix[y2].length - x1 - 2;i >= x1;i--){
            orders.add(matrix[y2][i]);
        }
        for(int j=y2-1;j >= y1;j--){
            orders.add(matrix[j][x1]);
        }
        if(x2 - x1 > 1){
            printTopRight(orders,matrix,x1+1,y1,y2-1);
        }
    }
    /**
     * LeetCode 25 Reverse Nodes in k-Group
     * Example:
     * Given this linked list: 1->2->3->4->5
     * For k = 2, you should return: 2->1->4->3->5
     * For k = 3, you should return: 3->2->1->4->5
     * @param head
     * @param k
     * @return
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        if(k < 2 || head == null){
            return head;
        }
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode pre = dummy;
        int index = 0;
        while(head != null) {
            index++;
            if(index%k == 0) {
                pre = reverseExclusive(pre,head.next);
                head = pre.next;
            }else {
                head = head.next;
            }
        }

        return dummy.next;
    }

    /**
     *
     * @param pre exclusively
     * @param tail exclusively
     * @return
     */
    public ListNode reverseExclusive(ListNode pre,ListNode tail){
        ListNode last = pre.next;
        ListNode cur = last.next;
        while(cur != tail){
            //保持链条指向下一次处理node
            last.next = cur.next;
            //每次cur放到pre后面，即可完成倒序
            cur.next = pre.next;
            pre.next = cur;
            cur = last.next;
        }

        return last;
    }

    public ListNode rotateRight(ListNode head, int k) {
     ListNode cur = head;
     int len = 0;
     while (cur != null){
         len++;
         cur = cur.next;
     }

     if(head == null || head.next == null || k % len == 0){
         return head;
     }

     ListNode dummy = new ListNode(-1);
     dummy.next = head;

     return doRotate(dummy,k%len);
    }

    private ListNode doRotate(ListNode dummy,int k){
        if(k == 0){
            return dummy.next;
        }
        ListNode cur = dummy.next;
        while (cur.next != null && cur.next.next != null){
            cur = cur.next;
        }

        cur.next.next = dummy.next;
        dummy.next = cur.next;
        cur.next = null;

        return doRotate(dummy,k-1);
    }

}
