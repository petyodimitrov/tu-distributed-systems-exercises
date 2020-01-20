package bg.tusofia.cst.ds.jms;

import org.apache.qpid.jms.JmsConnectionFactory;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Message producer via AMQP 1.0.
 */
public class AmqpProducer {

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new JmsConnectionFactory("amqp://localhost:5672");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false /* no transaction */, Session.AUTO_ACKNOWLEDGE);
        Queue destination = session.createQueue(Constants.QUEUE);
        MessageProducer producer = session.createProducer(destination);

        String text = args.length == 0 ? "opa opa" : args[0];
        TextMessage message = session.createTextMessage(text);

        System.out.println("Sending message to queue '" + Constants.QUEUE + "'...");
        producer.send(message);
        System.out.println("Sent message.");

        connection.close();
    }

}