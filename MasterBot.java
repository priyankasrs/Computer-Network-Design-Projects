import java.net.*;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.io.BufferedWriter.*;

public class MasterBot extends Thread {

	static String add_s,add_t, port_connect;
	static String conn,d_conn;

	static ArrayList<Socket> SBlist = new ArrayList<>();//to contain all slaves 

	static SlaveBot b = new SlaveBot();//create object

	public static void main(String [] args) throws IOException {

		int port =Integer.parseInt(args[1]);//get valid port number like 10004 from command line
		
			if(port != 0){
			try {
				Thread t = new MasterBot(port);
				t.start();
			
			}
			catch(IOException e) {
				e.printStackTrace();
				
			
			}
		}
		try{
		String str;//read form command line
		BufferedReader console = new BufferedReader
				(new InputStreamReader(System.in));
		BufferedReader br = null;
		
		while (true) {
			
			str = console.readLine();
			
			if (str.equals(""))
				continue;
			

			if (str.endsWith("list"))
			{	
				String para;

				br = new BufferedReader(new FileReader("client.txt"));//open file to store
				if((para = br.readLine()) == null)
				{
					System.out.println("List is Empty");
				}
				else
				{
				br = new BufferedReader(new FileReader("client.txt"));
				while ((para = br.readLine()) != null)
					{
						System.out.println(para);
					}
				}				

			}
			if (str.startsWith("connect"))
			{	
				String[] connect_var = str.split("\\s+");//split the path "\ \ \ "
				
				if(connect_var.length == 4) 
				{
					add_s = connect_var[1];
					add_t = connect_var[2];
					port_connect = connect_var[3];
					conn = "1";
				
				}
				else if(connect_var.length == 5)
				{
					
				add_s = connect_var[1];
				add_t = connect_var[2];
				port_connect = connect_var[3];
				conn = connect_var[4];
			
				}
				
		
				BufferedReader reader;
				if(add_s.equalsIgnoreCase("all"))
				{
					
					for(int k=0; k<SBlist.size();k++)
					{	 
						for(int j = 0; j < Integer.parseInt(conn);j++)
						{
							b.createSocket(SBlist.get(k),Integer.parseInt(port_connect),add_t,Integer.parseInt(conn));
							reader = new BufferedReader(new FileReader("client.txt"));
							
						}

					}


				}
				else
				{
					String line;
					
					for(int j =0; j< SBlist.size();j++)
					{
						line = "/"+add_s;

						if(line.equalsIgnoreCase(SBlist.get(j).getRemoteSocketAddress().toString()))
						{
							
							for(int k = 0; k < Integer.parseInt(conn);k++)
							{
							b.createSocket(SBlist.get(j),Integer.parseInt(port_connect),add_t,Integer.parseInt(conn));
							reader = new BufferedReader(new FileReader("client.txt"));
					
							
							}
						}
		
					}
				}
			}
					
		if (str.contains("disconnect"))
		{	 String[] disconnect_var = str.split("\\s+");
		if(disconnect_var.length == 3)
		{
			add_s = disconnect_var[1];
			add_t = disconnect_var[2];
			port_connect = "1000";
		}
		else
		{
			add_s = disconnect_var[1];
			add_t = disconnect_var[2];
			port_connect = disconnect_var[3];			
		}
			 if(add_s.equalsIgnoreCase("all"))
				{
					 
					 b.disconnect(Integer.parseInt(port_connect),add_t);
				
				}
			 else
			 	{
				 
					 b.disconnect(Integer.parseInt(port_connect),add_t);
				 
			 	}
		

		}
		
	
		}
		}catch (Exception e){
			e.printStackTrace();
		}


}

private ServerSocket serverSocket;


public MasterBot(int port) throws IOException {

	serverSocket = new ServerSocket(port);	
}	

public void run() {

	try {
		int count=0;
		BufferedWriter output = null;
		String text = "";
		File file = new File("client.txt");
		output = new BufferedWriter(new FileWriter(file));
		text = "SlaveHostName" +"\t"+"IPAddress"+"\t\t"+"SourcePortNumber"+"\t"+"RegistrationDate";
		output.write(text);
		output.newLine();
		while(true)
		{
			count++;

			
			Socket server = new Socket();
			server = serverSocket.accept();
			SBlist.add(server);
			text = "Client:"+count+"\t\t"  ;
			output.write(text);

			text = server.getRemoteSocketAddress()+"\t";
			output.write(text);

			text = serverSocket.getLocalPort()+"\t"+"\t";
			output.write(text);

			text = "\t"+new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());//format date
			output.write(text);
			output.newLine();


			output.flush();  

			
		}		   

	}
	catch(IOException e) {
		e.printStackTrace();
		
	}
	

	try {

		String Line;
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader("client.txt"));

		while ((Line = reader.readLine()) != null) {
			System.out.println(Line);
		}

	} catch (IOException e) {
		e.printStackTrace();
		
	} 
}	
}
