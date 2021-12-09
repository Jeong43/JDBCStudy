/*==============================================
	MemverProcess.java
	- 콘솔 기반 서브 메뉴 입출력 전용 클래스 
==============================================*/

package com.test;

import java.util.ArrayList;
import java.util.Scanner;

public class MemberProcess
{
	// 주요 속성 구성
	private MemberDAO dao;
		
	// 생성자 정의
	public MemberProcess()
	{
		dao = new MemberDAO();
	}
	
	
	// 직원 정보 입력 메소드 정의
	public void memberInsert()
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			// 데이터베이스 연결
			dao.connection();
			
			// 지역 리스트 구성
			ArrayList<String> citys = dao.searchCity();
			StringBuilder cityStr = new StringBuilder();
			for (String city : citys)
				cityStr.append(city + "/");

			// 부서 리스트 구성
			ArrayList<String> buseos = dao.searchBuseo();
			StringBuilder buseoStr = new StringBuilder();
			for (String buseo : buseos)
				buseoStr.append(buseo + "/");

			// 직위 리스트 구성
			ArrayList<String> jikwis = dao.searchJikwi();
			StringBuilder jikwiStr = new StringBuilder();
			for (String jikwi : jikwis)
				jikwiStr.append(jikwi + "/");
			
			
			// 사용자에게 안내 메시지 출력
			System.out.println();
			System.out.println("직원 정보 입력 ------------------------------------");
			System.out.print("이름: ");
			String empName = sc.next();
			
			System.out.print("주민등록번호(yymmdd-nnnnnnn): ");
			String ssn = sc.next();
			
			System.out.print("입사일(yyyy-mm-dd): ");
			String ibsaDate = sc.next();
			
			System.out.printf("지역(%s): ", cityStr.toString());		
			String cityName = sc.next();
			
			System.out.print("전화번호: ");
			String tel = sc.next();

			System.out.printf("부서(%s): ", buseoStr.toString());
			String buseoName = sc.next();		
					
			System.out.printf("직위(%s): ", jikwiStr.toString());
			String jikwiName = sc.next();
			
			System.out.printf("기본급(최소 %,d원 이상): ", dao.searchBasicPay(jikwiName));
			int basicPay = sc.nextInt();
			
			System.out.print("수당: ");
			int sudang = sc.nextInt();
			
			System.out.println();
			
			
			// MemberDTO 구성
			MemberDTO dto = new MemberDTO();
			dto.setEmpName(empName);
			dto.setSsn(ssn);
			dto.setIbsaDate(ibsaDate);
			dto.setCityName(cityName);
			dto.setTel(tel);
			dto.setBuseoName(buseoName);
			dto.setJikwiName(jikwiName);
			dto.setBasicPay(basicPay);
			dto.setSudang(sudang);
			
			int result = dao.add(dto);
			if(result > 0)
				System.out.println("직원 정보 입력 완료~!!!");
			else 
				System.out.println("ERROR: 직원정보 입력에 실패했습니다.");
			
			System.out.println("------------------------------------ 직원 정보 입력");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				dao.close();
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
				
	}// end memberInsert()
	
	
	// 직원 전체 출력 메소드 정의
	public void memberLists()
	{
		Scanner sc = new Scanner(System.in);
		
		// 서브 메뉴 출력
		System.out.println("");
		System.out.println("1. 사번 정렬");				// EMP_ID
		System.out.println("2. 이름 정렬");				// EMP_NAME
		System.out.println("3. 부서 정렬");				// BUSEO_NAME
		System.out.println("4. 직위 정렬");				// JIKWI_NAME
		System.out.println("5. 급여 내림차순 정렬");	// PAY DESC
		System.out.print(">> 선택(1~5, -1 종료): ");
		String menuStr = sc.next();
		
		try
		{
			int menu = Integer.parseInt(menuStr);
			if (menu == -1)
				return;
			
			String key = "";
			switch (menu)
			{
				case 1: 
					key = "EMP_ID"; 
					break;
				case 2: 
					key = "EMP_NAME"; 
					break;
				case 3: 
					key = "BUSEO_NAME"; 
					break;
				case 4: 
					key = "JIKWI_NAME"; 
					break;
				case 5: 
					key = "PAY DESC"; 
					break;
				}
			
			
			// 데이터베이스 연결
			dao.connection();
			
			// 직원 리스트 출력
			System.out.println();
			System.out.printf("전체 인원: %d명\n", dao.memberCount());
			System.out.println(" 사번    이름      주민번호       입사일      지역    전화번호      부서   직위    기본급        수당       급여");
			
			ArrayList<MemberDTO> memList = dao.lists(key);
			for (MemberDTO dto : memList)
			{
				System.out.printf("%5d  %4s  %14s  %10s  %4s  %13s  %3s  %2s  %,10d  %,10d  %,10d\n"
						, dto.getEmpId(), dto.getEmpName(), dto.getSsn(), dto.getIbsaDate()
						, dto.getCityName(), dto.getTel(), dto.getBuseoName(), dto.getJikwiName()
						, dto.getBasicPay(), dto.getSudang(), dto.getPay());
			}
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		} 
		finally
		{
			try
			{
				dao.close();
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
				
			}
			
		}
		
	}//end memberLists();
	
	
	// 직원 검색 출력 메소드 정의
	public void memberSearch()
	{
		// 인스턴스 생성
		Scanner sc = new Scanner(System.in);
		
		
		// 정렬 유형 선택 → 1:사번, 2:이름, 3:부서, 4:직위
		System.out.println();
		System.out.println("1. 사번 검색");
		System.out.println("2. 이름 검색");
		System.out.println("3. 부서 검색");
		System.out.println("4. 직위 검색");
		System.out.print(">> 선택(1~4, -1 종료): ");
		String menuStr = sc.next();
		
		try
		{
			int menu = Integer.parseInt(menuStr);
			if (menu == -1)
				return;
			
			
			// 사용자로부터 키와 값 입력 받기
			String key = "";
			String value = "";
			
			switch (menu)
			{
				case 1: 
					System.out.print("검색할 사번을 입력하세요: ");
					key = "EMP_ID";
					break;
				case 2: 
					System.out.print("검색할 이름을 입력하세요: ");
					key = "EMP_NAME"; 
					break;
				case 3: 
					System.out.print("검색할 부서를 입력하세요: ");
					key = "BUSEO_NAME"; 
					break;
				case 4: 
					System.out.print("검색할 직위를 입력하세요: ");
					key = "JIKWI_NAME"; 
					break;
			}
			
			value = sc.next();
			
			
			
			// 데이터베이스 연결
			dao.connection();
			
			// 직원 리스트 출력
			System.out.println();
			System.out.println(" 사번    이름      주민번호       입사일      지역    전화번호      부서   직위    기본급        수당       급여");
			
			ArrayList<MemberDTO> memList = dao.searchLists(key, value);
			for (MemberDTO dto : memList)
			{
				System.out.printf("%5d  %4s  %14s  %10s  %4s  %13s  %3s  %2s  %,10d  %,10d  %,10d\n"
						, dto.getEmpId(), dto.getEmpName(), dto.getSsn(), dto.getIbsaDate()
						, dto.getCityName(), dto.getTel(), dto.getBuseoName(), dto.getJikwiName()
						, dto.getBasicPay(), dto.getSudang(), dto.getPay());
			}
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally 
		{
			try
			{
				dao.close();
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
	
	}//end memberSearch()
	
	
	// 직원 정보 수정 메소드 정의
	public void memberUpdate()
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			// 수정할 대상 입력받기
			System.out.print("수정할 직원의 사원번호 입력: ");
			String value = sc.next();
			
			// 데이터베이스 연결
			dao.connection();
			
			ArrayList<MemberDTO> members = dao.searchLists("EMP_ID", value);
			
			if (members.size() > 0)
			{				
				// 지역 리스트 구성
				ArrayList<String> citys = dao.searchCity();
				StringBuilder cityStr = new StringBuilder();
				for (String city : citys)
					cityStr.append(city + "/");

				// 부서 리스트 구성
				ArrayList<String> buseos = dao.searchBuseo();
				StringBuilder buseoStr = new StringBuilder();
				for (String buseo : buseos)
					buseoStr.append(buseo + "/");

				// 직위 리스트 구성
				ArrayList<String> jikwis = dao.searchJikwi();
				StringBuilder jikwiStr = new StringBuilder();
				for (String jikwi : jikwis)
					jikwiStr.append(jikwi + "/");
				
				
				MemberDTO mMember = members.get(0);
				int mEmpId = mMember.getEmpId();
				String mEmpName = mMember.getEmpName();
				String mSsn = mMember.getSsn();
				String mIbsaDate = mMember.getIbsaDate();
				String mCityName = mMember.getCityName();
				String mTel = mMember.getTel();
				String mBuseoName = mMember.getBuseoName();
				String mJikwiName = mMember.getJikwiName();
				int mBasicPay = mMember.getBasicPay();
				int mSudang = mMember.getSudang();
												
				
				// 직원 정보 수정
				System.out.println();
				System.out.println("직원 정보 수정 ------------------------------------");
				System.out.printf("기존 이름: %s\n", mEmpName);
				System.out.print("수정 이름: ");
				String empName = sc.next();
				if (empName.equals("-"))
					empName = mEmpName;
				
				System.out.printf("기존 주민등록번호(yymmdd-nnnnnnn): %s\n", mSsn);
				System.out.print("수정 주민등록번호(yymmdd-nnnnnnn): ");
				String ssn = sc.next();
				if (ssn.equals("-"))
					ssn = mSsn;
				
				System.out.printf("기존 입사일(yyyy-mm-dd): %s\n", mIbsaDate);
				System.out.print("수정 입사일(yyyy-mm-dd): ");
				String ibsaDate = sc.next();
				if (ibsaDate.equals("-"))
					ibsaDate = mIbsaDate;
				
				System.out.printf("기존 지역: %s\n", mCityName);
				System.out.printf("수정 지역(%s): ", cityStr.toString());
				String cityName = sc.next();
				if (cityName.equals("-"))
					cityName = mCityName;
				
				System.out.printf("기존 전화번호: %s\n", mTel);
				System.out.print("수정 전화번호: ");
				String tel = sc.next();
				if (tel.equals("-"))
					tel = mTel;
				
				System.out.printf("기존 부서: %s\n", mBuseoName);
				System.out.printf("수정 부서(%s): ", buseoStr.toString());
				String buseoName = sc.next();
				if (buseoName.equals("-"))
					buseoName = mBuseoName;
				
				System.out.printf("기존 직위: %s\n", mJikwiName);
				System.out.printf("수정 부서(%s): ", jikwiStr.toString());
				String jikwiName = sc.next();
				if (jikwiName.equals("-"))
					jikwiName = mJikwiName;
				
				System.out.printf("기존 기본급: %d\n", mBasicPay);
				System.out.printf("수정 기본급(최소 %,d원 이상): ", dao.searchBasicPay(jikwiName));
				String basicPayStr = sc.next();
				int basicPay = 0;
				if (basicPayStr.equals("-"))
					basicPay = mBasicPay;
				else
					basicPay = Integer.parseInt(basicPayStr);
				
				System.out.printf("기존 수당 %d\n", mSudang);
				System.out.print("수정 수당: ");
				String sudangStr = sc.next();
				int sudang = 0;
				if (sudangStr.equals("-"))
					sudang = mSudang;
				else
					sudang = Integer.parseInt(sudangStr);			
				
				
				// MemberDTO 구성
				MemberDTO dto = new MemberDTO();
				dto.setEmpId(mEmpId);
				dto.setEmpName(empName);
				dto.setSsn(ssn);
				dto.setIbsaDate(ibsaDate);
				dto.setCityName(cityName);
				dto.setTel(tel);
				dto.setBuseoName(buseoName);
				dto.setJikwiName(jikwiName);
				dto.setBasicPay(basicPay);
				dto.setSudang(sudang);				

				
				// 회원 정보 수정 메소드 실행
				int result = dao.modify(dto);
				
				if (result > 0)
					System.out.println("직원 정보 수정 완료~!!!");
				
				System.out.println("------------------------------------ 직원 정보 수정");
				
			}
			else
			{
				System.out.println("수정 대상을 검색하지 못했습니다.");
			}
				
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		finally
		{
			try
			{
				dao.close();
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
	}//end memberUpdate()
	
	
	// 직원 정보 삭제 메소드 정의
	public void memberDelete()
	{
		Scanner sc = new Scanner(System.in);
		
		try
		{
			System.out.print("삭제할 직원의 사원번호 입력: ");
			String value = sc.next();
			
			// 직원 정보 확인 후 삭제 여부 결정
			dao.connection();
			ArrayList<MemberDTO> members = dao.searchLists("EMP_ID", value);
			
			if (members.size() > 0)
			{
				System.out.println();
				System.out.println(" 사번    이름      주민번호       입사일      지역    전화번호      부서   직위    기본급        수당       급여");
				
				for (MemberDTO dto : members)
				{
					System.out.printf("%5d  %4s  %14s  %10s  %4s  %13s  %3s  %2s  %,10d  %,10d  %,10d\n"
							, dto.getEmpId(), dto.getEmpName(), dto.getSsn(), dto.getIbsaDate()
							, dto.getCityName(), dto.getTel(), dto.getBuseoName(), dto.getJikwiName()
							, dto.getBasicPay(), dto.getSudang(), dto.getPay());
				}
				
				System.out.print("\n정말 삭제하시겠습니까(Y/N) : ");
				String response = sc.next();
				if (response.equals("Y") || response.equals("y"))
				{
					int result = dao.remove(Integer.parseInt(value));
					if (result > 0)
						System.out.println("직원 정보가 정상적으로 삭제되었습니다.");
				}
				
			}
			else
			{
				System.out.println("삭제 대상을 찾지 못했습니다.");
			}
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
			
		} finally
		{
			try
			{
				dao.close();
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
		}
		
	
	}//end memberDelete()
	
	
}
