package com.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.util.DBConn;

public class test003
{
	public static void main(String[] args)
	{
		try
		{
			Connection conn = DBConn.getConnection();
			
			if (conn != null)
			{
				System.out.println("데이터베이스 연결 성공~!!!");
				
				try
				{
					String sql = "SELECT SID, NAME, TEL FROM TBL_MEMBER ORDER BY SID";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery();
					
					while (rs.next())
					{
						int sid = rs.getInt("SID");
						String name = rs.getString("NAME");
						String tel = rs.getString("TEL");
						
						String str = String.format("%3d %7s %10s", sid, name, tel);
						
						System.out.println(str);						
					}			
					
				} catch (Exception e)
				{	
					System.out.println(e.toString());
				}
			}
			
			DBConn.close();
			
			System.out.println();
			System.out.println("데이터베이스 연결 닫힘~!!!");
			System.out.println("프로그램 종료됨~!!!");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		} 
		
	}
	
}
