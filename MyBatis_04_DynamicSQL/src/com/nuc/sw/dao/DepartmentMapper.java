package com.nuc.sw.dao;

import org.apache.ibatis.annotations.Param;

import com.nuc.sw.bean.Department;

public interface DepartmentMapper {
	
	public Department getDepartmentById(@Param("id")Integer id);
	
	// ��������������ڲ�ѯ����ʱ����������ӵ�е�Ա����Ϣ��list���ϵ���ʽ���
	public Department getDepartmentsById(@Param("id")Integer id);
	
	public Department getDepartmentsByIdStep(@Param("id")Integer id);
}
