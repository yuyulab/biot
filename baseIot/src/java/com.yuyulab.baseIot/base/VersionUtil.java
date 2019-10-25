package com.yuyulab.baseIot.base;

/**
 * @author : guiheng.wang
 * @className : VersionUtil
 * @date : 2019/10/25 13:49
 */
public class VersionUtil {

    final public static Integer version = 1*1000000 +1*1000 +1;

    public static String getVersion(){
        int v1 = version/1000000;
        int v2 = (version%1000000)/1000;
        int v3 = version%1000;
        return v1+"."+v2+"."+v3;
    }

}
