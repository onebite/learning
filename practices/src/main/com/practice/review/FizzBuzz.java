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


    private String say2(int point){
        if(isFizz(point)){
            if(isBuzz(point)){
                return "FizzBuzz";
            }

            return "Fizz";
        }else if(isBuzz(point)){
            return "Buzz";
        }

        return String.valueOf(point);
    }


    private boolean isFizz(int point){

        return doCheck(point,3);
    }

    private boolean isBuzz(int point){

        return doCheck(point,5);
    }


    private boolean doCheck(int point,int factor){
        if(point%factor == 0){
            return true;
        }

        return String.valueOf(point).contains(String.valueOf(factor));
    }

    /**
     * string.contain slow than this
     * @param point
     * @param factor
     * @return
     */
    private boolean docheck2(int point,int factor){
        if(point%factor == 0){
            return true;
        }
        int remainder = point;
        while(true){
            if(remainder == factor){
                return true;
            }

            if((remainder = remainder/10) == 0){
                break;
            }
        }

        return false;
    }
}
