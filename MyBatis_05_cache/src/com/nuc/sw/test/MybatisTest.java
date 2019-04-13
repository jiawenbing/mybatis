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
	
	/* �������棺
	 * 	һ�����棨���ػ��棩
	 * 		�����ݿ�ͬһ�λỰ�ڼ��ѯ�������ݻ���ڱ��ػ�����,�Ժ����Ҫ��ȡ��ͬ�����ݣ�ֱ�Ӵӱ��ػ����л�ȡ
	 * 		һ������ʧЧ�������û��ʹ�õ���ǰһ������������Ч�����ǻ���Ҫ�����ݿⷢ��SQL����	
	 * 			1.sqlSession��ͬ
	 * 			2.sqlSession��ͬ����ѯ������ͬ������ǰ�Լ������л�û��������ݣ�
	 * 			3.sqlSession��ͬ�����β�ѯ֮��ִ������ɾ�Ĳ����������ɾ�Ŀ��ܶԵ�ǰ������Ӱ�죩
	 * 			4.sqlSession��ͬ���ֶ������һ�����棨������գ�
	 * �������棨ȫ�ֻ��棩
	 * 		
	 * */
	
	//һ������
	public void test01() throws Exception {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// �õ�һ�λỰ
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
			Employee employee1 = employeeMapper.getEmployeeById(1);
			System.out.println(employee1);
			
			// ���һ������
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
			// ֻ�лỰ�رգ���ѯ�����ݲ��ύ��namespaceΪEmployeeMapper�Ķ���������
			sqlSession1.close();
			
			// �ڶ��β�ѯ�ǴӶ��������л�ȡ�����ݣ���û�з����µ�sql
			Employee employee2 = employeeMapper2.getEmployeeById(1);
			System.out.println(employee2);
			sqlSession2.close();
			
			System.out.println(employee1==employee2);
		}finally {
		}
	}
}
