package com.fanout;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.utils.MQConnectionUtils;

//������  ����������  producerFanout����
public class FanoutProducer {
	//����������
	private static final String DESTINATION_NAME="my_fanout_destination";
	public static void main(String[] args) throws IOException, TimeoutException {
		//1.����mq����
		Connection connection = MQConnectionUtils.newConnection();
		//2.����ͨ��
		Channel channel = connection.createChannel();
		//3.�����߰󶨽����� ����1 ���������� ����2����������
		channel.exchangeDeclare(DESTINATION_NAME,"fanout");
		//4.������Ӧ����Ϣ
		String msg = "my_fanout_destination_msg";
		// 5. ������Ϣ
		channel.basicPublish(DESTINATION_NAME, "", null, msg.getBytes());
		System.out.println("������Ϣ�ɹ���"+msg);
		// 6.�ر�ͨ��������
		channel.close();
		connection.close();
	}
}
