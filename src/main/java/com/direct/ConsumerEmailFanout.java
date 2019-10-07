package com.direct;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.utils.MQConnectionUtils;

public class ConsumerEmailFanout {
	private static final String EAMIL_QUEUE = "eamil_routing_fanout";
	//����������
	private static final String DESTINATION_NAME="my_routing_destination";
	public static void main(String[] args) throws IOException, TimeoutException {
		//1.����mq����
		Connection connection = MQConnectionUtils.newConnection();
		//2.����ͨ��
		Channel channel = connection.createChannel();
		//��������������
		channel.queueDeclare(EAMIL_QUEUE, false, false, false, null);
		//4.�����߶��а󶨽�����
		channel.queueBind(EAMIL_QUEUE, DESTINATION_NAME, "email");
		// 5.�����߼�����Ϣ
		DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String msg = new String(body,"UTF-8");
				System.out.println("�ʼ������߻�ȡ������Ϣ:"+msg);
			}
		};
		channel.basicConsume(EAMIL_QUEUE,true, defaultConsumer);
	}

}
