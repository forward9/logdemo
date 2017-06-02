package ch.qos.logback.classic;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogbackFilter {
    private static final Logger logger = LoggerFactory.getLogger(LogbackFilter.class);

    private static String LOG_FILTER_SWITCH = "false";

    private static String LOG_FILTER_KEYS = null;
    private static Pattern pattern;
    private static String[] keyArr;

    static {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("logback_filter");
            LOG_FILTER_SWITCH = bundle.getString("logback.filter.switch");
            LOG_FILTER_KEYS = bundle.getString("logback.filter.keys");
            keyArr = LOG_FILTER_KEYS.split(",");
        } catch (Exception e) {
            logger.error("", e);
        }

        pattern = Pattern.compile("[0-9a-zA-Z]");
    }


    public static String invokeMsg(String msg) {
        if ("true".equals(LOG_FILTER_SWITCH)) {
            if ((LOG_FILTER_KEYS != null) && (LOG_FILTER_KEYS.length() > 0)) {
                for (String key : keyArr) {
                    int index = -1;
                    do {
                        index = msg.indexOf(key, index + 1);
                        if (index == -1)
                            continue;
                        if (isWordChar(msg, key, index)) {
                            continue;
                        }

                        int valueStart = getValueStartIndex(msg, index + key.length());

                        int valueEnd = getValuEndEIndex(msg, valueStart);

                        String subStr = msg.substring(valueStart, valueEnd);
                        subStr = tuomin(subStr);

                        msg = msg.substring(0, valueStart) + subStr + msg.substring(valueEnd);
                    } while (index != -1);
                }
            }

        }

        return msg;
    }


    private static boolean isWordChar(String msg, String key, int index) {
        if (index != 0) {
            char preCh = msg.charAt(index - 1);
            Matcher match = pattern.matcher(String.valueOf(preCh));
            if (match.matches()) {
                return true;
            }
        }

        char nextCh = msg.charAt(index + key.length());
        Matcher match = pattern.matcher(String.valueOf(nextCh));

        return match.matches();
    }


    private static int getValueStartIndex(String msg, int valueStart) {
        while (true) {
            char ch = msg.charAt(valueStart);
            if ((ch == ':') || (ch == '=')) {
                valueStart++;
                ch = msg.charAt(valueStart);
                if (ch != '"')
                    break;
                valueStart++;

                break;
            }
            valueStart++;
        }

        return valueStart;
    }


    private static int getValuEndEIndex(String msg, int valueEnd) {
        while (valueEnd != msg.length()) {
            char ch = msg.charAt(valueEnd);

            if (ch == '"') {
                if (valueEnd + 1 == msg.length()) {
                    break;
                }
                char nextCh = msg.charAt(valueEnd + 1);
                if (nextCh != ';') {
                    if (nextCh != ',')
                        ;
                } else {
                    while (valueEnd > 0) {
                        char preCh = msg.charAt(valueEnd - 1);
                        if (preCh != '\\') {
                            break;
                        }
                        valueEnd--;
                    }
                    break;
                }
                valueEnd++;

                continue;
            }
            if ((ch == ';') || (ch == ',')) {
                break;
            }
            valueEnd++;
        }

        return valueEnd;
    }


    public static String tuomin(String submsg) {
        StringBuffer sbResult = new StringBuffer();
        if ((submsg != null) && (submsg.length() > 0)) {
            int len = submsg.length();
            if (len > 8) {
                for (int i = len - 1; i >= 0; i--) {
                    if ((len - i < 5) || (len - i > 8))
                        sbResult.insert(0, submsg.charAt(i));
                    else
                        sbResult.insert(0, '*');
                }
            } else {
                for (int i = 0; i < len; i++) {
                    sbResult.append('*');
                }
            }
        }
        return sbResult.toString();
    }


    public static void main(String[] args) {
        String msg = "\\\"account_num\\\":\\\"6230958600001008\\\",\\\"amount\\\":\\\"\\\"";
        System.out.println(invokeMsg(msg));

        System.out.println(invokeMsg("password:你好这是一条有味道的程序,测试一下。account_num=好像还可以,切记不能用中文符号。"));
    }
}