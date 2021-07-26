/****************************************************
 * 创建人：  @author xieguocheng
 * 创建时间: 2021/06/23 13:58
 * 项目名称:  yh-risk-parent
 * 文件名称: KafkaTest
 * 文件描述: KafkaTest
 * 公司名称: 深圳市赢和信息技术有限公司
 *
 * All rights Reserved, Designed By 深圳市赢和信息技术有限公司
 * @Copyright:2016-2019
 *
 ********************************************************/
package com.xo.common.antlr.test;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

/**
 * 包名称： com.xo.common.antlr.test
 * 类名称：KafkaTest
 * 类描述：KafkaTest
 * 创建人： xieguocheng
 * 创建时间：2021/06/23 13:58
 */
public class KafkaTest {

    public static void main(String[] args) {
        Properties props = new Properties();
        //kafka 集群，broker-list
        props.put("bootstrap.servers", "121.43.147.69:9092");
        props.put("acks", "all");
        //重试次数
        props.put("retries", 1);
        //批次大小
        props.put("batch.size", 16384);
        //等待时间
        props.put("linger.ms", 1);
        //RecordAccumulator 缓冲区大小
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 100; i++) {
//            producer.send(new ProducerRecord<>("second", Integer.toString(i), Integer.toString(i)));
            producer.send(new ProducerRecord<>("five",
                    Integer.toString(i), Integer.toString(i)), new Callback() {
                //回调函数，该方法会在 Producer 收到 ack 时调用，为异步调用
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        System.out.println("success->" + metadata.offset());
                    } else {
                        exception.printStackTrace();
                    }
                }
            });
        }
        producer.close();

    }

}
