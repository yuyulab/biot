package com.yuyulab.baseIot.base;

/**
 * @author : guiheng.wang
 * @className : BaseUtil
 * @date : 2019/10/25 13:48
 */
public class BaseUtil {

    private static String env(String key, String defaultValue) {
        String rc = System.getenv(key);
        if (rc == null)
            return defaultValue;
        return rc;
    }

    private static String arg(String[] args, int index, String defaultValue) {
        if (index < args.length)
            return args[index];
        else
            return defaultValue;
    }
}
