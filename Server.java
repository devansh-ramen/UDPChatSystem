/*
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
 
public class Server {
	
  public DatagramSocket aSocket = null;
  public DatagramPacket request;
  public DatagramPacket reply;
  public static int JOINCOUNTSTUDENT=0;
  public static int JOINCOUNTLEISURE=0;
  public static int CLIENTSTUDENTPORT=2406;
  public static int CLIENTLEISUREPORT=2506;
  public static void main(String[] args) {
	  
	  new Server();
  }
 
  public Server() {
	  InetAddress [] arrayClientIpStudent = new InetAddress [30];
	  InetAddress [] arrayClientIpLeisure = new InetAddress [30];
     // arrayclientip [0] = "192.168.1.20";
      try{
			aSocket = new DatagramSocket(10007);
			
			while (true){
					byte [] buffer = new byte [1000];
					DatagramPacket request = new DatagramPacket(buffer, buffer.length);
					aSocket.receive(request);
					String data = "";
					data = new String (request.getData());
					String joiningStudentKeyCode = "joiningS";
					String joiningLeisureKeyCode = "joiningL";
					if (data.substring(0,8).trim().equals(joiningStudentKeyCode.trim())){
						
						System.out.println("array" + arrayClientIpStudent [JOINCOUNTSTUDENT]);
						
						int i;
						boolean foundSameIp = false;
						for ( i = 0 ; i < JOINCOUNTSTUDENT ; i ++ ){
							if (arrayClientIpStudent[i].equals(request.getAddress())){
								foundSameIp = true;
								break;
							}
						}

						if (foundSameIp != true){
							arrayClientIpStudent [JOINCOUNTSTUDENT] = request.getAddress();
							System.out.println("Not same "+arrayClientIpStudent [JOINCOUNTSTUDENT]  + " " +request.getAddress() );
							JOINCOUNTSTUDENT++;
						}
						
					}else if (data.substring(0,8).trim().equals(joiningLeisureKeyCode.trim())){
						
						int i;
						boolean foundSameIp = false;
						for ( i = 0 ; i < JOINCOUNTLEISURE ; i ++ ){
							if (arrayClientIpLeisure[i].equals(request.getAddress())){
								foundSameIp = true;
								break;
							}
						}

						if (foundSameIp != true){
							arrayClientIpLeisure [JOINCOUNTLEISURE] = request.getAddress();
							System.out.println("Not same "+arrayClientIpLeisure [JOINCOUNTLEISURE]  + " " +request.getAddress() );
							JOINCOUNTLEISURE++;
						}
					
					}else{	
						System.out.println("PORT: " + data.substring(0,1));
						if (data.substring(0,1).equals("S")){
							for ( int i = 0 ; i < JOINCOUNTSTUDENT ; i ++ ){
									System.out.println("i "+i);
									System.out.println(arrayClientIpStudent [i]);
									InetAddress clientaddress = arrayClientIpStudent[i];
									//System.out.println(clientaddress + "vs" + request.getAddress());
									//String truedata = data.substring(1);
									//byte [] m = truedata.getBytes();
									DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), clientaddress, CLIENTSTUDENTPORT);
									System.out.println("server received packet");
									aSocket.send(reply);
									System.out.println("server sent packet");
							}
						}
						else if (data.substring(0,1).equals("L")){
							for ( int i = 0 ; i < JOINCOUNTLEISURE ; i ++ ){
								System.out.println("i "+i);
								System.out.println(arrayClientIpLeisure [i]);
								InetAddress clientaddress = arrayClientIpLeisure[i];
								//String truedata = data.substring(1);
								//byte [] m = truedata.getBytes();
								DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), clientaddress, CLIENTLEISUREPORT);
								System.out.println("server received packet");
								aSocket.send(reply);
								System.out.println("server sent packet");
							}
						}
					}
			}
		}catch(SocketException evt){
			
		}catch(IOException evt){
	
		}finally{
			if (aSocket != null){
				aSocket.close();
			}
		}
     }
}
