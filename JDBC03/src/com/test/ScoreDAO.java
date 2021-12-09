package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.util.DBConn;

public class ScoreDAO
{
	// 주요 변수 선언
	private Connection conn;
	
	public ScoreDAO()
	{
		conn = DBConn.getConnection();
	}
	
	// 데이터베이스 연결 담당 메소드
	public Connection connection() throws ClassNotFoundException, SQLException
	{
		conn = DBConn.getConnection();
		return conn;
	}
	
	// 메소드①: 데이터 입력
	public int add(ScoreDTO dto) throws SQLException
	{
		// 반환할 값을 담을 변수 선언
		int result = 0;
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비
		String sql = String.format("INSERT INTO TBL_SCORE(SID, NAME, KOR, ENG, MAT) VALUES(SCORESEQ.NEXTVAL, '%s', %d, %d, %d)"
								, dto.getName(), dto.getKor(), dto.getEng(), dto.getMat());
		
		// 작업 객체를 활용하여 쿼리문 실행(전달)
		result = stmt.executeUpdate(sql);
		
		// 사용한 리소스 반납
		stmt.close();
		
		// 최종 결과값 반환		
		return result;
	}
	
	// 메소드②: 인원 수 확인
	public int count() throws SQLException
	{
		// 반환할 값을 담을 변수 선언
		int result = 0;
		
		// 작업객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비
		String sql = "SELECT COUNT(*) AS COUNT FROM TBL_SCORE";
		
		// 작업객체를 활용하여 쿼리문 실행(전달)
		ResultSet rs = stmt.executeQuery(sql);
		
		// ResultSet 처리
		while(rs.next())
		{
			result = rs.getInt("COUNT");
		}
		
		// 사용한 리소스 반납
		rs.close();
		stmt.close();
		
		// 최종 결과값 반환
		return result;
	}
	
	
	// 메소드③: 전체 데이터 확인
	public ArrayList<ScoreDTO> lists() throws SQLException 
	{
		// 결과값으로 반환할 변수 선언 및 초기화
		ArrayList<ScoreDTO> result = new ArrayList<ScoreDTO>();
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비
		String sql = "SELECT SID, NAME, KOR, ENG, MAT FROM TBL_SCORE ORDER BY SID";
		
		// 작업 객체를 활용하여 쿼리문 실행(전달)
		ResultSet rs = stmt.executeQuery(sql);
		
		// ResultSet 처리
		while (rs.next())
		{
			ScoreDTO dto = new ScoreDTO();
			
			dto.setSid(rs.getString("SID"));
			dto.setName(rs.getString("NAME"));
			dto.setKor(rs.getInt("KOR"));
			dto.setEng(rs.getInt("ENG"));
			dto.setMat(rs.getInt("MAT"));
			
			result.add(dto);
		}
		
		// 사용한 리소스 반납
		rs.close();
		stmt.close();
				
		// 최종 결과값 반환
		return result;
	}

	
	// 데이터베이스 연결 종료 메소드
	public void close()
	{
		DBConn.close();
	}
}
