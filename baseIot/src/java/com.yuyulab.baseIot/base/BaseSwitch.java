package com.yuyulab.baseIot.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author : guiheng.wang
 * @className : Switch
 * @date : 2019/10/25 10:13
 */

@Getter
@Setter
@ToString
public class BaseSwitch {

    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private Long code;
    /**
     * 时间
     */
    private Long time;
    /**
     * 延时
     */
    private Integer timeLong;
    /**
     * 备注
     */
    private String remork;
}
