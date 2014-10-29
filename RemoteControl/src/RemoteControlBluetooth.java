import java.io.*;
import javax.bluetooth.*;
import javax.microedition.io.*;

public class RemoteControlBluetooth implements Runnable {

    private final UUID uuid = new UUID(0x1101); //it can be generated randomly
    private final String name = "Echo Server";                       //the name of the service
    private final String url  =  "btspp://localhost:" + uuid         //the service url
                                + ";name=" + name
                                + ";authenticate=false;encrypt=false;";
    protected LocalDevice local;
    protected StreamConnectionNotifier server;
    protected StreamConnection conn;
    protected Client client;

    public RemoteControlBluetooth(Client client) {
    	this.client = client;
    }

    public void run() {
        try {
            System.out.println("Setting device to be discoverable...");
            local = LocalDevice.getLocalDevice();
            local.setDiscoverable(DiscoveryAgent.GIAC);
            System.out.println("Start advertising service...");
            server = (StreamConnectionNotifier)Connector.open(url);
            System.out.println("Waiting for incoming connection...");

            while(true) {
            	conn = server.acceptAndOpen();
                System.out.println("Client Connected...");

                if (!this.client.isConnected()) {
                	this.client.bind(conn);
                    new Thread(new WorkerRunnable(this.client)).start();
                } else {
                	System.out.println("Client connection has been denied, other device already connected.");
                	conn.close();
                }
            }
        } catch (Exception  e) {
        	System.out.println("Exception Occured: " + e.toString());
        }
    }
}
