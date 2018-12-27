package com.practice.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * @author lxl
 * @Date 2018/11/12 10:37
 */
public class KeyUtilsTest {
    private Logger log = LoggerFactory.getLogger(KeyUtilsTest.class);

    @Test
    public void hash() throws Exception {
    }

    @Test
    public void parseInt() throws Exception {
        log.info("result : {}",KeyUtils.parseInt(new byte[] {'a','f','9','e'}));
    }

    @Test
    public void randomStr() throws Exception {
    }

    @Test
    public void randomStr1() throws Exception {
    }

    @Test
    public void randomStr2() throws Exception {
    }

}