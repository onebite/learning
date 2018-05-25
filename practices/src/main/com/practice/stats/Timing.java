package com.practice.stats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lxl
 * @Date 2018/5/11 10:49
 */
public class Timing {
   private final static Logger log = LoggerFactory.getLogger(Timing.class);
   private String name ;
   private long startTime = 0;
   private long endTime = 0;
   private long duration = 0;

   public Timing(String name) {
      this.name = name;
   }


   public void start(){
      startTime= System.nanoTime();
   }

   public void end(){
      endTime = System.nanoTime();
      duration = endTime - startTime;

   }

   public String getName() {
      return name;
   }

   public long getStartTime() {
      return startTime;
   }

   public long getEndTime() {
      return endTime;
   }

   public long getDuration() {
      return duration;
   }

   @Override
   public String toString() {
      return "Timing{" +
              "name='" + name + '\'' +
              ", startTime=" + startTime +
              ", endTime=" + endTime +
              ", duration=" + duration +
              '}';
   }
}
