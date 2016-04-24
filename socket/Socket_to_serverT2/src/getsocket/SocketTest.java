package getsocket;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.*;

import com.allen.dao.dbConnection.DB_Connection;
 



public class SocketTest extends Thread{

	
	ServerSocket server = null; //服务器的操作
	Socket scwifi = null;	    //客户端的操作
	BufferedReader bi  = null;  //读取的数据流
	PrintWriter pwt = null;		//写数据到wifi模块
	
/*	InputStream is = null;
	OutputStream os = null;*/
	
	int theSize = 20;			//定义data的字节数大小
	int getSizeCounter = 0;		//定义接收到的数据大小

	String getDataTest = new String();
	
	public SocketTest()
	{
		
		try
		{
			server = new ServerSocket(1111);//打开1111通道
			System.out.println("Server open success~s");
		}
		catch(IOException e)
		{
			e.printStackTrace();//连接出错
			System.out.println("Init port fail");
		}
	}
	
	public void run()
	{
		while(true)											//一直监听接入的wifi模块
		{
			System.out.println("Listennin...");
			try 
			{
				scwifi = server.accept();					//接收到了连接
				System.out.println("Get access~");
				ServerThread th = new ServerThread(scwifi);
				th.start();
				System.out.println("Finish one Thread.");
//				sleep(1000);								//需要验证到底需不需要sleep
			} 
			catch (Exception e) 
			{
				
			}
		}
	}
	public static void main(String[] args)
	{
		new SocketTest().start();
	}
	
	class ServerThread extends Thread		//再开一个线程去处理wifi模块与服务器通讯，需要吗？
	{
		Socket sk = null;
		public ServerThread(Socket sk)
		{
			this.sk  = sk;
		}
		public void run()
		{
			try {
				boolean connect = true;						//连接成功了的
				byte getData[] = new byte[theSize];			//建立一个20个字节的bao
				InputStream isTemplet = null;
				OutputStream osTemplet = null;
				while(true)
				{


				int getSizeCounterNow = getSizeCounter;	
				try{
					osTemplet = sk.getOutputStream();
					isTemplet = sk.getInputStream();		//得到wifi模块接收到的数据
					sk.sendUrgentData(0);					//一直发送紧急数据检查是否连接
					System.out.println("Connecting...");
				}
				catch(IOException e)
				{
//					e.printStackTrace();
					System.out.println("Broken in 3.1");
					connect = false;
					break;
				}
				
				try
				{
					getSizeCounterNow = isTemplet.read(getData, 0, 20);
					/////////////////////////////////////////////////////
					////////////////在下面写将接入到的数据存入数据库的函数
					////////////////////////////////////////////////////
				}
				catch(IOException e)
				{
//					e.printStackTrace();
					System.out.println("Broken in 4.1");
					connect = false;
					break;
				}
				System.out.println(getSizeCounterNow);	//打印出接收到的数据个数
				if(sk.isConnected())					//需要先判断是否还在连接中在进行数据的发送
				{
					try{
						sk.sendUrgentData(0);			//一直发送紧急数据检查是否连接,如果是连接了的就继续发送数据
					}
					catch(IOException e)
					{
//						e.printStackTrace();
						System.out.println("Broken in 2.1");
						connect = false;
						break;
					}
					osTemplet.write(getData);			//向客户端发送数据
					osTemplet.flush();
				}
				if(connect == false)break;				//如果客户端断线，就跳出死循环
				}	
			} catch (IOException e) {
//				e.printStackTrace();
				System.out.println("Broken in 1.1");
			}
		}
		
		
		
	}
	
	
	public static void PastToMysql(String theString,byte theByte){
		Connection con;
		Statement sql;

		DB_Connection DB_C = new DB_Connection();;
		String uri = DB_C.getConnection_2();

		try{
			con = DriverManager.getConnection(uri);
			String condition = "Insert into Fil_Studio values('"+theString+"','"+theByte+"','"+theString+"')";
			sql = con.createStatement();
			sql.executeUpdate(condition);
			con.close();
		}catch(SQLException exp){
			String backNews=""+exp;
			System.out.print(backNews);
		}
	}
	
	
}
