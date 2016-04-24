package com.allen.dao.socketDao;

import com.allen.model.Eworld;
import com.allen.model.EworldCO2;
import com.allen.model.EworldDust;
import com.allen.model.EworldFormaldehyde;
import com.allen.model.EworldHumidity;
import com.allen.model.EworldTemperayure;


/**
 * 用于存储传感器数据的抽象
 * @author Alish
 *
 */
public interface SocketDao {

	/**
	 * 	存储Eword
	 * @param eworld
	 * @return
	 * @throws Exception
	 */
	public boolean Save(Eworld eworld) throws Exception;
	/**
	 * 存储温度数据
	 * @param eworldT
	 * @return
	 * @throws Exception
	 */
	public boolean SaveTem(EworldTemperayure eworldT) throws Exception;
	/**
	 * 存储湿度数据
	 * @param eworldH
	 * @return
	 * @throws Exception
	 */
	public boolean SaveHum(EworldHumidity eworldH) throws Exception;
	/**
	 * 存数二氧化碳数据
	 * @param eworldCo2
	 * @return
	 * @throws Exception
	 */
	public boolean SaveCO2(EworldCO2 eworldCo2) throws Exception;
	/**
	 * 存数粉尘(PM2.5)数据
	 * @param eworldDust
	 * @return
	 * @throws Exception
	 */
	public boolean SaveDust(EworldDust eworldDust) throws Exception;
	/**
	 * 存储甲醛数据
	 * @param eworldFor
	 * @return
	 * @throws Exception
	 */
	public boolean SaveFor(EworldFormaldehyde eworldFor) throws Exception;
}
