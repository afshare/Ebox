package getsocket;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.*;

import com.allen.dao.dbConnection.DB_Connection;
 



public class SocketTest extends Thread{

	
	ServerSocket server = null; //�������Ĳ���
	Socket scwifi = null;	    //�ͻ��˵Ĳ���
	BufferedReader bi  = null;  //��ȡ��������
	PrintWriter pwt = null;		//д���ݵ�wifiģ��
	
/*	InputStream is = null;
	OutputStream os = null;*/
	
	int theSize = 20;			//����data���ֽ�����С
	int getSizeCounter = 0;		//������յ������ݴ�С

	String getDataTest = new String();
	
	public SocketTest()
	{
		
		try
		{
			server = new ServerSocket(1111);//��1111ͨ��
			System.out.println("Server open success~s");
		}
		catch(IOException e)
		{
			e.printStackTrace();//���ӳ���
			System.out.println("Init port fail");
		}
	}
	
	public void run()
	{
		while(true)											//һֱ���������wifiģ��
		{
			System.out.println("Listennin...");
			try 
			{
				scwifi = server.accept();					//���յ�������
				System.out.println("Get access~");
				ServerThread th = new ServerThread(scwifi);
				th.start();
				System.out.println("Finish one Thread.");
//				sleep(1000);								//��Ҫ��֤�����費��Ҫsleep
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
	
	class ServerThread extends Thread		//�ٿ�һ���߳�ȥ����wifiģ���������ͨѶ����Ҫ��
	{
		Socket sk = null;
		public ServerThread(Socket sk)
		{
			this.sk  = sk;
		}
		public void run()
		{
			try {
				boolean connect = true;						//���ӳɹ��˵�
				byte getData[] = new byte[theSize];			//����һ��20���ֽڵ�bao
				InputStream isTemplet = null;
				OutputStream osTemplet = null;
				while(true)
				{


				int getSizeCounterNow = getSizeCounter;	
				try{
					osTemplet = sk.getOutputStream();
					isTemplet = sk.getInputStream();		//�õ�wifiģ����յ�������
					sk.sendUrgentData(0);					//һֱ���ͽ������ݼ���Ƿ�����
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
					////////////////������д�����뵽�����ݴ������ݿ�ĺ���
					////////////////////////////////////////////////////
				}
				catch(IOException e)
				{
//					e.printStackTrace();
					System.out.println("Broken in 4.1");
					connect = false;
					break;
				}
				System.out.println(getSizeCounterNow);	//��ӡ�����յ������ݸ���
				if(sk.isConnected())					//��Ҫ���ж��Ƿ����������ڽ������ݵķ���
				{
					try{
						sk.sendUrgentData(0);			//һֱ���ͽ������ݼ���Ƿ�����,����������˵ľͼ�����������
					}
					catch(IOException e)
					{
//						e.printStackTrace();
						System.out.println("Broken in 2.1");
						connect = false;
						break;
					}
					osTemplet.write(getData);			//��ͻ��˷�������
					osTemplet.flush();
				}
				if(connect == false)break;				//����ͻ��˶��ߣ���������ѭ��
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
