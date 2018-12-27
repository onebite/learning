package com.practice.algorithm.problem;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author lxl
 * @Date 2018/11/12 15:17
 */
public class SolutionTest {
    private final static Logger log = LoggerFactory.getLogger(SolutionTest.class);
    private final  static  Solution solution = new Solution();

    @Test
    public void testUniquePathsWithObstacles() throws Exception{
        int[][] obstacles = new int[][] {
                {0,0,0},
                {0,1,0},
                {0,0,0}
        };
        int i = solution.uniquePathsWithObstacles(obstacles);
        log.info("result : {}",i);
        obstacles = new int[][] {
                {0,1}
        };
        i = solution.uniquePathsWithObstacles(obstacles);
        log.info("result : {}",i);
    }

    @Test
    public void testGenerateMatrix() throws Exception{
        int[][] ints = solution.generateMatrix(5);
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for(int i=0;i < ints.length;i++){
            for(int j=0;j < ints[0].length;j++){
                sb.append(ints[i][j]).append(",");
            }
            sb.append("\n");
        }
        log.info("result : {}",sb.toString());
    }

    @Test
    public void testReverseKGroup() throws Exception {
        ListNode head = new ListNode(1);
        ListNode p = head;
        for(int i=2;i < 60;i++){
            ListNode temp = new ListNode(i);
            p.next = temp;
            p = p.next;
        }
        head = solution.reverseKGroup(head,9);
        StringBuilder sb = new StringBuilder();
        for(;head != null;){
            sb.append("->" + head.val);
            head = head.next;
        }
        log.info("revverse : {}",sb.toString());
    }

    @Test
    public void testSpiralOrder() throws Exception {
        int[][] matrix = new int[][] {
                {1,2,3},
                {4,5,6,13},
                {7,9,10,11,12},
                {71,91,101,111,121},
                {14,15,18,19,20}
        };
        List<Integer> integers = solution.spiralOrder(matrix);
        log.info("result : {}",integers);
    }

}