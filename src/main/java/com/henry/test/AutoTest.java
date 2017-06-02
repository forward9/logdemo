package com.henry.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AutoTest {
    private static final Logger logger = LoggerFactory.getLogger(AutoTest.class);


    public static void main(String[] args) {
        logger.info("================msg:{}", "xxxx000000988888");
        logger.info("================msg={}", "xxxx000000988888");
        logger.info("================password:{}", "12345678");
        logger.info("================password={}", "12345678");

        logger.info("================passwordxxxx={}", "12345678");
    }
}
