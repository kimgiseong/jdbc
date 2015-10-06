package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import dbconfig.oracle.OracleConfig;
import vo.MemberVO;
// 메인에서 키보드 입력하여호출 / 메뉴만들기
// 쿼리문 인터페이스로 클래스 만들기
// 삭제 추가하기
// 연결, 해제부문 메소드로 구현 또는 클래스로 구현

/*
 * Statement --> preparedStatement로 변경하여 JDBC 프로그램을 구성
 */
public class MemberDAO{
	/*
	 * Oracle Driver loading... //생성자
	 * @throws ClassNotFoundException
	 */
	public MemberDAO() throws ClassNotFoundException
	{
		Class.forName(OracleConfig.DRIVER);
		System.out.println("driver loading.....");
	}
	
	public void closeAll(ResultSet rs, PreparedStatement pstmt, Connection con) throws SQLException
	{
		//select인 경우
		if(rs != null)
			rs.close();
		if(pstmt != null)
			pstmt.close();
		if(con != null)
			con.close();
	}
	
	public void closeAll(PreparedStatement pstmt, Connection con) throws SQLException
	{
		// select가 아닌경우
		closeAll(null, pstmt, con);
	}
	
	public void insertMember(MemberVO vo) throws SQLException
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		try
		{
			con = DriverManager.getConnection(OracleConfig.URL, OracleConfig.USER, OracleConfig.PASS);
			System.out.println("connection ...");
			String sql = "insert into member(id, password, name, address)"+"values(?,?,?,?)";
			pstmt=con.prepareStatement(sql);
			// 위 부분은 동일
			
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getAddress());
			
			int result = pstmt.executeUpdate();
			
			System.out.println("insert : "+result);
		}
		finally
		{
			closeAll(pstmt, con);
		}
	}
	
	public MemberVO selectById(String id) throws SQLException
	{
		ResultSet rs = null; // resultSet = 결과에 대한 값을 출력할때 사용, select면 resultSet이 들어가야함
		Connection con = null;
		PreparedStatement pstmt = null;
		MemberVO vo = null;
		try
		{
			con = DriverManager.getConnection(OracleConfig.URL, OracleConfig.USER, OracleConfig.PASS);
			System.out.println("connection ...");
			String sql = "select password, name, address from member where id = ?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
				// 아이디는 유일한 값이기 때문에 while문으로 전체를 검색할 필요가 없다.
				// 주소는 같은 장소에 다양한 사람이 살고있으므로 while로 검색해야 한다.
				vo = new MemberVO(id, rs.getString(1), rs.getString(2), rs.getString(3));
		}
		finally
		{
			closeAll(rs, pstmt, con);
		}
		
		return vo;
	}
	
	public ArrayList selectAllMember() throws SQLException
	{
		ResultSet rs = null; // resultSet = 결과에 대한 값을 출력할때 사용, select면 resultSet이 들어가야함
		Connection con = null;
		PreparedStatement pstmt = null;
		MemberVO vo = null;
		ArrayList list = new ArrayList();
		
		try
		{
			con = DriverManager.getConnection(OracleConfig.URL, OracleConfig.USER, OracleConfig.PASS);
			System.out.println("connection ...");
			String sql = "select password, name, address from member";
			pstmt=con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				vo = new MemberVO(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
				list.add(vo);
			}				
		}
		finally
		{
			closeAll(rs, pstmt, con);
		}
		
		return list;
	}
	
	public ArrayList selectByAddress (String addr) throws SQLException
	{
		ResultSet rs = null; // resultSet = 결과에 대한 값을 출력할때 사용, select면 resultSet이 들어가야함
		Connection con = null;
		PreparedStatement pstmt = null;
		MemberVO vo = null;
		ArrayList list = new ArrayList();
		
		try
		{
			con = DriverManager.getConnection(OracleConfig.URL, OracleConfig.USER, OracleConfig.PASS);
			System.out.println("connection ...");
			String sql = "select password, name, address from member where address = ?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, addr);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				vo = new MemberVO(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
				list.add(vo);
			}				
		}
		finally
		{
			closeAll(rs, pstmt, con);
		}
		
		return list;
	}
	
	public void upDateMemberByAddress(String address, String newAddress) throws SQLException
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try
		{
			con = DriverManager.getConnection(OracleConfig.URL, OracleConfig.USER, OracleConfig.PASS);
			System.out.println("connection ...");
			String sql = "update member set address = ? where address = ?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, newAddress);
			pstmt.setString(2, address);
			int result = pstmt.executeUpdate();
		}
		finally
		{
			closeAll(pstmt, con);
		}
	}
}
