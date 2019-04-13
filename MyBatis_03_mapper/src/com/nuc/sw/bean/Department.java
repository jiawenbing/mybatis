package com.nuc.sw.bean;

import java.util.List;

public class Department {
	private Integer id;
	private String Deptname;
	
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
		return Deptname;
	}
	public void setDeptname(String deptname) {
		Deptname = deptname;
	}
	@Override
	public String toString() {
		return "Department [id=" + id + ", Deptname=" + Deptname + ", emps=" + emps + "]";
	}
	
	
}
