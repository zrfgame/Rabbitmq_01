package com.consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.utils.MQConnectionUtils;

public class ConsumerSmslFanout {
	private static final String SMS_QUEUE = "sms_queue_fanout";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		//1.����mq����
		Connection connection = MQConnectionUtils.newConnection();
		//2.����ͨ��
		Channel channel = connection.createChannel();
		//��������������
		channel.queueDeclare(SMS_QUEUE, false, false, false, null);
		//4.�����߶��а󶨽�����
		channel.queueBind(SMS_QUEUE, "my_fanout_destination", "");
		// 5.�����߼�����Ϣ
		DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String msg = new String(body,"UTF-8");
				System.out.println("���ŷ��͵���Ϣ:"+msg);
			}
		};
		//�����Զ�ǩ��
		channel.basicConsume(SMS_QUEUE,true, defaultConsumer);
	}

}