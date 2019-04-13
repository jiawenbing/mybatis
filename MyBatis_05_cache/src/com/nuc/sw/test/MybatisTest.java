package com.nuc.sw.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.nuc.sw.bean.Department;
import com.nuc.sw.bean.Employee;
import com.nuc.sw.dao.DepartmentMapper;
import com.nuc.sw.dao.EmployeeMapper;
import com.nuc.sw.dao.EmployeeMapperAnnotation;
import com.nuc.sw.dao.EmployeeMapperPlus;

public class MybatisTest {

	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory;
	}
	
	@Test
	
	/* 两级缓存：
	 * 	一级缓存（本地缓存）
	 * 		与数据库同一次会话期间查询到的数据会放在本地缓存中,以后如果要获取相同的数据，直接从本地缓存中获取
	 * 		一级缓存失效情况：（没有使用到当前一级缓存的情况，效果就是还需要向数据库发出SQL请求）	
	 * 			1.sqlSession不同
	 * 			2.sqlSession相同，查询条件不同。（当前以及缓存中还没有这个数据）
	 * 			3.sqlSession相同，两次查询之间执行了增删改操作（这次增删改可能对当前数据有影响）
	 * 			4.sqlSession相同，手动清除了一级缓存（缓存清空）
	 * 二级缓存（全局缓存）
	 * 		
	 * */
	
	//一级缓存
	public void test01() throws Exception {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 得到一次会话
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
			Employee employee1 = employeeMapper.getEmployeeById(1);
			System.out.println(employee1);
			
			// 清空一级缓存
			sqlSession.clearCache();
			Employee employee2 = employeeMapper.getEmployeeById(1);
			System.out.println(employee2);
			
			System.out.println(employee1==employee2);
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void test02() throws Exception {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession1 = sqlSessionFactory.openSession();
		SqlSession sqlSession2 = sqlSessionFactory.openSession();
		try{
			EmployeeMapper employeeMapper1 = sqlSession1.getMapper(EmployeeMapper.class);
			EmployeeMapper employeeMapper2 = sqlSession2.getMapper(EmployeeMapper.class);
			Employee employee1 = employeeMapper1.getEmployeeById(1);
			System.out.println(employee1);
			// 只有会话关闭，查询的数据才提交到namespace为EmployeeMapper的二级缓存中
			sqlSession1.close();
			
			// 第二次查询是从二级缓存中获取的数据，并没有发送新的sql
			Employee employee2 = employeeMapper2.getEmployeeById(1);
			System.out.println(employee2);
			sqlSession2.close();
			
			System.out.println(employee1==employee2);
		}finally {
		}
	}
}
