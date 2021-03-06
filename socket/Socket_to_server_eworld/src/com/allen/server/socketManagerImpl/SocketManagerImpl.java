package com.allen.server.socketManagerImpl;

import java.io.IOException;
import java.io.OutputStream;


import com.allen.model.OrderEworld;
import com.allen.server.socketByteDeal.SocketByteDeal;
import com.allen.server.socketByteDealImpl.SocketByteDealImpl;
import com.allen.server.socketManager.SocketManager;
import com.allen.dao.socketDao.OrderDao;
import com.allen.dao.socketDaoImpl.OrderDaoImpl;

public class SocketManagerImpl implements SocketManager {
	

	@Override
	public boolean Add(byte[] bstream,int socketID) throws Exception {
		
		SocketByteDeal skbd = new SocketByteDealImpl();
		/////////////////////////
		/////1`处理字节流,判断是否符合标准
		////////////////////////
		
		if(skbd.Chick(bstream, 20))
		{
			System.out.println("Chick success");
		/////////////////////////
		/////2`处理检查包头和包尾通过的数据
		////////////////////////
			skbd.Deal(bstream,socketID);

		}
		else
		{
			System.out.println("Chick fail");
			return false;
		}

		

//		skDao.Save(eworld);
		return true;
	}
	@Override
	public boolean GiveOrder(int FacNumber, byte[] order) {
		System.out.println("Get into UserOrder");
		
		int SockIDTemplet = 0;//存储搜寻到的设备编号对应的设备编号下标和socket数组下标
		OutputStream osTemplet = null;//存储socket的输出流
		for(SockIDTemplet = 0;SockIDTemplet < getsocket.SocketTest.counterSocket;SockIDTemplet++)
		{
			System.out.println("FacNumber:"+FacNumber);
			if(getsocket.SocketTest.sockettofac[SockIDTemplet].FaitityNumber == FacNumber)
			{
				System.out.println("Find FacNumber:"+getsocket.SocketTest.sockettofac[SockIDTemplet].FaitityNumber);
				break;//搜索到对应的设备号在线
			}
		}
		if(SockIDTemplet == getsocket.SocketTest.counterSocket)//如果没有搜索到对应的设备号,也就是循环之后设变编号下标扫描数值超过了已有的设备数号
		{
			System.out.println("Order Fail_1111111111111111111111111111");
			return false;//发送命令失败,因为没有这样的设备号连接进来
		}
		//////////////////////////////
		////////////判断是否连接上了
		///////////////////////////////
		try {
			getsocket.SocketTest.sockettofac[SockIDTemplet].socket.sendUrgentData(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.print("Not Linked");
			return false;
		}
		/////////////////////////////////
		/////////////////如果连接上了去获得该socket的输出流，然后在发送；这里需要测试在如果两个地方同时调用输出流的时候会出现什么情况
		////////////////////////////////
		try {
			osTemplet = getsocket.SocketTest.sockettofac[SockIDTemplet].socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/////////////////////////////////////////////////////////////////////////
		//////////////开始发送指令
		/////////////////////////////////////////////////////////////////////////
		try										//捕获发送字节时候的异常
		{
			osTemplet.write(order);			//向客户端发送数据
			osTemplet.flush();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;//顺利完成之后就返回
	}
	
	
	@Override
	public boolean GiveOrder(long FacNumber, byte[] order) {
		System.out.println("Get into UserOrder");
		
		int SockIDTemplet = 0;//存储搜寻到的设备编号对应的设备编号下标和socket数组下标
		OutputStream osTemplet = null;//存储socket的输出流
		for(SockIDTemplet = 0;SockIDTemplet < getsocket.SocketTest.counterSocket;SockIDTemplet++)
		{
			System.out.println("FacNumber:"+FacNumber);
			if(getsocket.SocketTest.sockettofac[SockIDTemplet].FaitityNumber == FacNumber)
			{
				System.out.println("Find FacNumber:"+getsocket.SocketTest.sockettofac[SockIDTemplet].FaitityNumber);
				break;//搜索到对应的设备号在线
			}
		}
		if(SockIDTemplet == getsocket.SocketTest.counterSocket)//如果没有搜索到对应的设备号,也就是循环之后设变编号下标扫描数值超过了已有的设备数号
		{
			System.out.println("Order Fail_1111111111111111111111111111");
			return false;//发送命令失败,因为没有这样的设备号连接进来
		}
		//////////////////////////////
		////////////判断是否连接上了
		///////////////////////////////
		try {
			getsocket.SocketTest.sockettofac[SockIDTemplet].socket.sendUrgentData(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.print("Not Linked");
			return false;
		}
		/////////////////////////////////
		/////////////////如果连接上了去获得该socket的输出流，然后在发送；这里需要测试在如果两个地方同时调用输出流的时候会出现什么情况
		////////////////////////////////
		try {
			osTemplet = getsocket.SocketTest.sockettofac[SockIDTemplet].socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/////////////////////////////////////////////////////////////////////////
		//////////////开始发送指令
		/////////////////////////////////////////////////////////////////////////
		try										//捕获发送字节时候的异常
		{
			osTemplet.write(order);			//向客户端发送数据
			osTemplet.flush();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;//顺利完成之后就返回
	}
	/**
	 * 通过数据库给命令
	 */
	@Override
	public boolean GiveOrder() {
		//打印进入该函数的标志
//		System.out.println("Get into UserOrder");
		
		//进入OrderDao读取ordermodel
		OrderDao orderdaotem = new OrderDaoImpl();
		
		//new一个order模型，用于存储设别号和命令
		OrderEworld orderemodel = new OrderEworld();
		
		//进行数据的读取，和发送命令，然后改变命令发送状态
		try {
			orderemodel = orderdaotem.GiveOrder();
			orderdaotem.ChangeOrderState();						//改变Order表的状态
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		int SockIDTemplet = 0;//存储搜寻到的设备编号对应的设备编号下标和socket数组下标
		OutputStream osTemplet = null;//存储socket的输出流
		for(SockIDTemplet = 0;SockIDTemplet < getsocket.SocketTest.counterSocket;SockIDTemplet++)
		{
			//打印到频幕上，查看是否发送的设备号是正确的
			System.out.println("FacNumber:"+orderemodel.getFacNumber());
			if(getsocket.SocketTest.sockettofac[SockIDTemplet].FaitityNumber == orderemodel.getFacNumber())
			{
				System.out.println("Find FacNumber:"+getsocket.SocketTest.sockettofac[SockIDTemplet].FaitityNumber);
				break;//搜索到对应的设备号在线
			}
		}
		if(SockIDTemplet == getsocket.SocketTest.counterSocket)//如果没有搜索到对应的设备号,也就是循环之后设变编号下标扫描数值超过了已有的设备数号
		{
			System.out.println("Order Fail");
			return false;//发送命令失败,因为没有这样的设备号连接进来
		}
		//////////////////////////////
		////////////判断是否连接上了
		///////////////////////////////
		try {
			//发送一个紧急信号，判断是否在线
			getsocket.SocketTest.sockettofac[SockIDTemplet].socket.sendUrgentData(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.print("Not Linked");
			return false;
		}
		/////////////////////////////////
		/////////////////如果连接上了去获得该socket的输出流，然后在发送；这里需要测试在如果两个地方同时调用输出流的时候会出现什么情况
		////////////////////////////////
		try {
			osTemplet = getsocket.SocketTest.sockettofac[SockIDTemplet].socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/////////////////////////////////////////////////////////////////////////
		//////////////开始发送指令
		/////////////////////////////////////////////////////////////////////////
		try										//捕获发送字节时候的异常
		{
			System.out.println("The Order is"+orderemodel.getOrder());
			osTemplet.write(orderemodel.getOrder());			//向客户端发送数据
			osTemplet.flush();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;//顺利完成之后就返回
	}
	@Override
	public boolean AddtheFacWithOnline() {

		return false;
	}


}
