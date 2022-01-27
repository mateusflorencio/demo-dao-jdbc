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

public class SellerDaoJDBC implements SellerDao{
	
	private Connection conn;
	
	public SellerDaoJDBC (Connection conn) {
		this.conn=conn;
	}

	@Override
	public void insert(Seller obj) {
		PreparedStatement statement=null;
		try {
			statement=conn.prepareStatement(
					"INSERT INTO seller "
					+"(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+"(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, obj.getName());
			statement.setString(2, obj.getEmail());
			statement.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			statement.setDouble(4, obj.getBaseSalary());
			statement.setInt(5, obj.getDepartment().getId());
			
			int rowsAffected= statement.executeUpdate();
			
			if (rowsAffected>0) {
				ResultSet rSet= statement.getGeneratedKeys();
				
				if (rSet.next()) {
					int id=rSet.getInt(1);
					obj.setId(id);
				}
			DB.closeResultSet(rSet);
			}
			else {
				throw new DbException("unexpected error! No rows affected");
			}
			
		} catch (SQLException e) {
				throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(statement);
		}
	}

	@Override
	public void update(Seller obj) {
		PreparedStatement statement=null;
		try {
			statement=conn.prepareStatement(
					"UPDATE seller "
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "WHERE Id = ?");
			
			statement.setString(1, obj.getName());
			statement.setString(2, obj.getEmail());
			statement.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			statement.setDouble(4, obj.getBaseSalary());
			statement.setInt(5, obj.getDepartment().getId());
			statement.setInt(6, obj.getId());
			
			statement.executeUpdate();
		} 
		catch (SQLException e) {
				throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(statement);
		}
		
	}

	@Override
	public void delete(Seller id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st=null;
		ResultSet rs=null;
		try {
			st= conn.prepareStatement("SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+"ON seller.DepartmentId = department.Id "
					+"WHERE seller.Id = ?");
			
			st.setInt(1, id);
			rs=st.executeQuery();
			
			if (rs.next()) {
				Department department= instantiateDepartment(rs);
				Seller seller= instantiateSeller(rs, department);
				return seller;
				
			}
			return null;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Seller instantiateSeller(ResultSet rs, Department department) throws SQLException {
		Seller seller=new Seller();
		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("Name"));
		seller.setEmail(rs.getString("Email"));
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setBirthDate(rs.getDate("BirthDate"));
		seller.setDepartment(department);
		return seller;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		
		Department department= new Department();
		department.setId(rs.getInt("DepartmentId"));
		department.setName(rs.getString("DepName"));
		return department;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st=null;
		ResultSet rs=null;
		
		try {
			st= conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name");
			
			rs=st.executeQuery();
			
			List<Seller> list=new ArrayList<>();
			Map<Integer, Department> map=new HashMap<>();
			
			while (rs.next()) {
				
				Department dep= map.get(rs.getInt("DepartmentId"));
				
				if(dep==null) {
					dep=instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller seller= instantiateSeller(rs, dep);
				list.add(seller);				
			}
			return list;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		
		PreparedStatement st=null;
		ResultSet rs=null;
		
		try {
			st= conn.prepareStatement("SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name");
			
			st.setInt(1, department.getId());
			
			rs=st.executeQuery();
			
			List<Seller> list=new ArrayList<>();
			Map<Integer, Department> map=new HashMap<>();
			
			while (rs.next()) {
				
				Department dep= map.get(rs.getInt("DepartmentId"));
				
				if(dep==null) {
					dep=instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller seller= instantiateSeller(rs, dep);
				list.add(seller);				
			}
			return list;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
}
