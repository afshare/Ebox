package com.allen.dao.socketDaoImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.allen.dao.dbConnection.DB_Connection;
import com.allen.dao.socketDao.FacilityStateDAO;
import com.allen.model.FacilityEworldState;

public class FacilityStateDAOImpl implements FacilityStateDAO{

	
	
	
	
	
	String SensorTable_name   = "Sensor";
	String OrderTable_name    = "OrderTable";
	String FacilityTable_name = "Facility";
	String UserTable_name     = "User";
	String FacStateTable_name    = "HaveAccess";
	String ColName = "(DeviceName,DeviceNumber,Type,DataValue,Battery,ReceiveTime)";
	
	
	@Override
	public boolean ChangeToOnline(FacilityEworldState fstate) throws Exception {
		String condition = "update "+FacStateTable_name+"set Online = 'ON' where Fnumber = '"+fstate.geteId()+"'";				
		return UpdateTheTable(condition);
	}

	@Override
	public boolean ChangeRecentTime(FacilityEworldState fstate)
			throws Exception {
		
		java.util.Date datetime = new java.util.Date(System.currentTimeMillis());	
		java.sql.Timestamp timeNow = new java.sql.Timestamp(datetime.getTime());
		System.out.println(timeNow);
		String condition = "update "+FacStateTable_name+"set RecentTime = '"+timeNow+"' where Fnumber = '"+fstate.geteId()+"'";				
		return UpdateTheTable(condition);
		
	}
	
	@Override
	public boolean ChangeToOffline(FacilityEworldState fstate) throws Exception {
		java.util.Date datetime = new java.util.Date(System.currentTimeMillis());	
		java.sql.Timestamp timeNow = new java.sql.Timestamp(datetime.getTime());
		System.out.println(timeNow);
		String condition = "update "+FacStateTable_name+"set Online = 'OFF' where Fnumber = '"+fstate.geteId()+"'";				
		return UpdateTheTable(condition);
	}

	@Override
	public boolean AddFacWithOnline(FacilityEworldState fstate)
			throws Exception {
		
		return false;
	}

	@Override
	public boolean DeleteFacOnline(FacilityEworldState fstate) throws Exception {
		java.util.Date datetime = new java.util.Date(System.currentTimeMillis());	
		java.sql.Timestamp timeNow = new java.sql.Timestamp(datetime.getTime());
		System.out.println(timeNow);
		String condition = "update "+FacStateTable_name+"set Online = 'OFF' where Fnumber = '"+fstate.geteId()+"'";				
		return UpdateTheTable(condition);
	}
	@Override
	public boolean SelectFac(FacilityEworldState fstate) throws Exception {
		
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 连接mysql和执行sql语句
	 * @param condition
	 * @return
	 */
	public static boolean UpdateTheTable(String condition){
		Connection con;
		Statement sql;
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
			sql.executeUpdate(condition);
			con.close();
		}catch(SQLException exp){
			String backNews=""+exp;
			System.out.println(backNews);
			return false;
		}
		return true;
	}





}
