package vo;

public class MemberVO {
	private String id;
	private String name;
	private String password;
	private String addr;
	
	public MemberVO(String id, String name, String addr, String password) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.addr = addr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return addr;
	}

	public void setAddress(String addr) {
		this.addr = addr;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String toString()
	{
		return "MemberVO [id="+ id +", adderess =" + addr + ", name =" + name + ", password =" + password +"]"; 
	}
}
