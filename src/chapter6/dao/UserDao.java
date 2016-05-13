package chapter6.dao;

import static chapter6.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import chapter6.beans.User;
import chapter6.exception.NoRowsUpdatedRuntimeException;
import chapter6.exception.SQLRuntimeException;

public class UserDao {

	public User getUser(Connection connection, String accountOrEmail, String password){

		PreparedStatement ps = null;
		try{
			String sql = "SELECT * FROM user WHERE (account = ? OR email = ?) AND password = ?";

			ps = connection.prepareStatement(sql);

			ps.setString(1, accountOrEmail);
			ps.setString(2, accountOrEmail);
			ps.setString(3, password);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if(userList.isEmpty() == true){
				return null;
			}else if(2 <= userList.size()){
				throw new IllegalStateException ("2 <= userList.size()");
			}else{
				return userList.get(0);
			}
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

//	public boolean checkAccount(Connection connection, String account){
//
//		PreparedStatement ps = null;
//		try{
//			String sql = "SELECT * FROM user ?";
//
//			ps = connection.prepareStatement(sql);
//
//			ResultSet rs = ps.executeQuery();
//			List<String> stockList = new ArrayList<>();
//			while (rs.next()) {
//				String checkAccount = rs.getString("account");
//				stockList.add(account);
//			}
//
//			System.out.println("stockList : " + stockList);
//
//
//			return true;
//		}catch (SQLException e){
//			throw new SQLRuntimeException(e);
//		}finally{
//			close(ps);
//		}
//	}

	private List<User> toUserList(ResultSet rs) throws SQLException {

		List<User> ret = new ArrayList<>();
		try{
			while (rs.next()){
				int id = rs.getInt("id");
				String account = rs.getString("account");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String description = rs.getString("description");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				Timestamp updateDate = rs.getTimestamp("update_date");

				User user = new User();
				user.setId(id);
				user.setAccount(account);
				user.setName(name);
				user.setEmail(email);
				user.setPassword(password);
				user.setDescription(description);
				user.setInsertDate(insertDate);
				user.setUpdateDate(updateDate);

				ret.add(user);
			}
			return ret;
		}finally{
			close(rs);
		}
	}

	public void insert(Connection connection, User user){

		PreparedStatement ps = null;

		try{
			StringBuilder sql = new StringBuilder();

			sql.append("INSERT INTO user( ");
			sql.append(" account");
			sql.append(", name");
			sql.append(", email");
			sql.append(", password");
			sql.append(", description");
			sql.append(", insert_date");
			sql.append(", update_date");
			sql.append(") VALUES (");
			sql.append(" ?");//account
			sql.append(", ?");//name
			sql.append(", ?");//email
			sql.append(", ?");//password
			sql.append(", ?");//description
			sql.append(", NOW()");//insert_date
			sql.append(", NOW()");//update_date
			sql.append(");");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getAccount());
			ps.setString(2, user.getName());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPassword());
			ps.setString(5, user.getDescription());
			ps.executeUpdate();
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	public void update(Connection connection, User user){

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE `twitter`.`user` SET");
			sql.append(" `account` = ?");
			sql.append(", `name` = ?");
			sql.append(", `email` = ?");
			sql.append(", `password` = ?");
			sql.append(", `description` = ?");
			sql.append(", `update_date` = CURRENT_TIMESTAMP");
			sql.append(" WHERE");
			sql.append(" `id` = ?");
			sql.append(" AND");
			sql.append(" `update_date` = ? ;");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getAccount());
			ps.setString(2, user.getName());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPassword());
			ps.setString(5, user.getDescription());
			ps.setInt(6, user.getId());
			ps.setTimestamp(7, new Timestamp(user.getUpdateDate().getTime()));

			int count = ps.executeUpdate();
			if(count == 0){
				throw new NoRowsUpdatedRuntimeException();
			}
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	public User getUser(Connection connection, int id){

		PreparedStatement ps = null;
		try{
			String sql = "SELECT * FROM user WHERE id = ?";

			ps = connection.prepareStatement(sql);
			ps.setInt(1,  id);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if(userList.isEmpty() == true){
				return null;
			}else if(2 <= userList.size()){
				throw new IllegalStateException("2 <= userList.size()");
			}else{
				return userList.get(0);
			}
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

}
