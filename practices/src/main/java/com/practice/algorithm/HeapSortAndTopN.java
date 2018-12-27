package com.practice.algorithm;

/**
 * @author lxl
 * @Date 2018/10/20 16:47
 */
public class HeapSortAndTopN {
    public static int[] topN(int[] array,int n){
        if(n >= array.length) {
            return array;
        }

        int[] topn = new int[n];
        for(int i=0;i < n;i++){
            topn[i] = array[i];
        }

       return topn;
    }
}
