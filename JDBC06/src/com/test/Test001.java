/*===========================
      Test001.java
   - 쿼리문 전송 실습
=============================*/

package com.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import com.util.DBConn;

public class Test001
{
   public static void main(String[] args)
   {
      try
      {
         Connection conn = DBConn.getConnection();
         
         if(conn != null)
         {
            System.out.println("데이터베이스 연결 성공~!!!");
            
            try
            {
            	
               /*
               Statement stmt = conn.createStatement();
               
               String sql = "INSERT INTO TBL_MEMBER(SID, NAME, TEL)"
                 		+ " VALUES(MEMBERSEQ.NEXTVAL, '정효진', '010-6666-6666')";
                 		
               int result = stmt.executeUpdate(sql);
               
               if(result>0)
                  System.out.println("데이터 입력 성공~!!!");
                  
               stmt.close();
               DBConn.close();
               */
               
               // preparedStatement 는 위의 구조로는 못 만든다!
               // 쿼리문 준비 → 작업 객체 생성순서 변경!
               // 양파를 먼저 깎으나 당근을 먼저 깎으나 재료 손질은 똑같아서 실행 잘 됨!!
            	
               String sql = "INSERT INTO TBL_MEMBER(SID, NAME, TEL)"
            		   + " VALUES(MEMBERSEQ.NEXTVAL, ?, ?)";

               PreparedStatement pstmt = conn.prepareStatement(sql);
               
               // IN 매개변수 넘겨주기
               pstmt.setString(1, "박효빈");
               pstmt.setString(2, "010-8888-8888");
               
               
               //System.out.println(sql);
               //--==>> INSERT INTO TBL_MEMBER(SID, NAME, TEL) VALUES(MEMBERSEQ.NEXTVAL, ?, ?)
               
               int result = pstmt.executeUpdate();
               
               if(result>0)
                  System.out.println("데이터 입력 성공~!!!");
               
               pstmt.close();
               DBConn.close();
               
               
            } catch (Exception e)
            {
               System.out.println(e.toString());
            }
               
         }
         
      } catch (Exception e)
      {
         System.out.println(e.toString());
      }
   }
}