import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.sql.Date;

public class MasterBot
{
	ServerSocket Ssock;
	ServerListener Slist;
	Thread st;
	ArrayList<Sinfo_client> Clist;
	int pnum;
	BufferedReader BR;
	PrintWriter prwrite;
	
	public MasterBot(int pnum)
	{
		try 
		{
			this.pnum = pnum;
			this.Ssock = new ServerSocket(pnum);
			this.Clist = new ArrayList<Sinfo_client>();
			this.Slist = new ServerListener();

			this.st  = new Thread(this.Slist);
			this.st.start();
		
		} 
		catch (IOException e)
		{
					e.printStackTrace();
		}
	}

	public void runCommand(String str) throws IOException
	{	//split your input based on space
				String[] Scmd = str.split(" ");
				//return if no string
				if(Scmd.length==0)
				return;
				//else split cases
				switch(Scmd[0])
				{
					case "list":System.out.println("SlaveHostName IPAddress SourcePortNumber RegistrationDate");
						//display list
						for(Sinfo_client C: Clist)
						{
							System.out.print(C.getHostname() + " ");
							System.out.print(C.getIpaddress() + " ");
							System.out.print(C.getPort() + " ");
							System.out.print(C.getRegisteredDate() + " ");
							System.out.println();
						}
						break;

					case "connect":
						for(Sinfo_client C : Clist)
						{
							prwrite = new PrintWriter(C.getSocket().getOutputStream(), true);
							prwrite.println(str);
						}
						break;

					case "disconnect":
						for(Sinfo_client C : Clist)
						{
							prwrite = new PrintWriter(C.getSocket().getOutputStream(), true);
							prwrite.println(str);
						}
						break;

					default:
						//exit
						System.exit(-1);
						break;
				}
	}
	public static void main(String[] args)
	{	//initialize pnum 
		int pnum=0;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//assigning pnum number from our cmd line argument
		
		if(args[0].equals("p")&&(args.length==2))
		{
			pnum = Integer.parseInt(args[1]);
		}
		else
		{
			System.exit(-1);//exiting the program if pnum num not given 
		}
		//create object
		MasterBot M = new MasterBot(pnum);
		String str;
		//call function inside loop		
		while(true)
		{	str = null;
			try
			{	System.out.print(">");
				str = br.readLine();
				//check if exit/null

				if(str.equals("exit"))
					break;
				if(str != null)
				{
					M.runCommand(str);
				}
			} 
			catch (IOException e)
			{
					e.printStackTrace();
			}
		}

	}
	private class ServerListener extends Thread
	{
		public ServerListener() 
		{
		
		}
		@Override
		public void run()
		{
			try
			{
				while(true)
				{//get initial values
					Socket sc = Ssock.accept();
					InetAddress addr = sc.getInetAddress();
					String hostaddr = addr.getHostAddress();
					String name = addr.getHostName();
					Date d = new Date(System.currentTimeMillis());
					int num_p = pnum;
					
					Sinfo_client new_clientInfo = new Sinfo_client(name, hostaddr, num_p, 													sc, d);
					//add to client					
					Clist.add(new_clientInfo);
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unused")
	private class Sinfo_client 
	{
		private String name;
		private String hostaddr;
		private int pnum;
		private Socket sock;
		private Date d;

		public Socket getSocket()
		{
			return sock;
		}
		public String getIpaddress()
		{
			return hostaddr;
		}
		public int getPort()
		{
			return pnum;
		}
		public Sinfo_client(String name, String hostaddr, int pnum, Socket sock, Date d)
		{
				this.name = name;
				this.hostaddr = hostaddr;
				this.pnum = pnum;
				this.sock = sock;
				this.d = d;
		}
		public String getHostname()
		{
			return name;
		}
		

		public void setHostname(String name)
		{
			this.name = name;
		}
		public void setPort(int pnum)
		{
			this.pnum = pnum;
		}
		public Date getRegisteredDate()

		{
			return d;
		}
		public void setIpaddress(String ipaddress)
		{
			this.hostaddr = hostaddr;
		}
		
		public void setRegisteredDate(Date d)
		{
			this.d = d;
		}
		public void setSocket(Socket socket)
		{
			this.sock = socket;
		}
		
	}
}


