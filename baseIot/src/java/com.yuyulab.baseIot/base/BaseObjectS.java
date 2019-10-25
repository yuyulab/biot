package com.yuyulab.baseIot.base;


import lombok.Getter;
import lombok.Setter;

/**
 * @author : guiheng.wang
 * @className : BaseObjectS
 * @date : 2019/10/25 9:44
 */

@Setter
@Getter
public class BaseObjectS {

    private int versionNum;
    private String version;
    private String classPath;
    private int encode;
    private Object o;
    private Long time;
    private String timeS;
    private Integer hashCode;

}
