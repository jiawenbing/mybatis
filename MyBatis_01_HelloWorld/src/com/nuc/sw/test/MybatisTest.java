package com.nuc.sw.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.nuc.sw.bean.Employee;
import com.nuc.sw.dao.EmployeeMapper;

public class MybatisTest {
	/**
	 * 
	 * SqlSession�����Connection����һ�����Ƿ��̰߳�ȫ�ģ�������Ϊȫ�ֹ��������ÿ��ʹ�ö�Ӧ��ȥ��ȡ�µġ�
	 * ���裺
	 * 1.����xml�����ļ�(ȫ�������ļ�,������Դ��һЩ������Ϣ)������һ��SqlSessionFactory����
	 * 2.��дsqlӳ���ļ���������ÿһ��sql�Լ�sql�ķ�װ����
	 * 3.��sqlӳ���ļ�ע����xmlȫ�������ļ���
	 * 4.��д����
	 * 		ʹ��ȫ�������ļ��õ�SqlSessionFactory����
	 * 		ʹ��SqlSessionFactory�õ�һ��SqlSession����
	 * 		һ��SqlSession�ʹ��������ݿ��һ�ζԻ�������͹ر�
	 * 		ʹ��sql��Ψһ��ʶ����mybatisʹ����һ��sql��sql��䶼�Ǳ�����sqlӳ���ļ��С�
	 * @throws IOException
	 */
	
	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		return sqlSessionFactory;
	}
	
	@Test
	public void test1() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		//1.����xml�����ļ�(ȫ�������ļ�,������Դ��һЩ������Ϣ)������һ��SqlSessionFactory����
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		//2.��ȡSqlSessionʵ������ֱ��ִ����ӳ���sql���
		SqlSession openSession = sqlSessionFactory.openSession();
		
		/* ����1 sql��Ψһ��ʶ namespace+id
		 * ����2  ִ��sql�õ��Ĳ���
		 */
		try{
			Employee employee = openSession.selectOne("com.nuc.sw.dao.EmployeeMapper.getEmployeeById", 1);
			System.out.println(employee);
		}finally {
			openSession.close();
		}
	}
	
	@Test
	public void test2() throws IOException {
		// ��ȡSqlSessionFactory����
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		//��ȡSqlSession����
		SqlSession sqlSession = sqlSessionFactory.openSession();
		//��ȡ�ӿڵ�ʵ�������
		try{
			//��Ϊ�ӿڴ���һ��������󣬴�����������ɾ�Ĳ�
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			Employee employee = mapper.getEmployeeById(1);
			System.out.println(employee);
		}finally {
			sqlSession.close();
		}
	}
	
}
