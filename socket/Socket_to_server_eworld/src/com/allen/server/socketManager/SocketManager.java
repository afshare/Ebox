package com.allen.server.socketManager;



public interface SocketManager {
	/*
	 * ��Ӵ�����������
	 */
	public abstract boolean Add(byte[] bstream,int socketID) throws Exception;//�����յ������ݴ���Ȼ�������ݿ�
	/*
	 * ������
	 */
	public abstract boolean GiveOrder(int FacNumber,byte[] order);
	public abstract boolean GiveOrder(long FacNumber, byte[] order);
	public abstract boolean GiveOrder();
	/*
	 * ����豸
	 */
	public abstract boolean AddtheFacWithOnline();
}
