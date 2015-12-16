package com.baofeng.mobile.bean;

import java.io.Serializable;

public class Status<T> implements Serializable {

	public static final int STATUS_200 = 200;

	private int status;

	private int error_no;
	private String error_msg;

	private T data;

	public boolean is200() {
		return STATUS_200 == status;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getError_no() {
		return error_no;
	}

	public void setError_no(int error_no) {
		this.error_no = error_no;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	@Override
	public String toString() {
		return "StatusBean [status=" + status + ", error_no=" + error_no + ", error_msg=" + error_msg + ", data=" + data + "]";
	}
}
