
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class Messaging {

    // public final static int id = 0;
    public final String QUEUE_SEND = "A";
    public final String QUEUE_RECIEVE = "B";

    Connection connection;
    Channel channel;
    QueueingConsumer consumer;
    QueueingConsumer.Delivery delivery;

    public void setConnection() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        //factory.setUsername("guest");
        //factory.setPassword("guest");
        //factory.setVirtualHost("/");
        //factory.setPort(5672);
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_SEND, false, false, false, null);
        consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_RECIEVE, true, consumer);
    }

    public void closeConnection() throws Exception {
        channel.close();
        connection.close();
    }

    public void sendShotMessage(int coord1, int coord2) throws Exception {
        String chars = "ABCDEFGHIJ";
        String message = coord1 + String.valueOf(chars.charAt(coord2));

        channel.basicPublish("", QUEUE_SEND, null, message.getBytes());
        System.out.println("Sent shot message with coodinates " + message
                + " in queue " + QUEUE_SEND);
    }

    public void sendStatusMessage(String status) throws Exception {
        channel.basicPublish("", QUEUE_SEND, null, status.getBytes());
        System.out.println("Sent status message with status " + status
                + " in queue " + QUEUE_SEND);
    }

    public int[] recieveShotMessage() throws Exception {
        System.out.println("Waiting for messages" + " in queue "
                + QUEUE_RECIEVE);

        while (true) {
            delivery = consumer.nextDelivery();

            String message = new String(delivery.getBody());
            char secondChar = message.charAt(1);

            int coordsArray[] = new int[2];

            if (secondChar == 'A') {
                coordsArray[1] = 0;
            }
            if (secondChar == 'B') {
                coordsArray[1] = 1;
            }
            if (secondChar == 'C') {
                coordsArray[1] = 2;
            }
            if (secondChar == 'D') {
                coordsArray[1] = 3;
            }
            if (secondChar == 'E') {
                coordsArray[1] = 4;
            }
            if (secondChar == 'F') {
                coordsArray[1] = 5;
            }
            if (secondChar == 'G') {
                coordsArray[1] = 6;
            }
            if (secondChar == 'H') {
                coordsArray[1] = 7;
            }
            if (secondChar == 'I') {
                coordsArray[1] = 8;
            }
            if (secondChar == 'J') {
                coordsArray[1] = 9;
            }

            coordsArray[0] = Integer
                    .parseInt(String.valueOf(message.charAt(0)));

            System.out.println("Received shot message with coordinates "
                    + message + " in queue " + QUEUE_RECIEVE);
            return coordsArray;
        }
    }

    public String recieveStatusMessage() throws Exception {
        System.out.println("Waiting for messages" + " in queue "
                + QUEUE_RECIEVE);

        while (true) {
            delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("Received status message with coordinates "
                    + message + " in queue " + QUEUE_RECIEVE);
            return message;
        }
    }

}
