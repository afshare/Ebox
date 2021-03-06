package getsocket;
import java.net.*;
import java.io.*;

import com.allen.dao.socketDao.FacilityStateDAO;
import com.allen.dao.socketDaoImpl.FacilityStateDAOImpl;
import com.allen.model.FacilityEworldState;
import com.allen.server.socketManager.SocketManager;
import com.allen.server.socketManagerImpl.SocketManagerImpl;

import java.util.Date;  
import java.util.Timer;  
import java.util.TimerTask;  
 

/**
 * while(1)
 * {
 * 		进程开启（申请变量，记录接入的SocketID和）
 * 			{
 * 				进程开启（进行同行等主要业务）
 * 			}（一旦break，就清空变量）
 * }
 *
 * @author Alish
 *
 */

public class SocketTest extends Thread{

	final long timeInterval = 1000;
	ServerSocket server = null; //服务器的操作
	Socket scwifi = null;	    //客户端的操作
	BufferedReader bi  = null;  //读取的数据流
	PrintWriter pwt = null;		//写数据到wifi模块
	
/*	InputStream is = null;
	OutputStream os = null;*/
	
	int theSize = 20;			//定义data的字节数大小
	int getSizeCounter = 0;		//定义接收到的数据大小

	String getDataTest = new String();
	///////////////////////////////////////////////
	//////////下面是绑定socket编号和设备编号,主要用于设备号与接入编号做映射
	//////////////////////////////////////////////
	public static SocketToFac[] sockettofac = new SocketToFac[10];
	public static int counterSocket = 0;//一共连接进来的socket暂时设置最多10个
/*	public static 	Socket[] socketNumber = new Socket[10];	//socket编号
	public static int[] FaitityNumber = new int[10];	//设备编号
*/	
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
		ServerThread th;
		
		while(true)											//一直监听接入的wifi模块
		{
			System.out.println("Listennin...");
			try 
			{
				sockettofac[counterSocket].socket = server.accept();//接收到了连接
				
//				scwifi = server.accept();
//				sockettofac[counterSocket].socket = scwifi;
				sockettofac[counterSocket].SocketID = counterSocket;//将得到的连接的ID记录下来
				System.out.println("Get access~"+sockettofac[counterSocket].socket.getInetAddress());
				th = new ServerThread(sockettofac[counterSocket]);//将获得的连接传入线程
				th.start();
				
				//////////////////////////////////////////
				//测试定时发送函数
				//////////////////////////////////////////
				th.test1();
				
				
				counterSocket++;//获得一个socket连接
				
				System.out.println("Finish one Thread.");
//				sleep(1000);								//需要验证到底需不需要sleep
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main(String[] args)
	{
		for(int i = 0;i<10;i++)//初始化SocketToFac数组
			sockettofac[i] = new SocketToFac();
		new SocketTest().start();
		
	}
	
	
	class ServerThread extends Thread		//再开一个线程去处理wifi模块与服务器通讯，需要吗？
	{
		Socket sk = null;		//socket对象
		int SocketID = 0;		//socketID
		FacilityStateDAO fsdao = new FacilityStateDAOImpl();//用于操作监控状态
		SocketManager skm  = new SocketManagerImpl();
		
		public ServerThread(SocketToFac sktofac)
		{
			this.sk  = sktofac.socket;
			SocketID = sktofac.SocketID;
		}
		/////////////////////////////////////////////////////////////////////
		 // 2:5000毫秒后，执行任务，以后每隔10000毫秒再执行一次任务(无限执行)(10s)  
		////////////////////////////////////////////////////////////////////
	    public void test1() {  
	        new Timer().schedule(new TimerTask() {  
	           
	            public void run() {  
	            	
					/*测试用户给命令的代码*/
						skm.GiveOrder();//向发来的设备发送order
	            }  
	        }, 5000, 10000);  
	    }
	    
	    //////////////////////////////////////////////////////////////////////////
	    //进程执行。
	    //////////////////////////////////////////////////////////////////////////
		public void run()
		{
			try {
				boolean connect = true;						//连接成功了的
				byte getData[] = new byte[theSize];			//建立一个20个字节的bao
				InputStream isTemplet = null;
				OutputStream osTemplet = null;
				boolean isgetConnecting = false;
				while(true)
				{
					
					
					
					
					
					
					///////////////////////////////////////////////////////////
					////
					///////////////////////////////////////////////////////////
//					Thread.sleep(timeInterval);
					///////////////////////////////////////////////////////////
					////
					///////////////////////////////////////////////////////////
				int getSizeCounterNow = getSizeCounter;

				if(isgetConnecting == false)
				{
					try{
						osTemplet = sk.getOutputStream();
		//				osTemplet = sockettofac[0].socket.getOutputStream();//用同一个socket接收用来测试
						isTemplet = sk.getInputStream();		//得到wifi模块接收数据的数据流
	//					sk.sendUrgentData(0xff);				//一直发送紧急数据检查是否连接
						System.out.println("Connecting...");
						isgetConnecting = true;
					}
					catch(IOException e)
					{
						e.printStackTrace();
						System.out.println("Broken in 1.1");
						connect = false;
						break;
					}
				}
				
				
				//尝试发送一个字节个模块，判断是否还连接成功。
				try{
					sk.sendUrgentData(0x33);			//一直发送紧急数据检查是否连接,如果是连接了的就继续发送数据

				}
				catch(IOException e)
				{
//					e.printStackTrace();
					System.out.println("Broken in 0.11111111111111111111111111");
					connect = false;
					break;
				}
				
				
				
				
				
				
				
				
				
				
				
				
				if(sk.isConnected())					//需要先判断是否还在连接中在进行数据的发送
				{
					try
					{
						getSizeCounterNow = isTemplet.read(getData, 0, 20);
						
					}
					catch(IOException e)
					{
						e.printStackTrace();
						System.out.println("Broken in 2.1(read)");
						connect = false;
						break;
					}
				}
				/////////////////////////////////////////////////////
				////////////////在下面写将接入到的数据存入数据库的函数
				////////////////////////////////////////////////////
				
//				SocketManager skm  = new SocketManagerImpl();
				skm.Add(getData,SocketID);
				/*测试用户给命令的代码*/
				System.out.println(counterSocket);

//					byte[] order = {0x01,0x02,0x03,0x04};
//					if(!skm.GiveOrder(sockettofac[SocketID].FaitityNumber, order))//向发来的设备发送order
					if(!skm.GiveOrder())//向发来的设备发送order
						System.out.println("Order Fail");
					System.out.println(sockettofac[SocketID].FaitityNumber+"///"+sockettofac[SocketID].SocketID);
				
				////////////////////////////////////////////////////
				///////////
				////////////////////////////////////////////////////
				
				System.out.println(getSizeCounterNow);	//打印出接收到的数据个数
				if(sk.isConnected())					//需要先判断是否还在连接中在进行数据的发送
				{
					try{
						sk.sendUrgentData(0);			//一直发送紧急数据检查是否连接,如果是连接了的就继续发送数据

					}
					catch(IOException e)
					{
//						e.printStackTrace();
						System.out.println("Broken in 3.1");
						connect = false;
						break;
					}
					////////////////////////////////////////////////////////////////////
					//////接下来发送数据都是为了检测发送数据的正确性
					///////////////////////////////////////////////////////////////////
					try										//捕获发送字节时候的异常
					{
						osTemplet.write(getData);			//向客户端发送数据
						osTemplet.flush();
					}
					catch(IOException e)
					{
						connect = false;
						e.printStackTrace();
						System.out.println("Broken in 4.1");
					}
					
					//更新设备状态，如果表里面没有就增加一项，由于用户不可能在第一条数据发上来的时候就去看设备是否在线的状态，所以在这里正价设备在线状态时也是可以的
					try {
						System.out.println("Chaneg Table HaveAccess");
						FacilityEworldState fstateTempl = new FacilityEworldState();
						fstateTempl.setOnline(false);
						fstateTempl.seteId(String.format("%08x",(int)sockettofac[SocketID].FaitityNumber));
						
						//如果查到表中有相应的设备
						if(fsdao.SelectFac(fstateTempl))
						{
							fsdao.ChangeToOnline(fstateTempl);
							fsdao.ChangeRecentTime(fstateTempl);
						}
						else
						{
							fsdao.AddFacWithOnline(fstateTempl);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
					if(connect == false)//如果客户端断线，就跳出死循环
					{
						isTemplet.close();
						break;				
					}
				}	
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Broken in 5.1");
			}
			///////////////////////////////////
			/////////////////////////直接清空之前的socket
			//////////////////////////////////
			FacilityEworldState fstateTempl = new FacilityEworldState();
			fstateTempl.setOnline(false);
			fstateTempl.seteId(String.format("%08x",(int)sockettofac[SocketID].FaitityNumber));
			
			/*
			 * 操作数据库,改变设备状态为下线
			 */
			try {
				fsdao.ChangeToOffline(fstateTempl);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			sockettofac[SocketID].FaitityNumber = 0;//设备号在SocketByteDealImpl类里面解析的时候写入的
			sockettofac[SocketID].socket = null;
			sockettofac[SocketID].SocketID = 0;
				System.out.println("Done!");
				
			}
		
		
		
	}
	
}
