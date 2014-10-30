import java.awt.AWTException;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class WorkerRunnable implements Runnable {

	protected Client client = null;

	public WorkerRunnable(Client client) {
		this.client = client;
	}

	public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            out.write(">ACCEPTED");
            out.flush();

            //DataInputStream is = new DataInputStream(client.getInputStream());

            try {
            	SystemInputExecutor key = SystemInputExecutor.getInstance();
                boolean done = false;
                while (!done) {
                	String message = (String) in.readLine();
                	System.out.println(message);

                	if (">NEXT".equalsIgnoreCase(message)) {
                		key.right();
                	} else if (">PREV".equalsIgnoreCase(message)) {
                		key.left();
                	}

//                	String message = null;
//                	byte[] bytes = new byte[1024];
//                    int r;
//
//                    while ((r = is.read(bytes)) > 0) {
//                        message = new String(bytes, 0, r);
//                        System.out.println(message);
//
//                        if (">NEXT".equalsIgnoreCase(message)) {
//                        	key.right();
//                    	} else if (">PREV".equalsIgnoreCase(message)) {
//                    		key.left();
//                    	}
//
//                    }
                	done = ".quit".equals(message);
                }
            } catch (AWTException e) {
            	e.printStackTrace();
            }

            client.disconnect();
        } catch (IOException e) {
        	try {
        		client.disconnect();
        	} catch (IOException ex) {
        		System.out.println("Cannot disconnect client. " + ex.getMessage());
        	}
        }
    }
}
