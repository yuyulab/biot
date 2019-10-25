package com.yuyulab.baseIot.mq;

import com.yuyulab.baseIot.base.BaseSwitch;

//@RunWith(Arquillian.class)
public class MqSendTest {
    @org.junit.Test
    public void send() throws Exception {
        MqSend mqSend = new MqSend(null,null,null,null,"start_open_close");


        BaseSwitch openSwitch = new BaseSwitch();
        openSwitch.setCode(11L);
        openSwitch.setName("LED");
        openSwitch.setTime(System.currentTimeMillis());
        openSwitch.setTimeLong(0);
        openSwitch.setRemork("基础开关测试");

        for (int i =1;i<500;i++){
            openSwitch.setName("LED"+i);
            mqSend.send(openSwitch);
            Thread.sleep(800);
        }

    }

//    @Deployment
//    public static JavaArchive createDeployment() {
//        return ShrinkWrap.create(JavaArchive.class)
//                .addClass(MqSend.class)
//                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
//    }

}
