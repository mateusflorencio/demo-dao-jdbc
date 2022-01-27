package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection conn;

	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement("INSERT INTO department " + "(Id, name) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			statement.setInt(1, obj.getId());
			statement.setString(2, obj.getName());

			int rowsAfeccted = statement.executeUpdate();

			ResultSet resultSet = statement.getGeneratedKeys();

			if (resultSet.next()) {
				int id = resultSet.getInt(1);
				obj.setId(id);

			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(statement);
		}
	}

	@Override
	public void update(Department obj) {
		PreparedStatement statement = null;

		try {
			statement=conn.prepareStatement("UPDATE department SET Name=? WHERE Id=?");
			statement.setString(1, obj.getName());
			statement.setInt(2, obj.getId());
			
			statement.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		
		finally {
			DB.closeStatement(statement);
		}
		

	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM department where Id=?");

			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				Department department = instantiateDepartment(rs);
				return department;

			}
			return null;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());

		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department department = new Department();
		department.setId(rs.getInt("Id"));
		department.setName(rs.getString("Name"));
		return department;
	}
	

	@Override
	public List<Department> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}