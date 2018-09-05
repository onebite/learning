package com.practice.concurrent.taobao;

import java.io.Writer;

/**
 * @author lxl
 * @Date 2018/9/5 10:17
 */
public interface StressResultFormater {
    void format(StressResult stressResult, Writer writer);
}
