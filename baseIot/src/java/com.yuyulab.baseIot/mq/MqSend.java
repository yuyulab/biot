package com.yuyulab.baseIot.mq;

/**
 * @author : guiheng.wang
 * @className : MqSend
 * @date : 2019/10/22 17:27
 */


import org.fusesource.hawtbuf.AsciiBuffer;
import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Future;
import org.fusesource.mqtt.client.FutureConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;

import java.util.HashMap;


public class MqSend {


    final private static int messages = 10000;
    final private static int size = 256;

    private String user = "admin";
    private String password = "password";
    private String host = "localhost";
    private Integer port =1883;

    private MQTT mqtt = new MQTT();
    private String destination;

    private final HashMap<String,Future<Void>> queueMap = new HashMap<String, Future<Void>>(32);

    public MqSend(String user, String password, String host, Integer port, String destination){

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
            return;
        }else{
            this.destination = destination;
        }

        try{
            mqtt.setUserName(this.user);
            mqtt.setPassword(this.password);
            mqtt.setHost(this.host, this.port);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }



    public void send(String str){

        Buffer msg = new AsciiBuffer(str);
        FutureConnection connection = mqtt.futureConnection();
        UTF8Buffer top = new UTF8Buffer(destination);
        try {
            connection.connect().await();
            System.out.println(str);
            connection.publish(top, msg, QoS.AT_LEAST_ONCE, false).await();
            connection.disconnect().await();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String env(String key, String defaultValue) {
        String rc = System.getenv(key);
        if( rc== null )
            return defaultValue;
        return rc;
    }

    private static String arg(String []args, int index, String defaultValue) {
        if( index < args.length )
            return args[index];
        else
            return defaultValue;
    }
}
