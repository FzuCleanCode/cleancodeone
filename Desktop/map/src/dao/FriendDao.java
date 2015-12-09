package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Friend;
import Utils.DBUtils;

public class FriendDao {
	private Connection connection=null;
	private PreparedStatement ps=null;
	public void add(Friend friend) {
		// TODO Auto-generated method stub
		try {
			connection=DBUtils.getConnection();
			String sql="insert into t_friend(id,friendid) values (?,?)";
			ps=connection.prepareStatement(sql);
			ps.setString(1, friend.getId());
			ps.setString(2, friend.getFriendid());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(ps);
			DBUtils.close(connection);
		}
	}
	public List<Friend> GetFriends(String id){
		List<Friend> friends=new ArrayList<Friend>();
		ResultSet rs=null;
		try {
			connection = DBUtils.getConnection();
			String sql="select * from t_friend where id=?";
			ps=connection.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()){
				Friend friend=new Friend();
				friend.setId(rs.getString(rs.getInt("id")));
				friend.setFriendid(rs.getString(rs.getInt("friendid")));
				friends.add(friend);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtils.close(rs);
			DBUtils.close(ps);
			DBUtils.close(connection);
		}
		return friends;
			
	}
}
