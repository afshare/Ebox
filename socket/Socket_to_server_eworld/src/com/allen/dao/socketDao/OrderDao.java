package com.allen.dao.socketDao;

import com.allen.model.OrderEworld;


public interface OrderDao {
	/**
	 * ���豸�ŷ���Order������һ��Orderģ�ͣ���������ȡ����֮�󽻸���һ�㴦��
	 * @return
	 * @throws Exception
	 */
	public OrderEworld GiveOrder() throws Exception;
	/**
	 * �޸�Orde���Order ��״̬������޸ĳɹ��ͷ���true����false
	 * @return
	 * @throws Exception
	 */
	public boolean ChangeOrderState() throws Exception;

}
