package com.Model;

import java.util.Objects;

public class BatchRecordDTO {
	private int batchid;
	private int batchNo;
	private int cId;
	private String sEmail;
	
	public BatchRecordDTO(int batchid, int batchNo, int cId, String sEmail) {
		super();
		this.batchid = batchid;
		this.batchNo = batchNo;
		this.cId = cId;
		this.sEmail = sEmail;
	}

	public int getBatchid() {
		return batchid;
	}

	public void setBatchid(int batchid) {
		this.batchid = batchid;
	}

	public int getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(int batchNo) {
		this.batchNo = batchNo;
	}

	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public String getsEmail() {
		return sEmail;
	}

	public void setsEmail(String sEmail) {
		this.sEmail = sEmail;
	}

	@Override
	public int hashCode() {
		return Objects.hash(batchNo, batchid, cId, sEmail);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BatchRecordDTO other = (BatchRecordDTO) obj;
		return batchNo == other.batchNo && batchid == other.batchid && cId == other.cId
				&& Objects.equals(sEmail, other.sEmail);
	}

	@Override
	public String toString() {
		return "BatchRecordDTO [batchid=" + batchid + ", batchNo=" + batchNo + ", cId=" + cId + ", sEmail=" + sEmail
				+ "]";
	}
	
	
	
	
}
