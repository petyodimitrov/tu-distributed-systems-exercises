package bg.tusofia.cst.ds.amqp;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class Receiver {

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection(); // not closed on purpose
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Constants.EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        String queueName = channel.queueDeclare().getQueue(); /* generates a server-named, exclusive, autodelete, non-durable queue */
        channel.queueBind(queueName, Constants.EXCHANGE_NAME, "" /* blank routing key */);
        System.out.println("Queue '" + queueName + "' is bound to exchange '" + Constants.EXCHANGE_NAME + "'.");

        System.out.println("Waiting for message on queue '" + queueName + "'...");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("Received message '" + message + "'");
        };
        channel.basicConsume(queueName, true /* auto ack */, deliverCallback, consumerTag -> {
        });
    }
}