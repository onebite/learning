package com.practice.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Lempel–Ziv–Welch (LZW) is a universal lossless data compression algorithm
 * step 1: Initialize the dictionary to contain all strings of length one.
 * step 2: Find the longest string W in the dictionary that matches the current input.
 * step 3: Emit the dictionary index for W to output and remove W from the input.
 * step 4: Add W followed by the next symbol in the input to the dictionary.
 * step 5: Go to Step 2.
 */
public class LZW {
    /**
     * Compress a string to a list of output symbols
     * @param raw
     * @return
     */
    public static List<Integer> compress(String raw){
        int dictSize = 256;
        Map<String,Integer> dict = new HashMap<>();
        for(int i = 0;i < 256;i++){
            dict.put(""+(char)i,i);
        }

        String w = "";
        List<Integer> result = new ArrayList<>();
        for(char ch :raw.toCharArray()){
            String wc = w + ch;
            if(dict.containsKey(wc)){
                w = wc;
            }else {
                result.add(dict.get(w));
                dict.put(wc,dictSize++);
                w = "" + ch;
            }
        }

        if(!w.equals("")){
            result.add(dict.get(w));
        }

        return result;
    }


    public static String decompress(List<Integer> compressed){
        int dictSize = 256;
        Map<Integer,String> dict = new HashMap<>();
        for(int i = 0;i < 256;i++){
            dict.put(i,""+(char)i);
        }

        String w = "" + (char)(int)compressed.remove(0);
        StringBuffer result = new StringBuffer(w);
        String entry;
        for(int k :compressed){
            if(dict.containsKey(k)){
                entry = dict.get(k);
            }else if(k == dictSize){
                entry = w + w.charAt(0);
            }else{
                throw  new IllegalArgumentException("Bad compressed k:" + k);
            }
            result.append(entry);
            dict.put(dictSize++,w + entry.charAt(0));
            w = entry;
        }

        return result.toString();
    }

    public static void main(String[] args) {
        List<Integer> compressed = compress("TOBEORNOTTOBEORTOBEORNOT");
        System.out.println(compressed);
        String decompressed = decompress(compressed);
        System.out.println(decompressed);
    }
}
