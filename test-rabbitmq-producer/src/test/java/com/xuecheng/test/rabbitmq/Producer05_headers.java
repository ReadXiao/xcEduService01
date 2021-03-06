package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq的入门程序
 *
 * @author 肖宏武
 * @date 2020/9/14 - 14:15
 */
public class Producer05_headers {
    //队列
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_HEADERS_INFORM = "exchange_headers_inform";

    public static void main(String[] args) throws IOException, TimeoutException {
        //通过连接工厂创建新的连接和mq建立连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);//端口
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //设置虚拟机，一个mq服务可以设置多个虚拟机，每个虚拟机就相当与一个独立的mq
        connectionFactory.setVirtualHost("/");
        Connection connection = null;
        Channel channel = null;
        try {
            //新建连接
            connection = connectionFactory.newConnection();
            //创建会话通道，生产者和mq服务所有通信都在channel通道中完成
            channel = connection.createChannel();
            //声明队列,如果队列在mq中没有则要创建
            //参数：String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
            /*
             * 参数明细
             * 1、队列名称
             * 2、是否持久化，如果持久化，mq重启后队列还在
             * 3、是否独占连接，队列指允许在该连接中访问，如果连接关闭队列自动删除，如果将此参数设置true课用于历史队列的创建
             * 4、自动删除，队列不再使用时是否自动删除此队列，如果将此参数和exclusive参数设置为true就可以实现临时队列（队列不用了就自动删除）
             * 5、参数，可以设置一个队列的扩张参数，比如：可设置存活时间
             * */
            channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);
            channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);

            //声明一个交换机
            //参数：String exchange, BuiltinExchangeType type
            /*
             * 1、 交换机名称
             * 2、交换机的类型
             *   fanout：对应的erabbitmq的工作模式publish/subscribe
             *   direct: 对应的Routing工作模式
             *   topic:  对应Topics工作模式
             *   headers:    对应headers工作模式
             * */
            channel.exchangeDeclare(EXCHANGE_HEADERS_INFORM, BuiltinExchangeType.TOPIC);
            //进行交换机和队列进行绑定
            //参数：String queue, String exchange, String routingKey
            /*
             * 参数明细：
             * 1、队列名称
             * 2、exchange 交换机名称
             * 3、routingKey 路由key,作用是交换机根据路由key的值将消息转发到指定的队列中，在发布订阅模式中调协为空字符串
             * */
            Map<String, Object> headers_email = new Hashtable<String, Object>();
            headers_email.put("inform_type", "email");
            Map<String, Object> headers_sms = new Hashtable<String, Object>();
            headers_sms.put("inform_type", "sms");
            channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_HEADERS_INFORM, "", headers_email);
            channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_HEADERS_INFORM, "", headers_sms);


            //发送消息
            //参数：String exchange, String routingKey, BasicProperties props, byte[] body
            /*
             * 参数明细
             * 1、交换机，如果不指定将使用mq的默认交换机,(设置为"")
             * 2、routingKey 路由key,交换机根据路由key来将消息转发到指定队列，如果使用默认交换机，routingKey设置为队列的名称
             * 3、props,消息的属性
             * 4、body,消息内容
             * */
            //消息内容
            /*for (int i = 0; i < 5; i++) {
                //发送消息的时候需要指定routingkey
                String message = "send inform message to user inform.email";
                channel.basicPublish(EXCHANGE_TOPICS_INFORM, "inform.email", null, message.getBytes());
                System.out.println("send to mq" + message);
            }
            for (int i = 0; i < 5; i++) {
                //发送消息的时候需要指定routingkey
                String message = "send inform message to user inform.sms";
                channel.basicPublish(EXCHANGE_TOPICS_INFORM, "inform.sms", null, message.getBytes());
                System.out.println("send to mq" + message);
            }*/
            for (int i = 0; i < 5; i++) {
                //发送消息的时候需要指定routingkey
                String message = "email inform to user" + i;
                Map<String, Object> headers = new Hashtable<String, Object>();
                headers.put("inform_type", "email");//匹配email通知消费者绑定的header
                // headers.put("inform_type", "sms");//匹配sms通知消费者绑定的header
                AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties.Builder();
                properties.headers(headers); //Email通知
                channel.basicPublish(EXCHANGE_HEADERS_INFORM, "", properties.build(), message.getBytes());
                System.out.println("send to mq:" + message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            //关闭连接
            //先关闭通道
            channel.close();
            //再关闭连接
            connection.close();
        }

    }
}
