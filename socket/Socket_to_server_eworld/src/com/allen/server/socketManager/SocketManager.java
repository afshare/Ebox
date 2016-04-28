package com.allen.server.socketManager;



public interface SocketManager {
	/*
	 * 添加传感器的数据
	 */
	public abstract boolean Add(byte[] bstream,int socketID) throws Exception;//将接收到的数据处理，然后传入数据库
	/*
	 * 给命令
	 */
	public abstract boolean GiveOrder(int FacNumber,byte[] order);
	public abstract boolean GiveOrder(long FacNumber, byte[] order);
	public abstract boolean GiveOrder();
	/*
	 * 添加设备
	 */
	public abstract boolean AddtheFacWithOnline();
}
