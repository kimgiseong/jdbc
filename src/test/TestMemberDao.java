package test;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

import dbconfig.oracle.*;
import vo.MemberVO;
import service.MemberDAO;

public class TestMemberDao {
	public static void main(String args[]) throws SQLException
	{
		MemberDAO dao;
		
		try
		{
			dao.insertMember(vo);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			dao = new MemberDAO();
			try
			{
				ArrayList list = dao.selectAllMember(); for (int i = 0; i < list.size(); i++) {
					System.out.println(list.get(i));
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			dao.upDateMemberByAddress("판교", "야탑");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}
