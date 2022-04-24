import ru.pflb.mq.dummy.exception.DummyException;
import ru.pflb.mq.dummy.implementation.ConnectionImpl;
import ru.pflb.mq.dummy.interfaces.Connection;
import ru.pflb.mq.dummy.interfaces.Destination;
import ru.pflb.mq.dummy.interfaces.Producer;
import ru.pflb.mq.dummy.interfaces.Session;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class SendMess {
    public static void main(String[] args) throws DummyException, InterruptedException {
        Queue<String> data = new LinkedList<>();
        data.add("Четыре");
        data.add("Пять");
        data.add("Шесть");

        Connection connection = new ConnectionImpl();
        connection.start();

        Session session = connection.createSession(true);

        Destination destination = session.createDestination(data.peek());

        Producer producer = session.createProducer(destination);

        for (String s: data) {
            producer.send(s);
            TimeUnit.SECONDS.sleep(2);
        }
        session.close();
        connection.close();

    }
}
