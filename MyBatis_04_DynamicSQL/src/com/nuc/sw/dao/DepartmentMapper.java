package com.nuc.sw.dao;

import org.apache.ibatis.annotations.Param;

import com.nuc.sw.bean.Department;

public interface DepartmentMapper {
	
	public Department getDepartmentById(@Param("id")Integer id);
	
	// 这个方法定义了在查询部门时，将部门所拥有的员工信息以list集合的形式查出
	public Department getDepartmentsById(@Param("id")Integer id);
	
	public Department getDepartmentsByIdStep(@Param("id")Integer id);
}
