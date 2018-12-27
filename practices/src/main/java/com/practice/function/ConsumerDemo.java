package com.practice.function;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author lxl
 * @Date 2018/4/18 10:45
 */
public class ConsumerDemo {
    public static void main(String[] args) throws Exception{
        List<Student> data = new ArrayList<>();
        Random random = new Random();
        for(int i=0;i < 10000;i++){
            Student student = new Student();
            student.setAge(random.nextInt(30));
            student.setName(i+"_"+ random.nextInt(30));
            student.setGender(Gender.male);
            student.setScore((long)random.nextInt(100));
            if(random.nextBoolean()){
                student.setGender(Gender.female);
            }
            data.add(student);
        }

        data.forEach(student -> plusScore(student, new Predicate<Student>() {
            @Override
            public boolean test(Student student) {

                return student != null && student.gender == Gender.female ? true : false;
            }
        }, new Consumer<Student>() {
            @Override
            public void accept(Student student) {
                student.setScore(student.getScore() + 100);
            }
        }));


        data.forEach(student -> {if(student.gender == Gender.female){
         System.out.println(student.toString());
        }
        });
    }

    public static void plusGirlsScore(Student student, Consumer<Student> consumer){
        if(student != null && student.getGender() == Gender.female){
            consumer.accept(student);
        }
    }

    public static void plusScore(Student student,Predicate<Student> predicate,Consumer<Student> consumer){
        if(predicate.test(student)){
            consumer.accept(student);
        }
    }

    public static class Student{
        private String name;
        private Integer age;
        private Gender gender;
        private Long score;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Gender getGender() {
            return gender;
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Long getScore() {
            return score;
        }

        public void setScore(Long score) {
            this.score = score;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", gender=" + gender +
                    ", score=" + score +
                    '}';
        }
    }

    public enum Gender{
        female,
        male;
    }
}
