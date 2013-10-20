/*Author
Ramen Devansh 
CS STUDENT Level 3
Dept of Computer Science 
University Of Mauritius
*/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
 
public class Client extends JFrame implements ActionListener {

	//private JButton btnSend = new JButton("Send");
	private JButton btnJoinGroupStudent = new JButton("Join Student Group");
	private JButton btnJoinGroupLeisure = new JButton("Join Leisure Group");
	
	private JPanel mainPanel = new JPanel();
	//private JPanel chatPanel = new JPanel();
	private JPanel groupPanel = new JPanel();
	//private JPanel inputPanel = new JPanel();
	
	
	//public static JTextArea DISPLAY = new JTextArea();
	public static String SERVERADDRESS = "localhost";
	public static int SERVERPORT = 10007;
	public static int LISTENPORT;
	public static String CLIENTJOIN;
	
  public static void main(String[] args) throws IOException {
		new Client();

  }
 
    public void actionPerformed(ActionEvent e) {
	   if (e.getSource() == btnJoinGroupStudent){
		   LISTENPORT = 2406;
	    	try {
				DatagramSocket aSocket = new DatagramSocket();
					CLIENTJOIN = "joiningS";
					byte [] m = CLIENTJOIN.getBytes();
					InetAddress address = InetAddress.getByName(Client.SERVERADDRESS);
					DatagramPacket request = new DatagramPacket(m, m.length, address, Client.SERVERPORT);
					aSocket.send(request);
			}catch (SocketException evt){	
			}catch (IOException evt){
			}
	    	
	        new GUIChatBox();
	        this.setVisible(false);
		   
	   }
	   
	   if (e.getSource() == btnJoinGroupLeisure){
		   LISTENPORT = 2506;
	    	try {
				DatagramSocket aSocket = new DatagramSocket();
					CLIENTJOIN = "joiningL";
					byte [] m = CLIENTJOIN.getBytes();
					InetAddress address = InetAddress.getByName(Client.SERVERADDRESS);
					DatagramPacket request = new DatagramPacket(m, m.length, address, Client.SERVERPORT);
					aSocket.send(request);
			}catch (SocketException evt){	
			}catch (IOException evt){
			}
	    	
	        new GUIChatBox();
	        this.setVisible(false);
		   
	   }
    }
 
  public Client() {

	  	groupPanel.add(btnJoinGroupStudent);
	  	groupPanel.add(btnJoinGroupLeisure);
	  	btnJoinGroupStudent.addActionListener(this);
	  	btnJoinGroupLeisure.addActionListener(this);
	  	
	    mainPanel.add(groupPanel); 
	    add(mainPanel);
	 
	    setTitle("Welcome to our chat system" );
	    setSize(600, 400);
	    setVisible(true);


 
  }
}
/*
class ChatListener implements Runnable { 
    
	private MulticastSocket multiSocket;
    
	public void run() { 
        try 
        {
            InetAddress group = InetAddress.getByName("224.2.2.3");
            multiSocket = new MulticastSocket(Client.LISTENPORT);
            multiSocket.joinGroup(group);
            while ( true ) { 
            	System.out.println("listening");
                byte[] buf = new byte[1000];
                DatagramPacket receiveMessage = new DatagramPacket(buf, buf.length);
                multiSocket.receive(receiveMessage);
                System.out.println( GUIChatBox.USERNAME + " data received " + new String (receiveMessage.getData()));
                GUIChatBox.DISPLAY.setText(GUIChatBox.DISPLAY.getText() + "\n " + new String (receiveMessage.getData()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    } 
    
}

*/


class ChatListener implements Runnable { 

	public static DatagramSocket aSocket;

	public void run() { 
        try 
        {	
        	aSocket = new DatagramSocket(Client.LISTENPORT);
			while ( true ) { 
				byte [] buffer = new byte [1000];
				DatagramPacket receiveMessage = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(receiveMessage);
	            System.out.println("Client received new message");
	            GUIChatBox.DISPLAY.setText(GUIChatBox.DISPLAY.getText() + "\n " + new String (receiveMessage.getData()).substring(1));
			}
        }catch (Exception e){
            e.printStackTrace();

		}
    } 
    
}

