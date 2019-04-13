package com.nuc.sw.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuc.sw.bean.Employee;

public interface EmployeeMapperDynamicSQL {
	
	public List<Employee> getEmpsConditionIf(Employee employee);
	
	public List<Employee> getEmpsConditionTrim(Employee employee);
	
	public List<Employee> getEmpsConditionChoose(Employee employee);
	
	public void updateEmployee(Employee employee);
	
	public List<Employee> getEmpsConditionForEach(@Param("ids")List<Integer> ids);
	
	// ≈˙¡ø≤Â»Î
	public void insertEmpsConditionForEach(@Param("emps")List<Employee> emps);
	
	public List<Employee> getEmployeeInnerParameter(Employee employee);
	
}
