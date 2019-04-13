package com.nuc.sw.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.nuc.sw.bean.Employee;

public interface EmployeeMapper {
	
	// 返回多条记录javabean对象并封装成map key是记录的主键  value是记录封装成的对象
	@MapKey("id") //告诉mybatis封装这个map的时候使用哪个属性作为key
	public Map<Integer, Object> getEmployeeByLastNameReturnMap(String lastName);
	
	//返回单条记录的map key是字段名  value是字段值
	public Map<String, Object> getEmployeeByIdReturnMap(Integer id);
	
	// 返回值为list
	public List<Employee> getEmployeesByLastNameLike(String lastName);
	
//	如果传入的多个参数不是javabean数据模型的属性，就可以把多个参数写成一个map传进来
	public Employee getEmployeeByMap(Map<String, Object> map);
	
	public Employee getEmployeeByIdAndLastName(@Param("id")Integer id, @Param("lastName")String lastName);
	
	
	
	
	
	
	public Employee getEmployeeById(Integer id);
	
	public long addEmployee(Employee employee);
	
	public boolean deleteEmployeeById(Integer id);
	
	public Integer updateEmployee(Employee employee);
	
}
