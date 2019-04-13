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
	 * SqlSession对象和Connection对象一样都是非线程安全的，不可作为全局共享变量，每次使用都应该去获取新的。
	 * 步骤：
	 * 1.根据xml配置文件(全局配置文件,有数据源等一些环境信息)，创建一个SqlSessionFactory对象
	 * 2.编写sql映射文件，配置了每一个sql以及sql的封装规则
	 * 3.将sql映射文件注册在xml全局配置文件中
	 * 4.编写代码
	 * 		使用全局配置文件得到SqlSessionFactory工厂
	 * 		使用SqlSessionFactory得到一个SqlSession对象
	 * 		一个SqlSession就代表与数据库的一次对话，用完就关闭
	 * 		使用sql的唯一标识告诉mybatis使用哪一个sql，sql语句都是保存在sql映射文件中。
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
		//1.根据xml配置文件(全局配置文件,有数据源等一些环境信息)，创建一个SqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		//2.获取SqlSession实例，能直接执行已映射的sql语句
		SqlSession openSession = sqlSessionFactory.openSession();
		
		/* 参数1 sql的唯一标识 namespace+id
		 * 参数2  执行sql用到的参数
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
		// 获取SqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		//获取SqlSession对象
		SqlSession sqlSession = sqlSessionFactory.openSession();
		//获取接口的实现类对象
		try{
			//会为接口创建一个代理对象，代理对象进行增删改查
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			Employee employee = mapper.getEmployeeById(1);
			System.out.println(employee);
		}finally {
			sqlSession.close();
		}
	}
	
}
