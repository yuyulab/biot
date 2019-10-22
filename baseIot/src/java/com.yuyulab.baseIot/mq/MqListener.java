package com.yuyulab.baseIot.mq;

import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.*;

import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * @author : guiheng.wang
 * @className : MqListener
 * @date : 2019/10/22 17:29
 */
public class MqListener {


    ArrayList<Topic> destinationList = new ArrayList<Topic>(32);


    static private String user;
    static private String password;
    static private String host;
    static private int port;

    MqListener(String user, String password, String host, int port) throws InterruptedException {

        MqListener.user = user;
        MqListener.password = password;
        MqListener.host = host;
        MqListener.port = port;

        MQTT mqtt = new MQTT();
        mqtt.setUserName(user);
        mqtt.setPassword(password);
        try {
            mqtt.setHost(host, port);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }



        final CallbackConnection connection = mqtt.callbackConnection();
        connection.listener(new org.fusesource.mqtt.client.Listener() {
            long count = 0;
            long start = System.currentTimeMillis();

            public void onConnected() {
            }

            public void onDisconnected() {
            }

            public void onFailure(Throwable value) {
                value.printStackTrace();
                System.exit(-2);
            }

            public void onPublish(UTF8Buffer topic, Buffer msg, Runnable ack) {
                String body = msg.utf8().toString();
                if ("SHUTDOWN".equals(body)) {
                    long diff = System.currentTimeMillis() - start;
                    System.out.println(String.format("Received %d in %.2f seconds", count, (1.0 * diff / 1000.0)));
                    connection.disconnect(new Callback<Void>() {
                        @Override
                        public void onSuccess(Void value) {
                            System.exit(0);
                        }

                        @Override
                        public void onFailure(Throwable value) {
                            value.printStackTrace();
                            System.exit(-2);
                        }
                    });
                } else {
                    if (count == 0) {
                        start = System.currentTimeMillis();
                    }
                    if (count % 1000 == 0) {
                        System.out.println(String.format("Received %d messages.", count));
                    }
                    count++;
                }
                ack.run();
            }
        });
        connection.connect(new Callback<Void>() {
            @Override
            public void onSuccess(Void value) {
                Topic[] topics = {new Topic("/top", QoS.AT_LEAST_ONCE)};

                connection.subscribe(topics, new Callback<byte[]>() {
                    public void onSuccess(byte[] qoses) {
                    }

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

        // Wait forever..
        synchronized (Listener.class) {
            while (true)
                Listener.class.wait();
        }
    }

    public void start() {

    }

    public static void main(String[] args) throws Exception {

        String user = env("ACTIVEMQ_USER", "admin");
        String password = env("ACTIVEMQ_PASSWORD", "password");
        String host = env("ACTIVEMQ_HOST", "localhost");
        int port = Integer.parseInt(env("ACTIVEMQ_PORT", "1883"));
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
