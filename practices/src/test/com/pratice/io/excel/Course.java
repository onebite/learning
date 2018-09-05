package com.pratice.io.excel;

import com.practice.io.excel.ExcelCell;

/**
 * @author lxl
 * @Date 2018/6/19 20:06
 */
public class Course {
    @ExcelCell(head = "courseName")
    String name;
    @ExcelCell(head = "courseScore")
    int score;
    @ExcelCell(head = "courseGrade")
    Grade grade;

    public enum Grade{A,B,C,D,E}
}
