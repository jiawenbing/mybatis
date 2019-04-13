package com.nuc.sw.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.nuc.sw.bean.Department;
import com.nuc.sw.bean.Employee;
import com.nuc.sw.dao.EmployeeMapperDynamicSQL;

public class MybatisTest2 {
	// 获取SqlSessionFactory对象
	public SqlSessionFactory getSqlSessionFactory() throws Exception {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory;
	}
	
	@Test
	public void test01() throws Exception {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapperDynamicSQL employeeMapperDynamicSQL = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
			Employee employee = new Employee(null, "%m%", null, null, null);
			List<Employee> list = employeeMapperDynamicSQL.getEmpsConditionIf(employee);
			System.out.println(list);
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void test02() throws Exception {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapperDynamicSQL employeeMapperDynamicSQL = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
			Employee employee = new Employee(null, null, null, null, null);
			List<Employee> list = employeeMapperDynamicSQL.getEmpsConditionChoose(employee);
			System.out.println(list);
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void test03() throws Exception {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapperDynamicSQL employeeMapperDynamicSQL = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
			Employee employee = new Employee(1, "jiawenbing", null, null,null);
			employeeMapperDynamicSQL.updateEmployee(employee);
			sqlSession.commit();
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void test04() throws Exception {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapperDynamicSQL employeeMapperDynamicSQL = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
			/*List<Employee> list = employeeMapperDynamicSQL.getEmpsConditionForEach();
			System.out.println(list);*/
			List<Integer> ids = new ArrayList<Integer>();
			ids.add(1);
			ids.add(2);
			ids.add(3);
			List<Employee> list = employeeMapperDynamicSQL.getEmpsConditionForEach(ids);
			System.out.println(list);
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void test05() throws Exception {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapperDynamicSQL employeeMapperDynamicSQL = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
			List<Employee> emps = new ArrayList<Employee>();
			emps.add(new Employee(null, "ding", "0", "2689119@qq.com", new Department(1)));
			emps.add(new Employee(null, "ding1", "0", "2689119@qq.com", new Department(1)));
			emps.add(new Employee(null, "ding2", "0", "2689119@qq.com", new Department(1)));
			employeeMapperDynamicSQL.insertEmpsConditionForEach(emps);
			sqlSession.commit();
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void test06() throws Exception {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapperDynamicSQL employeeMapperDynamicSQL = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
			Employee employee = new Employee(null, "m", null, null, null);
			List<Employee> emps = employeeMapperDynamicSQL.getEmployeeInnerParameter(employee);
			System.out.println(emps);
		}finally {
			sqlSession.close();
		}
	}
}
