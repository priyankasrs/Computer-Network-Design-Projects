import java.net.*;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.io.BufferedWriter.*;


public class SlaveBot extends Thread{

	Socket s; //for communication between client and server

	public static ArrayList<Socket> conn_list = new ArrayList<>();
	
	public static ArrayList<Socket> conn = new ArrayList<>();

	
	
	public static void main(String [] args) 
	{
		
		if(args.length != 0)
		{
			
			if(args.length == 4)
			{
				listener lis = new listener(Integer.parseInt(args[3]),args[1]);
			}
			else if(args.length == 3)
			{
				listener lis = new listener(Integer.parseInt(args[2]),args[1]);
			}
		}

		while(true)
		{
			Scanner in = new Scanner(System.in);
			String line = in.nextLine();
		
			String[] arrayOfString = line.split("\\s+");
			if(arrayOfString.length == 4)
			{
				String serverName = arrayOfString[1];
				int port = Integer.parseInt(arrayOfString[3]);
				listener lis = new listener(port,serverName);
			}
			else if(arrayOfString.length == 3)
			{
				String serverName = arrayOfString[1];
				int port = Integer.parseInt(arrayOfString[2]);
				listener lis = new listener(port,serverName);
			}

		}
	}
		public static class listener
		{
			private int port;
			private String ip;
			public listener(int port, String ip)
			{
				this.ip = ip;
				this.port = port;
				try {
					
					Socket client = new Socket(ip, port);
					
					conn_list.add(client);
					//System.out.println(lstconnected.size());
					System.out.println("connected to " + client.getRemoteSocketAddress());    	
				}catch(IOException e) {
					e.printStackTrace();
					
				}
			}
		}
		@SuppressWarnings("deprecation")
		public void createSocket(Socket news ,int port,String ip, int connections) throws IOException
		{
				
			try {
				
				s = new Socket();
					
				s.connect(new InetSocketAddress(ip,port));
				conn.add(s);
		        if(s.isConnected()){
		        	System.out.println("\nClient: "+news.getRemoteSocketAddress().toString()+" connected to "+ s.getInetAddress().toString()+"\nTarget IP: "+s.getLocalPort()+"\n");
	        	

		        }
		       
					}catch(IOException e) {
					e.printStackTrace();
					
			
					} 
					
			
		
		}
		
		
		public void disconnect(int targetport, String TargetAdd) {
		
			String line;
			
			try {		
				
					if(targetport == 1000)
					{
						
						for(int i = 0; i < conn.size(); i++)
						{
							
						//System.out.println(conn.size()+"  "+ i);
						if(!conn.get(i).isClosed())
							{
								System.out.println("Connection to "+conn.get(i).getRemoteSocketAddress()+" "+conn.get(i).getLocalPort()+" is closed\n");
								conn.get(i).close();
								
							}
						else
							{
								System.out.println("No disconnect possible as Connection to "+conn.get(i).getRemoteSocketAddress()+" "+conn.get(i).getLocalPort()+" is closed\n");
							}
						}
					}
					else
					{
						
						for(int i = 0; i < conn.size(); i++)
						{
							
								if(conn.get(i).getLocalPort() == targetport)
								{
								
									if(!conn.get(i).isClosed())
									{
										System.out.println("Connection to "+conn.get(i).getRemoteSocketAddress()+" "+conn.get(i).getLocalPort()+" is closed\n");
										conn.get(i).close();
										
									}
									else
									{
										System.out.println("No disconnect is possible as Connection to "+conn.get(i).getRemoteSocketAddress()+" "+conn.get(i).getLocalPort()+" is closed\n");
									}
								}
							
						}
					}
					
				
					}catch(IOException e) {
					e.printStackTrace();
					
					}

		}
		

}
