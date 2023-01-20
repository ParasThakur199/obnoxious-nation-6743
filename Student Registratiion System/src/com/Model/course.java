package com.Model;

import java.util.Objects;

public class course {
	private int cid;
	private String cname;
	private int fee;
	private String cInfo;
	
	public course() {
		
	}
	
	public course(int cid, String cname, int fee, String cInfo) {
		super();
		this.cid = cid;
		this.cname = cname;
		this.fee = fee;
		this.cInfo = cInfo;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public String getcInfo() {
		return cInfo;
	}

	public void setcInfo(String cInfo) {
		this.cInfo = cInfo;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(cInfo, cid, cname, fee);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		course other = (course) obj;
		return Objects.equals(cInfo, other.cInfo) && cid == other.cid && Objects.equals(cname, other.cname)
				&& fee == other.fee;
	}

	@Override
	public String toString() {
		return "course [cid=" + cid + ", cname=" + cname + ", fee=" + fee + ", cInfo=" + cInfo + "]";
	}
	
}
