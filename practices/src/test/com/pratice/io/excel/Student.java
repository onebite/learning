package com.pratice.io.excel;

import com.practice.io.excel.ExcelCell;

import java.util.List;

/**
 * @author lxl
 * @Date 2018/6/19 20:05
 */
public class Student {
    @ExcelCell(head = "name")
    String name;
    @ExcelCell(head = "grade")
    String grade;
    @ExcelCell(head = "gender")
    Gender gender;
    List<Course> courses;

    public enum Gender {MALE,FEMALE}
}
