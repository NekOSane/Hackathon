import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.io.IOException;

import java.awt.AWTException;

public class RemoteControlWifi implements Runnable{

	protected int          	serverPort;
    protected ServerSocket 	serverSocket;
    protected boolean      	isStopped;
    protected Thread       	runningThread;
    protected Client	   	client;

	public RemoteControlWifi(int port, Client client)  {
		this.serverPort = port;
		this.client = client;
	}

	public void run() {
		System.out.println("Server Starting..") ;
		synchronized(this){
            this.runningThread = Thread.currentThread();
        }

        try {
        	this.serverSocket = new ServerSocket(this.serverPort);
        	System.out.println("Server Started.");
        	System.out.println("This Machine (" + InetAddress.getLocalHost() + ") is now waiting for incomming device connection.");
        } catch (IOException e) {
        	throw new RuntimeException("Cannot open port " + this.serverPort, e);
        }

        while(! isStopped()){
            try {
            	Socket clientSocket = this.serverSocket.accept();
                System.out.println("Client connected");

                if (!this.client.isConnected()) {
                	this.client.bind(clientSocket);
                    new Thread(new WorkerRunnable(this.client)).start();
                } else {
                	System.out.println("Client connection has been denied, other device already connected.");
                	clientSocket.close();
                }
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.") ;
                    return;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }
        }
        System.out.println("Server Stopped.") ;
	}

	private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }
}
