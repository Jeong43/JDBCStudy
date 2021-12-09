/*==================
   ScoreMain.java
==================*/

/*
○ 성적 처리 프로그램 구현 → 데이터베이스 연동 → ScoreDAO, ScoreDTO 클래스 활용 

   여러 명의 이름, 국어점수, 영어점수, 수학점수를 입력 받아
   총점, 평균을 연산하여 내용을 출력하는 프로그램을 구현한다.
   출력 시 번호(이름, 총점 등) 오름차순 정렬하여 출력한다.
   
   
실행 예)

1번 학생 성적 입력(이름 국어 영어 수학): 김진령 80 75 60
2번 학생 성적 입력(이름 국어 영어 수학): 이윤서 100 90 80 
3번 학생 성적 입력(이름 국어 영어 수학): 송해덕 80 85 80
4번 학생 성적 입력(이름 국어 영어 수학): .

--------------------------------------------------------
번호	이름	국어	영어	수학	총점	평균
--------------------------------------------------------
  1    김진령	80		75		60		xxx		xx.xx
  2    이윤서	100		90		80		xxx		xx.xx
  3    송해덕	80		85		80		xxx		xx.xx
--------------------------------------------------------  
 */


package com.test;

import java.sql.SQLException;
import java.util.Scanner;

public class ScoreMain
{
	public static void main(String[] args) throws SQLException
	{
		// 인스턴스 생성
		Scanner sc = new Scanner(System.in);
		ScoreDAO dao = new ScoreDAO();
		
		
		// ① 성적 입력 받기
		do
		{
			// 안내 메시지 출력
			int count = dao.count();
			System.out.printf("%d번 학생 성적 입력(이름 국어 영어 수학): ", ++count);
			
			
			// 사용자가 입력한 값 받기
			String name = sc.next();
			
			if (name.equals("."))
				break;
			
			int kor = sc.nextInt();
			int eng = sc.nextInt();
			int mat = sc.nextInt();
			
			
			// 입력받은 값으로 ScoreDTO 객체 구성
			ScoreDTO dto = new ScoreDTO();
			dto.setName(name);
			dto.setKor(kor);
			dto.setEng(eng);
			dto.setMat(mat);
			
			
			// ScoreDTO 객체를 DB에 입력
			dao.add(dto);
			
		} while(true);
		
		
		// ② 전체 성적 출력
		System.out.println("----------------------------------------------");
		System.out.println("번호  이름    국어   영어  수학   총점  평균");
		System.out.println("----------------------------------------------");
		
		for (ScoreDTO obj : dao.lists())
			System.out.printf("%3s %5s %5d %5d %5d %6d %6.2f\n"
					, obj.getSid(), obj.getName(), obj.getKor(), obj.getEng(), obj.getMat(), obj.getTot(), obj.getAvg() );
		
		System.out.println("----------------------------------------------");
		
		
		// 리소스 반환
		dao.close();
		sc.close();
		
		System.out.println("데이터베이스 연결 닫힘~!!!");
		System.out.println("프로그램 종료~!!!");				
	}
}

/*
1번 학생 성적 입력(이름 국어 영어 수학): 김진령 80 75 60
2번 학생 성적 입력(이름 국어 영어 수학): 이윤서 100 90 80 
3번 학생 성적 입력(이름 국어 영어 수학): 송해덕 80 85 80
4번 학생 성적 입력(이름 국어 영어 수학): .
----------------------------------------------
번호  이름    국어   영어  수학   총점  평균
----------------------------------------------
  1   김진령    80    75    60    215  71.67
  2   이윤서   100    90    80    270  90.00
  3   송해덕    80    85    80    245  81.67
----------------------------------------------
데이터베이스 연결 닫힘~!!!
프로그램 종료~!!!
*/
