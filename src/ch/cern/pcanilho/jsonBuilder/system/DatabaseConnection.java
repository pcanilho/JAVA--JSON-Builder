package ch.cern.pcanilho.jsonBuilder.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

import ch.cern.pcanilho.jsonBuilder.arch.DB_Column;
import ch.cern.pcanilho.jsonBuilder.arch.DB_JS_Manager;
import ch.cern.pcanilho.jsonBuilder.arch.DB_JS_Object;

public abstract class DatabaseConnection {
	private static Connection connection = null;

	public static boolean connectDatabase(Map<String, String> params) {
		if (params == null)
			return false;

		// GET PARAMS
		String userName = params.get("username");
		String password = params.get("password");

		String server = params.get("server");
		String database = params.get("database");

		// URL
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.print("Connecting to Database: " + database + " ...");
			connection = DriverManager.getConnection("jdbc:sqlserver://" + server + ";user=" + userName + ";password=" + password + ";database=" + database);
			System.out.print(" SUCCESS!\n");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean closeCurrentDBConnection() {
		try {
			if (connection != null && !connection.isClosed())
				connection.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void execQueryOnCurrentDB(String query) {
		System.out.println("Executing query...\n");

		try {
			if (connection == null || connection.isClosed())
				return;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		int line = 0;

		DB_JS_Manager data_manager = new DB_JS_Manager();
		try {
			java.sql.Statement st = connection.createStatement();
			ResultSet result = st.executeQuery(query);
			ResultSetMetaData metaData = result.getMetaData();
			String[] column_names = getColumnNamesFromDB(metaData);

			while (result.next()) {
				DB_Column column = new DB_Column();
				column.setName(column_names[1]);
				column.setValue(result.getString(column_names[1]));

				DB_JS_Object object = new DB_JS_Object();
				object.setValue(column);
				object.setUnitName(result.getString(column_names[0]));
				data_manager.addChildren(object);

				// EXTRA
				line++;
			}

			data_manager.printJSON();
			FileHandler.saveJSONFile(data_manager);

		} catch (SQLException e) {
			System.err.println("ERROR ON LINE: " + line);
			e.printStackTrace();
		}

	}

	private static String[] getColumnNamesFromDB(ResultSetMetaData metaData) {
		try {
			String[] column_names = new String[metaData.getColumnCount()];
			for (int i = 0; i < column_names.length; i++)
				column_names[i] = metaData.getColumnName(i + 1);

			return column_names;
		} catch (SQLException e) {
			System.err.println(Class.class.getName() + " -> getColumnNamesFromDB: " + e.getMessage());
			e.printStackTrace();
		}

		return null;
	}
}
