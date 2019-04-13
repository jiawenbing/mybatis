package com.nuc.sw.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuc.sw.bean.Employee;

public interface EmployeeMapperPlus {
	public Employee getEmployeeById(@Param("id") Integer id);
	
	public Employee getEmpAndDeptById(@Param("id")Integer id);
	
	public Employee getEmployeeByIdStep(@Param("id")Integer id);
	
	public List<Employee> getEmployeesByDept_IdStep(@Param("dept_id")Integer dept_id);
}
