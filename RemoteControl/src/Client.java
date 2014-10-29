import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.microedition.io.StreamConnection;


public class Client {

	private boolean connected;
	private String name;
	private InputStream in;
	private OutputStream out;

	public Client() {

	}

	public synchronized boolean isConnected() {
		return this.connected;
	}

	public synchronized void bind(Socket socket) throws IOException {
		this.connected = true;
		setInputStream(socket.getInputStream());
		setOutputStream(socket.getOutputStream());
	}

	public synchronized void bind(StreamConnection conn) throws IOException {
		this.connected = true;
		setInputStream(conn.openInputStream());
		setOutputStream(conn.openOutputStream());
	}

	public synchronized void disconnect() throws IOException {
		this.connected = false;
		this.in.close();
		this.out.close();
	}

	public InputStream getInputStream() {
		return in;
	}

	public void setInputStream(InputStream in) {
		this.in = in;
	}

	public OutputStream getOutputStream() {
		return out;
	}

	public void setOutputStream(OutputStream out) {
		this.out = out;
	}
}
