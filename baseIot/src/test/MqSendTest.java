import com.yuyulab.baseIot.mq.MqSend;

//@RunWith(Arquillian.class)
public class MqSendTest {
    @org.junit.Test
    public void send() throws Exception {
        MqSend mqSend = new MqSend(null,null,null,null,"start_open_close");
        for (int i =1;i<500;i++){
            String str = String.format("{start:open%d,key:close}",i);

            mqSend.send(str);
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
