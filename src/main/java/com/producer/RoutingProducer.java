package com.producer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.utils.MQConnectionUtils;

//������  ����������  producerFanout����
public class RoutingProducer {
	//����������
	private static final String DESTINATION_NAME="my_routing_destination";
	public static void main(String[] args) throws IOException, TimeoutException {
		//1.����mq����
		Connection connection = MQConnectionUtils.newConnection();
		//2.����ͨ��
		Channel channel = connection.createChannel();
		//3.�����߰󶨽����� ����1 ���������� ����2����������
		channel.exchangeDeclare(DESTINATION_NAME,"direct");
		//4.������Ӧ����Ϣ
		String msg = "my_fanout_destination_msg";
		// 5. ������Ϣ ����1 ����������  ����2�� routingkey 
		channel.basicPublish(DESTINATION_NAME, "email", null, msg.getBytes());
		System.out.println("������Ϣ�ɹ���"+msg);
		// 6.�ر�ͨ��������
		channel.close();
		connection.close();
	}
}
