package com.allen.dao.socketDao;

import com.allen.model.Eworld;
import com.allen.model.EworldCO2;
import com.allen.model.EworldDust;
import com.allen.model.EworldFormaldehyde;
import com.allen.model.EworldHumidity;
import com.allen.model.EworldTemperayure;


/**
 * ���ڴ洢���������ݵĳ���
 * @author Alish
 *
 */
public interface SocketDao {

	/**
	 * 	�洢Eword
	 * @param eworld
	 * @return
	 * @throws Exception
	 */
	public boolean Save(Eworld eworld) throws Exception;
	/**
	 * �洢�¶�����
	 * @param eworldT
	 * @return
	 * @throws Exception
	 */
	public boolean SaveTem(EworldTemperayure eworldT) throws Exception;
	/**
	 * �洢ʪ������
	 * @param eworldH
	 * @return
	 * @throws Exception
	 */
	public boolean SaveHum(EworldHumidity eworldH) throws Exception;
	/**
	 * ����������̼����
	 * @param eworldCo2
	 * @return
	 * @throws Exception
	 */
	public boolean SaveCO2(EworldCO2 eworldCo2) throws Exception;
	/**
	 * �����۳�(PM2.5)����
	 * @param eworldDust
	 * @return
	 * @throws Exception
	 */
	public boolean SaveDust(EworldDust eworldDust) throws Exception;
	/**
	 * �洢��ȩ����
	 * @param eworldFor
	 * @return
	 * @throws Exception
	 */
	public boolean SaveFor(EworldFormaldehyde eworldFor) throws Exception;
}
