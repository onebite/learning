package com.practice.review;

public class FizzBuzz {


    public void print(int num){
        for(int point=1;point <= num;point++){
            //when a big num, io is a bottleneck,use buffer
            System.out.println(say(point));
        }
    }

    /**
     * if time-consuming ，use thread task and buffer print
     * @param point
     * @return
     */
    private String say(int point){
        if(point%3 == 0){
            if(point%5 == 0){
                return "FizzBuzz";
            }

            return "Fizz";
        }else{
            if(point%5 == 0){
                return "Buzz";
            }
        }

        return String.valueOf(point);
    }
}
