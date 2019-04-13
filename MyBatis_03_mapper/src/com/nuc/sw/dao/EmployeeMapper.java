package com.nuc.sw.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.nuc.sw.bean.Employee;

public interface EmployeeMapper {
	
	// ���ض�����¼javabean���󲢷�װ��map key�Ǽ�¼������  value�Ǽ�¼��װ�ɵĶ���
	@MapKey("id") //����mybatis��װ���map��ʱ��ʹ���ĸ�������Ϊkey
	public Map<Integer, Object> getEmployeeByLastNameReturnMap(String lastName);
	
	//���ص�����¼��map key���ֶ���  value���ֶ�ֵ
	public Map<String, Object> getEmployeeByIdReturnMap(Integer id);
	
	// ����ֵΪlist
	public List<Employee> getEmployeesByLastNameLike(String lastName);
	
//	�������Ķ����������javabean����ģ�͵����ԣ��Ϳ��԰Ѷ������д��һ��map������
	public Employee getEmployeeByMap(Map<String, Object> map);
	
	public Employee getEmployeeByIdAndLastName(@Param("id")Integer id, @Param("lastName")String lastName);
	
	
	
	
	
	
	public Employee getEmployeeById(Integer id);
	
	public long addEmployee(Employee employee);
	
	public boolean deleteEmployeeById(Integer id);
	
	public Integer updateEmployee(Employee employee);
	
}
