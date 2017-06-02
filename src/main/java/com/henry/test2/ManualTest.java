/**
 * 
 */
package com.henry.test2;

import logback.encryption.Encryption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ManualTest {
    private static final Logger logger = LoggerFactory.getLogger(ManualTest.class);


    /**
     * @param args
     */
    public static void main(String[] args) {
        // 脱敏 >8
        String msg = Encryption.getEncString("passwd", "9089121212", "01", "$");
        logger.info(msg);

        // 脱敏 <=8
        msg = Encryption.getEncString("passwd", "90891222", "01", "$");
        logger.info(msg);

        // 本地加密
        msg = Encryption.getEncString("passwd", "9089121212", "02", "$");
        logger.info(msg);

        // 调用接口加密 未实现返回null
        msg = Encryption.getEncString("passwd", "90891222", "03", "$");
        logger.info(msg);
    }

}
