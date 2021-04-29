package vgc_Mubasher_Zeb_Khan_21694;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppConfig {

	//start variables for database handling
	public static java.sql.Connection con;
	public static PreparedStatement pst;
	public static ResultSet rs;
	//end variables for database handling
	
	/*
	 * This function will be used for connection with database
	 */

	public static void Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/db_vgc_mubasher_zeb_khan_21694?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");

		} catch (ClassNotFoundException | SQLException exe) {
			exe.printStackTrace();
		}
	}
}
