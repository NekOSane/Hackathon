import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ServerMain extends JFrame implements ActionListener {

	private JTextField text;
	private JComboBox combobox;
	private String[] connectionModes = {"Both", "Wifi", "Bluetooth"};


	public ServerMain() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());

		panel.add(new JLabel("Enter password"));

		text = new JTextField(10);
		panel.add(text);

		combobox = new JComboBox(connectionModes);
		combobox.setSelectedIndex(0);
		panel.add(combobox);

		JButton btn = new JButton("Start Server");
		btn.addActionListener(this);
		panel.add(btn);

		this.getContentPane().add(panel);

		this.setTitle("Server");
		this.setSize(500, 100);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
    {
        //Execute when button is pressed
		System.out.println("Your password is " + text.getText());
		System.out.println(combobox.getSelectedItem());
        System.out.println("You clicked the button");

        Client client = new Client();

        if (connectionModes[1].equals(combobox.getSelectedItem())) {
    		new Thread(new RemoteControlWifi(9000, client)).start();
        } else if (connectionModes[2].equals(combobox.getSelectedItem())) {
        	new Thread(new RemoteControlBluetooth(client)).start();
        } else {
        	new Thread(new RemoteControlWifi(9000, client)).start();
        	new Thread(new RemoteControlBluetooth(client)).start();
        }

    }

	public static void main(String[] args) {
		new ServerMain();
	}
}
