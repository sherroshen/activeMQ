package com.imooc.jms.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class AppConsumer {
	private static final String url = "tcp://192.168.1.4:61616";
	private static final String queueName = "queue-test";
	
	public static void main(String[] args) throws JMSException {
		//1.����Connection Factory
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		
		//2.����Connections
		Connection connection = connectionFactory.createConnection();
		
		//3.��������
		connection.start();
		
		//4.�����Ự
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//5.����һ��Ŀ��
		Destination destination = session.createQueue(queueName);
		
		/**
		//6.����һ��������
		MessageProducer producer = session.createProducer(destination);
		for(int i = 0 ; i < 100; i++){
			//7.������Ϣ
			TextMessage textMessage = session.createTextMessage("text" + i);
			//8.������Ϣ
			producer.send(textMessage);
			System.out.println("������Ϣ" + textMessage.getText());
		}
		*/
		//6.����������
		MessageConsumer consumer = session.createConsumer(destination);
		//7.����������
		consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				try {
					System.out.println("������Ϣ" + textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		
		//9.�ر�����
		//connection.close();
	}
}