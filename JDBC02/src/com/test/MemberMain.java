/*====================
  MemberMain.java
====================*/

/*
○ TBL_MEMBER 테이블을 활용하여
   이름과 전화번호를 여러 건 입력 받고, 전체 출력하는 프로그램을 구현한다.
   단, 데이터베이스 연동이 이루어져야 하고
   MmeberDAO, MemberDTO 클래스를 활용해야 한다.

실행 예)
이름 전화번호 입력(1): 이찬호 010-1111-1111
>> 회원 정보 입력 완료~!!!
이름 전화번호 입력(2): 박혜진 010-2222-2222
>> 회원 정보 입력 완료~!!!
이름 전화번호 입력(3): 윤유동 010-3333-3333
>> 회원 정보 입력 완료~!!!
이름 전화번호 입력(4): .

------------------------------
전체 회원 수 : 3명
------------------------------
번호	이름	전화번호
1		이찬호	010-1111-1111
2		박혜진	010-2222-2222
3		윤유동	010-3333-3333
------------------------------
*/

package com.test;

import java.util.Scanner;

import com.util.DBConn;

public class MemberMain
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			MemberDAO dao = new MemberDAO();
			
			int count = dao.count();
			
			do
			{
				System.out.printf("이름 전화번호 입력(%d): ", (++count));
				String name = sc.next();
				
				// 반복의 조건을 무너뜨리는 코드 구성
				if (name.equals("."))
					break;
				
				String tel = sc.next();
				
				
				// MemberDTO 객체 구성
				MemberDTO dto = new MemberDTO();
				dto.setName(name);
				dto.setTel(tel);
				// SID 는 시퀀스로 자동입력 되기 때문에 해당 값이 없어도 된다.
				
				
				// 데이터베이스에 데이터를 입력하는 메소드 호출 → add()
				int result = dao.add(dto);
				
				if (result > 0)
					System.out.println(">> 회원 정보 입력 완료~!!!");
				
			} while (true);					
			

			
			/*
			------------------------------
			전체 회원 수 : 3명
			------------------------------
			번호	이름	전화번호
			1		이찬호	010-1111-1111
			2		박혜진	010-2222-2222
			3		윤유동	010-3333-3333
			------------------------------
			*/
			
			System.out.println("------------------------------");
			System.out.printf("전체 회원 수: %d명\n", dao.count());
			System.out.println("------------------------------");
			System.out.println("번호	이름	전화번호");
			
			// 리스트 가져와 출력
			for (MemberDTO obj : dao.lists())
			{
				System.out.printf("%3s %7s %12s\n"
						, obj.getNumber(), obj.getName(), obj.getTel());
			}

			System.out.println("------------------------------");
			
			
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		finally
		{
			try
			{
				DBConn.close();
				sc.close();
				System.out.println("데이터베이스 연결 닫힘~!!!");
				System.out.println("프로그램 종료됨~!!!");
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
	}
	
}

/*
이름 전화번호 입력(2): 이찬호 010-2222-2222
>> 회원 정보 입력 완료~!!!
이름 전화번호 입력(3): 박혜진 010-3333-3333
>> 회원 정보 입력 완료~!!!
이름 전화번호 입력(4): 윤유동 010-4444-4444
>> 회원 정보 입력 완료~!!!
이름 전화번호 입력(5): .
------------------------------
전체 회원 수: 4명
------------------------------
번호	이름	전화번호
  1     김진희 010-1111-1111
  2     이찬호 010-2222-2222
  3     박혜진 010-3333-3333
  4     윤유동 010-4444-4444
------------------------------
데이터베이스 연결 닫힘~!!!
프로그램 종료됨~!!!
*/
