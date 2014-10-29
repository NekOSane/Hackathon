import java.awt.AWTException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class WorkerRunnable implements Runnable {

	protected Client client = null;

	public WorkerRunnable(Client client) {
		this.client = client;
	}

	public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            try {
            	SystemInputExecutor key = SystemInputExecutor.getInstance();
                boolean done = false;
                while (!done) {
                	String message = (String) in.readLine();
                	System.out.println(message);

                	if (">NEXT".equalsIgnoreCase(message)) {
                		key.left();
                	} else if (">PREV".equalsIgnoreCase(message)) {
                		key.right();
                	}

                	done = message.equals(".quit");
                }
            } catch (AWTException e) {
            	e.printStackTrace();
            }

            client.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
