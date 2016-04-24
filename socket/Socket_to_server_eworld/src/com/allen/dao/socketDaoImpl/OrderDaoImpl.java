package com.allen.dao.socketDaoImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.allen.dao.dbConnection.DB_Connection;
import com.allen.dao.socketDao.OrderDao;
import com.allen.model.OrderEworld;

public class OrderDaoImpl implements OrderDao{

	
	
	
	
	
	String SensorTable_name   = "Sensor";
	String OrderTable_name    = "OrderTable";
	String FacilityTable_name = "Facility";
	String UserTable_name     = "User";
	String FacStateTable_name    = "HaveAccess";
	String ColName = "(DeviceName,DeviceNumber,Type,DataValue,Battery,ReceiveTime)";
	
	
	
	private static String colname_OrderNumber = "OrderNumber";
	private static String colname_FacNum = "OrderFnumber";
	private static String colname_OrderPerform = "OrderPerform";
	
	
	
	
	
	
	
	
	@Override
	public OrderEworld GiveOrder() throws Exception {
		OrderEworld ordermodel = new OrderEworld();
		//String condition = "update "+FacStateTable_name+"set Online = 'ON' where Fnumber = '"+fstate.geteId()+"'";
		String condition = "select * from"+OrderTable_name+"where "+colname_OrderPerform+"='N' limit 0,1";
		ordermodel = selectTheTable(condition);
		if(ordermodel == null)
		{
			System.out.print("the Order Fail beacuse ordermodel == null");
		}
		return ordermodel;
	}

	@Override
	public boolean ChangeOrderState() throws Exception {
		
		return false;
	}
	
	
	/**
	 * 连接mysql和执行sql语句
	 * @param condition
	 * @return
	 */
	public static OrderEworld selectTheTable(String condition){
		
		OrderEworld ordermodel = new OrderEworld();//new一个order模型，用于存储从数据库里面读出来的数据
		
		Connection con;
		Statement sql;
		//////////////////////////////////////
		ResultSet rs;
		//////////////////////////////////////
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DB_Connection DB_C = new DB_Connection();;
		String uri = DB_C.getConnection_2();

		try{
			con = DriverManager.getConnection(uri);
			sql = con.createStatement();
			////////////////////////////////////////////
			rs = sql.executeQuery(condition);
			while(rs.next()){
				//获取设别号
				String facStr = rs.getString(colname_FacNum);
					int facInt = Integer.valueOf(facStr, 16);
					ordermodel.setFacNumber(facInt);
				//获取命令的
				String OrderNumber = rs.getString(colname_OrderNumber);
					byte order = Byte.valueOf(OrderNumber);
					ordermodel.setOrder(order);
				}
			////////////////////////////////////////////
			con.close();
		}catch(SQLException exp){
			String backNews=""+exp;
			System.out.println(backNews);
			return null;
		}
		return ordermodel;
	}


}
