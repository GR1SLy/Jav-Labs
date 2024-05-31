package com.lab.lib.control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import com.lab.lib.employee.*;

public class DBController {
    private static Connection $connection;
    private static final String URL = "jdbc:postgresql://localhost:5433/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1234";

    private static final String SQL_DEV_INSERT = "INSERT INTO devs (id, x, y, birthTime) VALUES (?, ?, ?, ?)";
    private static final String SQL_MAN_INSERT = "INSERT INTO mans (id, x, y, birthTime) VALUES (?, ?, ?, ?)";
    private static final String SQL_DEV_SELECT = "SELECT * FROM devs";
    private static final String SQL_MAN_SELECT = "SELECT * FROM mans";

    public DBController() {
        super();
    }

    public void connect() throws SQLException {
       $connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
       System.err.println("Connected to DB");
    }

    public void disconnect() throws SQLException {
        $connection.close();
        System.err.println("Disconnected from DB");
    }

    public void saveData(LinkedList<Employee> employees) throws SQLException {
        PreparedStatement devStatement = $connection.prepareStatement(SQL_DEV_INSERT);
        PreparedStatement manStatement = $connection.prepareStatement(SQL_MAN_INSERT);
        Statement statement = $connection.createStatement();
        statement.execute("DELETE FROM devs");
        statement.execute("DELETE FROM mans");
        for (Employee employee : employees) {
            if (employee instanceof Developer) saveEmp(employee, devStatement);
            else saveEmp(employee, manStatement);
        }
        System.err.println("Data saved successfully");
    }

    private void saveEmp(Employee emp, PreparedStatement statement) throws SQLException {
        statement.setInt(1, emp.getID());
        statement.setInt(2, emp.getX());
        statement.setInt(3, emp.getY());
        statement.setInt(4, emp.getBirthTime());
        statement.executeUpdate();
    }

    public LinkedList<Employee> loadData(Habitat hbt) throws SQLException {
        Statement statement = $connection.createStatement();
        ResultSet devResultSet = statement.executeQuery(SQL_DEV_SELECT);

        LinkedList<Employee> employees = new LinkedList<>();
        while (devResultSet.next()) {
            int id = devResultSet.getInt(1);
            int x = devResultSet.getInt(2);
            int y = devResultSet.getInt(3);
            int birthTime = devResultSet.getInt(4);
            Employee dev = new Developer(hbt.getGraphBounds().width, hbt.getGraphBounds().height, birthTime, id, x, y);
            employees.add(dev);
            Developer.incCount();
            Employee.incCount();
        }

        ResultSet manResultSet = statement.executeQuery(SQL_MAN_SELECT);
        while (manResultSet.next()) {
            int id = manResultSet.getInt(1);
            int x = manResultSet.getInt(2);
            int y = manResultSet.getInt(3);
            int birthTime = manResultSet.getInt(4);
            Employee man = new Manager(hbt.getGraphBounds().width, hbt.getGraphBounds().height, birthTime, id, x, y);
            employees.add(man);
            Manager.incCount();
            Employee.incCount();
        }
        System.err.println("Data loaded successfully");
        return employees;
    }


}

/**
 * TABLE
 * | id | x | y | birthTime | 
 * |    |   |   |           |
 */