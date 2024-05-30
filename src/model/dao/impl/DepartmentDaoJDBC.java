package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {
	
	private Connection connection;
	

	public DepartmentDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(Department department) {
		String sql = "INSERT INTO department " 
				+ "(Name) " 
				+ "VALUES "
				+ "(?)";

		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, department.getName());

			int rowsAffected = statement.executeUpdate();

			if (rowsAffected == 0)
				throw new DbException("Error: no rows affected");
			else {
				ResultSet resultSet = statement.getGeneratedKeys();
				if (resultSet.next()) {
					int key = resultSet.getInt(1);
					department.setId(key);
				}
				DB.closeResultSet(resultSet);
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(statement);
		}

	}

	@Override
	public void update(Department department) {
		String sql = "UPDATE department " 
				+ "SET Name = ? "
				+ "WHERE Id = ? ";
		
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, department.getName());
			statement.setInt(2, department.getId());

			statement.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(statement);
		}
	}

	@Override
	public void deleteById(Integer id) {
		String sql = "DELETE FROM department " 
				+ "WHERE Id = ? ";
		
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);

			int rowsAffected = statement.executeUpdate();
			
			if(rowsAffected == 0) 
				throw new DbException("Error: Id not found!");

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(statement);
		}

	}

	@Override
	public Department findById(Integer id) {
		String sql = "SELECT * " 
				+ "FROM department "
				+ "WHERE Id = ? ";

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				Department department = new Department();
				department.setId(resultSet.getInt("Id"));
				department.setName(resultSet.getString("Name"));
				return department;
			}

			return null;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(statement);
			DB.closeResultSet(resultSet);
		}
	}

	@Override
	public List<Department> findAll() {
		String sql = "SELECT * " 
				+ "FROM department "
				+ "ORDER BY Name";

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();

			List<Department> departmentList = new ArrayList<>();

			while (resultSet.next()) {
				Department department = new Department();
				department.setId(resultSet.getInt("Id"));
				department.setName(resultSet.getString("Name"));
				departmentList.add(department);
			}
			
			return departmentList;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(statement);
			DB.closeResultSet(resultSet);
		}
	}

}
