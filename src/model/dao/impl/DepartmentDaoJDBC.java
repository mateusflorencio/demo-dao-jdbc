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
import model.dao.DepartmentDao;
import model.entities.Department;

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
		PreparedStatement statement=null;
		
		try {
			statement=conn.prepareStatement(
					"DELETE FROM department WHERE Id =? "
					);
			statement.setInt(1, id);
			statement.executeUpdate();
					
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(statement);
		}
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
		List<Department> list=new ArrayList<>();
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		Map<Integer, Department> map = new HashMap<>();
		
		try {
			
			statement=conn.prepareStatement("SELECT * FROM department ORDER BY Name");
			resultSet=statement.executeQuery();
			
			while (resultSet.next()) {
				Department department = new Department();
				department.setId(resultSet.getInt("Id"));
				department.setName(resultSet.getString("Name"));
				list.add(department);
				
			}
			return list;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		
		finally {
			DB.closeResultSet(resultSet);
			DB.closeStatement(statement);
		}
		
	}

}
