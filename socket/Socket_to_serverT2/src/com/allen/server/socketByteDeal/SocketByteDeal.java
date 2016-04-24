package com.allen.server.socketByteDeal;

public interface SocketByteDeal {
	public abstract void Deal(byte[] bstream) throws Exception;//将接收到的数据处理，然后传入数据库

}