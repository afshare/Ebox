package com.allen.dao.socketDao;

import com.allen.model.OrderEworld;


public interface OrderDao {
	/**
	 * 给设备号发送Order，返回一个Order模型，将数据提取出来之后交给上一层处理
	 * @return
	 * @throws Exception
	 */
	public OrderEworld GiveOrder() throws Exception;
	/**
	 * 修改Orde表的Order 的状态，如果修改成功就返回true否则false
	 * @return
	 * @throws Exception
	 */
	public boolean ChangeOrderState() throws Exception;

}
