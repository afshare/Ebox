package com.allen.dao.socketDaoImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.allen.dao.dbConnection.DB_Connection;
import com.allen.dao.socketDao.FacilityStateDAO;
import com.allen.model.FacilityEworldState;


public class FacilityStateDAOImpl implements FacilityStateDAO{

	
	
	
	
	


	String FacStateTable_name    = "HaveAccess";
	String tableHaveAccessColName = "(Fnumber,Online,RecentTime)";
	
	private static String colname_FNumber = "FNumber";
	
	
	
	@Override
	public boolean ChangeToOnline(FacilityEworldState fstate) throws Exception {
		String condition = "update "+FacStateTable_name+" set Online = 'ON' where Fnumber = '"+fstate.geteId()+"'";				
		return UpdateTheTable(condition);
	}

	@Override
	public boolean ChangeRecentTime(FacilityEworldState fstate)
			throws Exception {
		
		java.util.Date datetime = new java.util.Date(System.currentTimeMillis());	
		java.sql.Timestamp timeNow = new java.sql.Timestamp(datetime.getTime());
		System.out.println(timeNow);
		String condition = "update "+FacStateTable_name+" set RecentTime = '"+timeNow+"' where Fnumber = '"+fstate.geteId()+"'";				
		return UpdateTheTable(condition);
		
	}
	
	@Override
	public boolean ChangeToOffline(FacilityEworldState fstate) throws Exception {
		java.util.Date datetime = new java.util.Date(System.currentTimeMillis());	
		java.sql.Timestamp timeNow = new java.sql.Timestamp(datetime.getTime());
		System.out.println(timeNow);
		String condition = "update "+FacStateTable_name+" set Online = 'OFF' where Fnumber = '"+fstate.geteId()+"'";				
		return UpdateTheTable(condition);
	}

	@Override
	public boolean AddFacWithOnline(FacilityEworldState fstate)
			throws Exception {
		java.util.Date datetime = new java.util.Date(System.currentTimeMillis());	
		java.sql.Timestamp timeNow = new java.sql.Timestamp(datetime.getTime());
		System.out.println(timeNow);
/*		String condition = "Insert into "+FacStateTable_name+tableHaveAccessColName+" values('"+
				fstate.geteId()+
				"','ON'"+
				"','"+timeNow+
				"')";	*/	
		String condition  = "Insert into HaveAccess(Fnumber,Online,RecentTime) values('"+fstate.geteId()+"'"+",'NO','"+timeNow+"')";
		return InsertIntoMysql(condition);
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
		String condition = "select * from "+FacStateTable_name+" where "+colname_FNumber+"='"+fstate.geteId()+"'";
		return selectTheTable(condition);
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
	
	public static boolean InsertIntoMysql(String condition){
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


	
	public static boolean selectTheTable(String condition){
		

		
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
			boolean theRs = rs.first(); 
			con.close();
			return theRs;
/*			while(rs.next()){
				//获取设备号
				String facStr = rs.getString(colname_FNumber);
					int facInt = Integer.valueOf(facStr, 16);
					ordermodel.setFacNumber(facInt);
				}*/
			////////////////////////////////////////////
			
		}catch(SQLException exp){
			String backNews=""+exp;
			System.out.println(backNews);
			return false;
		}
	
	}



}
