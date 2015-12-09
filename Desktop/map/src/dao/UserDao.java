package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Friend;
import model.User;
import Utils.DBUtils;

public class UserDao {
	private Connection connection=null;
	private PreparedStatement ps=null;
	private ResultSet rs=null;
	public void add(User user) {
		// TODO Auto-generated method stub
		try {
			if (hasRegist(user.getId())) {
				return ;
			}
			connection=DBUtils.getConnection();
			String sql="insert into t_user(id,password,isonline,longitude,latitude) values(?,?,?,?,?)";
			ps=connection.prepareStatement(sql);
			ps.setString(1, user.getId());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getIsonline());
			ps.setString(4, user.getLongitude());
			ps.setString(5, user.getLatitude());
			ps.execute();
			System.out.println(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(ps);
			DBUtils.close(connection);
		}
	}
	public void update(User user){
		try {
		
			connection=DBUtils.getConnection();
			String sql="update t_user set isonline=?,longitude=?,latitude=? where id=?";
			ps=connection.prepareStatement(sql);
			ps.setString(1, user.getIsonline());
			ps.setString(2, user.getLongitude());
			ps.setString(3, user.getLatitude());
			ps.setString(4, user.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			DBUtils.close(ps);
			DBUtils.close(connection);
		}
	}
	public User Loginin(String id,String password){
		User user=null;
		try {
			connection = DBUtils.getConnection();
			String sql="select * from t_user where id=?";
			ps=connection.prepareStatement(sql);
			ps.setString(1, id);
			rs=ps.executeQuery();		
			while (rs.next()) {
				user=new User();
				user.setId(rs.getString("id"));
				user.setIsonline(rs.getString("isonline"));
				user.setPassword(rs.getString("password"));
				user.setLatitude(rs.getString("latitude"));
				user.setLongitude(rs.getString("longitude"));
			}
			if (user==null||!(user.getPassword().equals(password))) {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(rs);
			DBUtils.close(ps);
			DBUtils.close(connection);
		}
		return user;
	}
	
	
	public boolean hasRegist(String id){
		try {
		
			connection = DBUtils.getConnection();
			String sql="select * from t_user where id=?";
			ps=connection.prepareStatement(sql);
			ps.setString(1, id);
			rs=ps.executeQuery();
			if (rs.next()) {	
				return true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
		
	}
	public List<User> getFriends(String id) {
		List<User> friends=new ArrayList<User>();
		User user;
		
		// TODO Auto-generated method stub
		try {
			connection=DBUtils.getConnection();
			String sql="select * from t_user where id in(select friendid from t_friend where id=?)";
			ps=connection.prepareStatement(sql);
			ps.setString(1, id);
			rs=ps.executeQuery();
			while (rs.next()) {
				user=new User();
				user.setId(rs.getString("id"));
				user.setPassword(rs.getString("password"));
				user.setIsonline(rs.getString("isonline"));
				user.setLatitude(rs.getString("latitude"));
				user.setLongitude(rs.getString("longitude"));
				friends.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return friends;
	}
	public List<User> getOnlineFriend(String id) {
		// TODO Auto-generated method stub
		List<User> friends=new ArrayList<User>();
		User user;
		
		// TODO Auto-generated method stub
		try {
			connection=DBUtils.getConnection();
			String sql="select * from t_user where id in(select friendid from t_friend where id=?) and isonline=1";
			ps=connection.prepareStatement(sql);
			ps.setString(1, id);
			rs=ps.executeQuery();
			while (rs.next()) {
				user=new User();
				user.setId(rs.getString("id"));
				user.setPassword(rs.getString("password"));
				user.setIsonline(rs.getString("isonline"));
				user.setLatitude(rs.getString("latitude"));
				user.setLongitude(rs.getString("longitude"));
				friends.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return friends;
	}
	public List<User> getNonlineFriend(String id) {
		// TODO Auto-generated method stub
		List<User> friends=new ArrayList<User>();
		User user;
		
		// TODO Auto-generated method stub
		try {
			connection=DBUtils.getConnection();
			String sql="select * from t_user where id in(select friendid from t_friend where id=?) and isonline=0";
			ps=connection.prepareStatement(sql);
			ps.setString(1, id);
			rs=ps.executeQuery();
			while (rs.next()) {
				user=new User();
				user.setId(rs.getString("id"));
				user.setPassword(rs.getString("password"));
				user.setIsonline(rs.getString("isonline"));
				user.setLatitude(rs.getString("latitude"));
				user.setLongitude(rs.getString("longitude"));
				friends.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return friends;
	}
	
	public void addFriend(String id,String friendid) {
		// TODO Auto-generated method stub
		try {
			connection=DBUtils.getConnection();
			
			String sql="insert into t_message (id,friendid,msg) values(?,?,?)";
			System.out.println(sql);
		
			
			
			ps=connection.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, friendid);
			ps.setString(3, "0");
			ps.execute();
			System.out.println("hhh");
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			DBUtils.close(ps);
			DBUtils.close(connection);
		}
	}
	public List<User> GetMessage(String id) {
		// TODO Auto-generated method stub
		User user;
		List<User> friends=new ArrayList<User>();
		try {
			connection=DBUtils.getConnection();
			String sql="select * from t_user where id in(select id from t_message where friendid=? and msg=0)";
			ps=connection.prepareStatement(sql);
			ps.setString(1, id);
			rs=ps.executeQuery();
			while (rs.next()) {
				user=new User();
				user.setId(rs.getString("id"));
				user.setPassword(rs.getString("password"));
				user.setIsonline(rs.getString("isonline"));
				user.setLatitude(rs.getString("latitude"));
				user.setLongitude(rs.getString("longitude"));
				friends.add(user);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			DBUtils.close(rs);
			DBUtils.close(ps);
			DBUtils.close(connection);
		}
		return friends;
	}
	public void sureFriend(String id, String friendid) {
		// TODO Auto-generated method stub
		try {
			connection=DBUtils.getConnection();
			String sql="insert into t_friend (id,friendid) values (?,?)";
			ps=connection.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, friendid);
			ps.execute();
			String sql1="insert into t_friend(id,friendid) values(?,?)";
			ps=connection.prepareStatement(sql1);
			ps.setString(1, friendid);
			ps.setString(2, id);
			ps.execute();
			String sqlString="delete from t_message where id=? and friendid=?";
			ps=connection.prepareStatement(sqlString);
			ps.setString(1, friendid);
			ps.setString(2, id);
			ps.execute();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			DBUtils.close(ps);
			DBUtils.close(connection);
		}
	}
}
