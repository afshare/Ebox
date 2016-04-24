package com.allen.server.socketManager;



public interface SocketManager {
	public abstract void add(byte[] b) throws Exception;//将接收到的数据处理，然后传入数据库
}
