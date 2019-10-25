package com.yuyulab.baseIot.mq;

import org.junit.Test;



public class MqListenerTest {
    @Test
    public void start() throws Exception {

        MqListener mqListener = new MqListener(null,null,null,null,"start_open_close");
        mqListener.start();

    }


}
