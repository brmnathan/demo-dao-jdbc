package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection connection;

	public SellerDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(Seller seller) {
		String sql = "INSERT INTO seller "
				+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
				+ "VALUES "
				+ "(?, ?, ?, ?, ?)";
		
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, seller.getName());
			statement.setString(2, seller.getEmail());
			statement.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			statement.setDouble(4, seller.getBaseSalary());
			statement.setInt(5, seller.getDepartment().getId());
			
			int rowsAffected = statement.executeUpdate();
			
			if(rowsAffected == 0)
				throw new DbException("Error: no rows affected");
			else {
				ResultSet resultSet = statement.getGeneratedKeys();
				if(resultSet.next()) {
					int key = resultSet.getInt(1);
					seller.setId(key);
				}
				DB.closeResultSet(resultSet);
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(statement);
		}

	}

	@Override
	public void update(Seller seller) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {

		String sql = "SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
				+ "ON seller.DepartmentId = department.Id " + "WHERE seller.Id = ? ";

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				Department department = instantiateDepartment(resultSet);
				Seller seller = instantiateSeller(resultSet, department);
				return seller;
			}

			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(statement);
			DB.closeResultSet(resultSet);
		}
	}

	private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
		Department department = new Department();
		department.setId(resultSet.getInt("DepartmentId"));
		department.setName(resultSet.getString("DepName"));

		return department;
	}

	private Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException {
		Seller seller = new Seller();
		seller.setId(resultSet.getInt("Id"));
		seller.setName(resultSet.getString("Name"));
		seller.setEmail(resultSet.getString("Email"));
		seller.setBirthDate(resultSet.getDate("BirthDate"));
		seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
		seller.setDepartment(department);
		new Seller();
		seller.setId(resultSet.getInt("Id"));
		seller.setName(resultSet.getString("Name"));
		seller.setEmail(resultSet.getString("Email"));
		seller.setBirthDate(resultSet.getDate("BirthDate"));
		seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
		seller.setDepartment(department);

		return seller;
	}

	@Override
	public List<Seller> findByDepartment(Department department) {

		String sql = "SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
				+ "ON seller.DepartmentId = department.Id " + "WHERE DepartmentId = ? " + "ORDER BY Name";

		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, department.getId());
			resultSet = statement.executeQuery();

			List<Seller> sellerList = new ArrayList<>();
			Map<Integer, Department> departmentMap = new HashMap<>();

			while (resultSet.next()) {
				Department dep = departmentMap.get(resultSet.getInt("DepartmentId"));

				if (dep == null) {
					dep = instantiateDepartment(resultSet);
					departmentMap.put(resultSet.getInt("DepartmentId"), dep);
				}

				Seller seller = instantiateSeller(resultSet, dep);
				sellerList.add(seller);
			}

			return sellerList;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(statement);
			DB.closeResultSet(resultSet);
		}
	}

	@Override
	public List<Seller> findAll() {

		String sql = "SELECT seller.*,department.Name as DepName "
				+ "FROM seller INNER JOIN department "
				+ "ON seller.DepartmentId = department.Id "
				+ "ORDER BY Name";
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();

			List<Seller> sellerList = new ArrayList<>();
			Map<Integer, Department> departmentMap = new HashMap<>();

			while (resultSet.next()) {
				Department dep = departmentMap.get(resultSet.getInt("DepartmentId"));

				if (dep == null) {
					dep = instantiateDepartment(resultSet);
					departmentMap.put(resultSet.getInt("DepartmentId"), dep);
				}

				Seller seller = instantiateSeller(resultSet, dep);
				sellerList.add(seller);
			}

			return sellerList;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(statement);
			DB.closeResultSet(resultSet);
		}
	}

}
