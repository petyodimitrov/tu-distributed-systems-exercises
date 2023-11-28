package bg.tusofia.cst.ds.amqp;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class Sender {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        String text = args.length == 0 ? "opa opa " : args[0];

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(Constants.EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

            for (int i = 0; i < 10; i++) {
                System.out.println("Sending message " + i + " to exchange '" + Constants.EXCHANGE_NAME + "'...");
                String message = text + i;
                channel.basicPublish(Constants.EXCHANGE_NAME, "" /* blank routing key */, null /* no properties */, message.getBytes(StandardCharsets.UTF_8));
                System.out.println("Sent message.");
            }
        }
    }
}