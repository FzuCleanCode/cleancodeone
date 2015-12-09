package model;

public class Friend {
	private String id;
	private String friendid;
	@Override
	public String toString() {
		return "Friend [id=" + id + ", friendid=" + friendid + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFriendid() {
		return friendid;
	}
	public void setFriendid(String friendid) {
		this.friendid = friendid;
	}
	
}
