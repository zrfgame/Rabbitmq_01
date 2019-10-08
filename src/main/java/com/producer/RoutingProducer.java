package com.producer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.utils.MQConnectionUtils;

//生产者  交换机类型  producerFanout类型
public class RoutingProducer {
	//交换机名称
	private static final String DESTINATION_NAME="my_routing_destination";
	public static void main(String[] args) throws IOException, TimeoutException {
		//1.建立mq连接
		Connection connection = MQConnectionUtils.newConnection();
		//2.创建通道
		Channel channel = connection.createChannel();
		//3.生产者绑定交换机 参数1 交换机名称 参数2交换机类型
		channel.exchangeDeclare(DESTINATION_NAME,"direct");
		//4.创建对应的消息
		String msg = "my_fanout_destination_msg";
		// 5. 发送消息 参数1 交换机名称  参数2是 routingkey 
		channel.basicPublish(DESTINATION_NAME, "email", null, msg.getBytes());
		System.out.println("发送消息成功："+msg);
		// 6.关闭通道，连接
		channel.close();
		connection.close();
	}
}
