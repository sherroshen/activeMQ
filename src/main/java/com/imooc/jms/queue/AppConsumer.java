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
		//1.创建Connection Factory
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		
		//2.创建Connections
		Connection connection = connectionFactory.createConnection();
		
		//3.启动连接
		connection.start();
		
		//4.创建会话
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//5.创建一个目标
		Destination destination = session.createQueue(queueName);
		
		/**
		//6.创建一个生产者
		MessageProducer producer = session.createProducer(destination);
		for(int i = 0 ; i < 100; i++){
			//7.创建消息
			TextMessage textMessage = session.createTextMessage("text" + i);
			//8.发布消息
			producer.send(textMessage);
			System.out.println("发送消息" + textMessage.getText());
		}
		*/
		//6.创建消费者
		MessageConsumer consumer = session.createConsumer(destination);
		//7.创建监听器
		consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				try {
					System.out.println("接收消息" + textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		
		//9.关闭连接
		//connection.close();
	}
}