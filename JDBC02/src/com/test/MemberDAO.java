/*==================
  MemberDAO.java
==================*/

// Database 에 Access 하는 기능
// → DBConn 활용

// 데이터를 입력하는 기능 → INSERT

// 인원 수 확인하는 기능
// 대상 테이블(TBL_MEMBER)의 레코드 카운팅 기능 → SELECT

// 전체 리스트 조회하는 기능
// 대상 테이블(TBL_MEMBER)의 데이터를 조회하는 기능 → SELELCT

package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.util.DBConn;

public class MemberDAO // → 쿼리문은 다 여기에 있다!
{
	// 주요 변수 선언 → DB 연결 객체
	private Connection conn;
	
	// 생성자 정의
	public MemberDAO() throws ClassNotFoundException, SQLException
	{
		conn = DBConn.getConnection();
	}
	
	// 메소드 정의 → 데이터 입력하는 기능
	// ▶ conn 객체 활용 → Statement → excuteUpdate() → insert 쿼리 수행  → int 적용된 행의 갯수 반환
	public int add(MemberDTO dto) throws SQLException
	{		
		// 반환할 결과값을 담아낼 변수(적용된 행의 갯수)
		int result = 0;
		
		
		// 작업객체 생성
		Statement stmt = conn.createStatement();
		
		/* 
		   ※ Statement 의 종류
		      - Statement
		        하나의 쿼리를 사용하고 나면 더 이상 사용할 수 없다.
		      - PreparedStatement
		        하나의 PreparedStatement 로 쿼리를 여러 번 처리할 수 있다.
		      - CallableStatement
		        데이터베이스 내의 스토어드 프로시저나 함수 등을 호출할 수 있다.
		        
		   ※ Statement 의 의미
		      자바에서 사용되는 3가지 종류의 작업 객체들은
		      데이터베이스로 쿼리를 담아서 보내는 그릇 정도로 생각하자.
		      즉, 작업 객체에 쿼리를 실어 데이터베이스로 보내버리면
		      그 내용이 데이터베이스에서 처리되는 것이다.
		      이때, 한 번 사용하고 버리는 그릇은 Statement 이며,
		      재사용이 가능한 그릇은 PreparedStatement 이다.
		 */
		
		
		// 쿼리문 준비
		String sql = String.format("INSERT INTO TBL_MEMBER(SID, NAME, TEL)"
								+ " VALUES(MEMBERSEQ.NEXTVAL, '%s', '%s')"
								, dto.getName(), dto.getTel());
		
		
		// 작업 객체를 활용하여 쿼리문 실행(전달)
		result = stmt.executeUpdate(sql);	
		
		
		// 사용한 리소스 반납
		stmt.close();
		
		
		// 최종 결과값 반환
		return result;
		
	}// end add()
	
	
	// 메소드 정의 → 전체 인원 수 확인 기능
	public int count() throws SQLException
	{
		// 결과값으로 반환하게 될 변수 선언 및 초기화
		int result = 0;
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비 → select 
		String sql = "SELECT COUNT(*) AS COUNT FROM TBL_MEMBER";
		
		// 작업 객체를 활용하여 쿼리문 실행(전달) → select → executeQuery() → ResultSet 반환 → 일반적으로 반복 처리
		ResultSet rs = stmt.executeQuery(sql);
		
		// ResultSet 처리 → 반복문 구성 → 결과값 수신
		while (rs.next())
		{
			result = rs.getInt("COUNT");	//	rs.getInt(1);	// ※ 컬럼 인덱스는 1 부터...
		}
		
		// 사용한 리소스 반납
		rs.close();	
		stmt.close();
		
		// 최종 결과값 반환
		return result;
		
	}// end count
	
	
	// 메소드 정의 → 전체 리스트 조회 기능
	public ArrayList<MemberDTO> lists() throws SQLException
	{
		// 결과값으로 반환하게 될 변수 선언 및 초기화
		ArrayList<MemberDTO> result = new ArrayList<MemberDTO>();
		
		// 작업 객체 생성
		Statement stmt = conn.createStatement();
		
		// 쿼리문 준비
		String sql = "SELECT SID, NAME, TEL FROM TBL_MEMBER ORDER BY SID";
		
		// 작업 객체를 활용하여 쿼리문 실행(전달) → select → executeQuery() → ResultSet 반환
		ResultSet rs = stmt.executeQuery(sql);
		
		// ResultSet 처리 → 일반적 반복문 활용 → MemberDTO 인스턴스 생성 → 속성 구성
		while(rs.next())
		{
			MemberDTO dto = new MemberDTO();
			
			dto.setNumber(rs.getString("SID"));
			dto.setName(rs.getString("NAME"));
			dto.setTel(rs.getString("TEL"));
			
			result.add(dto);
		}
		
		// 사용한 리소스 반납
		rs.close();
		stmt.close();
		
		// 최종 결과값 반환
		return result;
		
	} //end lists
	
	
	public void close() throws SQLException
	{
		DBConn.close();
	}
	
}












