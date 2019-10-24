package com.yuyulab.baseIot.mq;

import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.*;

import java.net.URISyntaxException;

/**
 * @author : guiheng.wang
 * @className : MqListener
 * @date : 2019/10/22 17:29
 */
public class MqListener {


//    ArrayList<Topic> destinationList = new ArrayList<Topic>(32);


    private String user = "admin";
    private String password = "password";
    private String host = "localhost";
    private int port = 1883;
    private MQTT mqtt = new MQTT();
    private long count = 0;
    private String destination;
    private CallbackConnection connection = mqtt.callbackConnection();

    MqListener(String user, String password, String host, Integer port,String destination) throws InterruptedException {

        if(user!=null){
            this.user = user;
        }
        if(password!=null){
            this.password = password;
        }
        if(host!=null){
            this.host = host;
        }
        if(port!=null){
            this.port = port;
        }
        if(destination==null){
            System.out.println("could not destination is null");
            System.exit(1);
        }
        this.destination = destination;

        try {
            mqtt.setUserName(this.user);
            mqtt.setPassword(this.password);
            mqtt.setHost(this.host, this.port);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    public void start() {
        connection.listener(new org.fusesource.mqtt.client.Listener() {

            @Override
            public void onConnected() {
                System.out.println("连接成功");
            }

            @Override
            public void onDisconnected() {
                System.out.println("连接失败");
            }

            @Override
            public void onFailure(Throwable value) {
                value.printStackTrace();
                System.exit(-2);
            }

            public void onPublish(UTF8Buffer topic, Buffer msg, Runnable ack) {
                String body = msg.utf8().toString();
                System.out.println(body);
                ack.run();
            }
        });

        final Topic topic = new Topic(this.destination, QoS.AT_LEAST_ONCE);
        connection.connect(new Callback<Void>() {
            @Override
            public void onSuccess(Void value) {
                Topic[] topics = {topic};

                connection.subscribe(topics, new Callback<byte[]>() {
                    @Override
                    public void onSuccess(byte[] qoses) {
                        System.out.println("订阅成功");
                    }

                    @Override
                    public void onFailure(Throwable value) {
                        value.printStackTrace();
                        System.exit(-2);
                    }
                });
            }

            @Override
            public void onFailure(Throwable value) {
                value.printStackTrace();
                System.exit(-2);
            }
        });
        synchronized (Listener.class) {
            while(true)
                try {
                    Listener.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }

    public static void main(String[] args) throws Exception {
        MqListener mqListener = new MqListener(null,null,null,null,"start_open_close");
        mqListener.start();
    }

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
