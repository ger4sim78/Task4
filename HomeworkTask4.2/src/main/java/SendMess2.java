import ru.pflb.mq.dummy.exception.DummyException;
import ru.pflb.mq.dummy.implementation.ConnectionImpl;
import ru.pflb.mq.dummy.interfaces.Connection;
import ru.pflb.mq.dummy.interfaces.Destination;
import ru.pflb.mq.dummy.interfaces.Producer;
import ru.pflb.mq.dummy.interfaces.Session;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class SendMess2 {
    public static void main(String[] args) throws DummyException, InterruptedException, IOException {
        String file = args[0];
        Queue<String> data = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while (reader.ready()) {
            data.add(reader.readLine());
        }
        Connection connection = new ConnectionImpl();
        connection.start();

        Session session = connection.createSession(true);

        Destination destination = session.createDestination(data.peek());

        Producer producer = session.createProducer(destination);

        for (String s : data) {
            producer.send(s);
            TimeUnit.SECONDS.sleep(2);
        }


        session.close();
        connection.close();


    }
}
