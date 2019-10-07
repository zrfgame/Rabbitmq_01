package com.direct;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.utils.MQConnectionUtils;

public class ConsumerSmslFanout {
	private static final String SMS_QUEUE = "sms_routing_fanout";
	//交换机名称
	private static final String DESTINATION_NAME="my_routing_destination";
	public static void main(String[] args) throws IOException, TimeoutException {
		//1.建立mq连接
		Connection connection = MQConnectionUtils.newConnection();
		//2.创建通道
		Channel channel = connection.createChannel();
		//消费者声明队列
		channel.queueDeclare(SMS_QUEUE, false, false, false, null);
		//4.消费者队列绑定交换机
		channel.queueBind(SMS_QUEUE, DESTINATION_NAME, "sms");
		// 5.消费者监听消息
		DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String msg = new String(body,"UTF-8");
				System.out.println("短信发送的消息:"+msg);
			}
		};
		//设置自动签收
		channel.basicConsume(SMS_QUEUE,true, defaultConsumer);
	}

}
