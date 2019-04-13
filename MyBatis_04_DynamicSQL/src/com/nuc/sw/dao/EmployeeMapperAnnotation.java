package com.nuc.sw.dao;

import org.apache.ibatis.annotations.Select;

import com.nuc.sw.bean.Employee;

public interface EmployeeMapperAnnotation {
	@Select("select * from tbl_employee where id = #{id}")
	public Employee getEmployeeById(Integer id);
}
