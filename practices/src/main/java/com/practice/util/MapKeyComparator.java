package com.practice.util;

import java.util.Comparator;

/**
 * @author lxl
 * @Date 2018/5/24 10:59
 */
public class MapKeyComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {

        return o1.compareTo(o2);
    }
}
