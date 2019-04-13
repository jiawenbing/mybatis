package com.nuc.sw.bean;

import java.io.Serializable;
import java.util.List;

public class Department implements Serializable{
	private Integer id;
	private String deptname;
	
	// 这个当中定义了该部门拥有员工信息
	private List<Employee> emps;
	public List<Employee> getEmps() {
		return emps;
	}
	public void setEmps(List<Employee> emps) {
		this.emps = emps;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	@Override
	public String toString() {
		return "Department [id=" + id + ", Deptname=" + deptname + ", emps=" + emps + "]";
	}
	public Department(Integer id, String deptname) {
		super();
		this.id = id;
		this.deptname = deptname;
	}
	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Department(Integer id) {
		super();
		this.id = id;
	}
	
}
