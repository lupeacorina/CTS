package ro.ase.acs;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ro.ase.acs.contracts.Operation;

public class SqlOperation implements Closeable, Operation {
	private Connection connection;

	public SqlOperation() {
		try {
			Class.forName("org.sqlite.JDBC");
			this.connection = DriverManager.getConnection("jdbc:sqlite:database.db");
			this.connection.setAutoCommit(false);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void create() {
		String sqlDrop = "DROP TABLE IF EXISTS employees";
		String sqlCreate = "CREATE TABLE employees(id INTEGER PRIMARY KEY," + "name TEXT, address TEXT, salary REAL)";

		Statement statement;
		try {
			statement = this.connection.createStatement();
			statement.executeUpdate(sqlDrop);
			statement.executeUpdate(sqlCreate);
			statement.close();
			this.connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert() {
		String sqlInsert = "INSERT INTO employees VALUES(1, 'Popescu Ion', 'Bucharest', 4000)";
		Statement statement;
		try {
			statement = this.connection.createStatement();
			statement.executeUpdate(sqlInsert);
			statement.close();

			String sqlInsertWithParams = "INSERT INTO employees VALUES (?,?,?,?)";
			PreparedStatement preparedStatement = this.connection.prepareStatement(sqlInsertWithParams);
			preparedStatement.setInt(1, 2);
			preparedStatement.setString(2, "Ionescu Vasile");
			preparedStatement.setString(3, "Brasov");
			preparedStatement.setDouble(4, 4500);
			preparedStatement.executeUpdate();
			preparedStatement.close();

			this.connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void read() {
		String sqlSelect = "SELECT * FROM employees";
		Statement statement;
		try {
			statement = this.connection.createStatement();
			ResultSet rs = statement.executeQuery(sqlSelect);
			while (rs.next()) {
				int id = rs.getInt("id");
				System.out.println("id: " + id);
				String name = rs.getString(2);
				System.out.println("name: " + name);
				String address = rs.getString("address");
				System.out.println("address: " + address);
				double salary = rs.getDouble("salary");
				System.out.println("salary: " + salary);
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
