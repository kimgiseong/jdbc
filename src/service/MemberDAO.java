package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import dbconfig.oracle.OracleConfig;
import vo.MemberVO;
// ���ο��� Ű���� �Է��Ͽ�ȣ�� / �޴������
// ������ �������̽��� Ŭ���� �����
// ���� �߰��ϱ�
// ����, �����ι� �޼ҵ�� ���� �Ǵ� Ŭ������ ����

/*
 * Statement --> preparedStatement�� �����Ͽ� JDBC ���α׷��� ����
 */
public class MemberDAO{
	/*
	 * Oracle Driver loading... //������
	 * @throws ClassNotFoundException
	 */
	public MemberDAO() throws ClassNotFoundException
	{
		Class.forName(OracleConfig.DRIVER);
		System.out.println("driver loading.....");
	}
	
	public void closeAll(ResultSet rs, PreparedStatement pstmt, Connection con) throws SQLException
	{
		//select�� ���
		if(rs != null)
			rs.close();
		if(pstmt != null)
			pstmt.close();
		if(con != null)
			con.close();
	}
	
	public void closeAll(PreparedStatement pstmt, Connection con) throws SQLException
	{
		// select�� �ƴѰ��
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
			// �� �κ��� ����
			
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
		ResultSet rs = null; // resultSet = ����� ���� ���� ����Ҷ� ���, select�� resultSet�� ������
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
				// ���̵�� ������ ���̱� ������ while������ ��ü�� �˻��� �ʿ䰡 ����.
				// �ּҴ� ���� ��ҿ� �پ��� ����� ��������Ƿ� while�� �˻��ؾ� �Ѵ�.
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
		ResultSet rs = null; // resultSet = ����� ���� ���� ����Ҷ� ���, select�� resultSet�� ������
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
		ResultSet rs = null; // resultSet = ����� ���� ���� ����Ҷ� ���, select�� resultSet�� ������
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
