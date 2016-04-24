package com.allen.dao.dbConnection;

public class DB_Connection {
	String Connection="jdbc:mysql://198.35.47.207/mobileshop?"+
			"user=root&password=allen95533&characterEncoding=gb2312";
	String uri = "jdbc:mysql://198.35.47.207/mobileshop?";
	String user = "user=root&password=allen95533&characterEncoding=gb2312";
	public String getConnection(){
		return Connection;
	}
	public String getConnection_2(){
		return uri+user;
	}
}
