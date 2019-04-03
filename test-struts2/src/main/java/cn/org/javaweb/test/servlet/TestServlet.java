package cn.org.javaweb.test.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Enumeration;

/**
 * Created by yz on 2016-06-16.
 */
public class TestServlet extends HttpServlet {

	public Connection getMysqlConnection() {
		try {
			String url      = "jdbc:mysql://localhost:3306?autoReconnect=true&zeroDateTimeBehavior=round&useUnicode=true&characterEncoding=UTF-8&useOldAliasMetadataBehavior=true&useOldAliasMetadataBehavior=true";
			String username = "root";
			String password = "beijing4";
			Class.forName("com.mysql.jdbc.Driver");

			return DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			return null;
		}
	}

	public Connection getMssqlConnection() {
		try {
			String url      = "jdbc:sqlserver://192.168.1.180:1433;DatabaseName=TRSWCMV65";
			String username = "sa";
			String password = "CcWdhFwKFBGEQ6tGeuyHXadu";
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			return DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			return null;
		}
	}

	public Connection getOracleConnection() {
		try {
			String url      = "jdbc:oracle:thin:@192.168.1.180:1521:orcl";
			String username = "system";
			String password = "LEHnN8EsfsEhbNTtvrxoQqUg";
			Class.forName("oracle.jdbc.driver.OracleDriver");

			return DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			return null;
		}
	}

	public void testMysqlPreparedStatement(Connection connection, PrintWriter out) {
		try {
			String            sql  = "select '/*exec xp_cmdshell(\\'cmd\\')*/'";
			PreparedStatement pstt = connection.prepareStatement(sql);
//			pstt.setObject(1, "root");
			ResultSet rs = pstt.executeQuery();
			out.println("----------------------------------------------------------------------------------");
			out.println("Mysql PreparedStatement: " + sql);

			while (rs.next()) {
				out.println("<font color='red'>" + rs.getObject(1) + "</font>");
			}

			out.println("----------------------------------------------------------------------------------");
			rs.close();
			pstt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void testMssqlPreparedStatement(Connection connection, PrintWriter out) {
		try {
			String sql = "select @@version ";
//			PreparedStatement pstt = connection.prepareStatement(sql);
			Statement statement = connection.createStatement();
//			pstt.setObject(1, "root");
			ResultSet rs = statement.executeQuery(sql);
			out.println("----------------------------------------------------------------------------------");
			out.println("MSSQL PreparedStatement: " + sql);

			while (rs.next()) {
				out.println("<font color='red'>" + rs.getObject(1) + "</font>");
			}

			out.println("----------------------------------------------------------------------------------");
			rs.close();
//			pstt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void testOraclePreparedStatement(Connection connection, PrintWriter out) {
		try {
			String            sql  = "select * from v$version where rownum = ?";
			PreparedStatement pstt = connection.prepareStatement(sql);

			pstt.setObject(1, 1);
			ResultSet rs = pstt.executeQuery();
			out.println("----------------------------------------------------------------------------------");
			out.println("Oracle PreparedStatement: " + sql);

			while (rs.next()) {
				out.println("<font color='red'>" + rs.getObject(1) + "</font>");
			}

			out.println("----------------------------------------------------------------------------------");
			rs.close();
			pstt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");

			System.out.println(request.getParameter("id"));
			Connection mysqlConnection  = getMysqlConnection();
			Connection mssqlConnection  = getMssqlConnection();
			Connection oracleConnection = getOracleConnection();

			PrintWriter out = response.getWriter();
			out.println("<pre>");
			out.println(request.getParameter("id"));

			testMysqlPreparedStatement(mysqlConnection, out);
			testMssqlPreparedStatement(mssqlConnection, out);
			testOraclePreparedStatement(oracleConnection, out);

			mysqlConnection.close();
			mssqlConnection.close();
			oracleConnection.close();

			Enumeration<Driver> em = DriverManager.getDrivers();
			while (em.hasMoreElements()) {
				Driver driver = em.nextElement();
				System.out.println(driver.getClass().getName());
			}

			out.println("</pre>");
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
