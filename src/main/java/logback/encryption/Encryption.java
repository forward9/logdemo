/**
 * 
 */
package logback.encryption;

import ch.qos.logback.classic.LogbackFilter;


public class Encryption {
    public static String getEncString(String filed, String content, String type, String symbol) {

        if (type.equalsIgnoreCase("01")) {
            return filed + ":" + LogbackFilter.tuomin(content);
        }

        if (type.equalsIgnoreCase("02")) {
            return symbol + filed + ":" + AES.getEncString(content) + symbol;
        }

        return null;
    }
}
