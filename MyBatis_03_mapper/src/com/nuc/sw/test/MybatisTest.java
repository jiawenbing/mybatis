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
			Employee employee = openSession.selectOne("nuc.sw.mybatis.EmployeeMapper.getEmployeeById", 1);
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
	
	@Test
	public void test3() throws IOException {
		// 获取SqlSessionFactory对象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		//获取SqlSession对象
		SqlSession sqlSession = sqlSessionFactory.openSession();
		//获取接口的实现类对象
		try{
			//会为接口创建一个代理对象，代理对象进行增删改查
			EmployeeMapperAnnotation mapper = sqlSession.getMapper(EmployeeMapperAnnotation.class);
			Employee employee = mapper.getEmployeeById(1);
			System.out.println(employee);
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	/**test crud
	 * 
	 * 1.mybatis允许增删改方法直接定义以下返回值类型
	 *   Boolean Integer Long  void
	 * 2.手动提交sqlSessionFactory.openSession();
	 * 	 自动提交sqlSessionFactory.openSession(true);
	 * @throws IOException
	 */
	public void test4() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 1.得手动进行提交
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
			//测试添加
			Employee employee = new Employee(null, "贾文兵", "男", "wenbing_jia@163.com");
			employeeMapper.addEmployee(employee);
			System.out.println(employee.getId());
			//测试修改
			/*Employee employee = new Employee(2, "张航名", "男", "hangming_zhang@163.com");
			employeeMapper.updateEmployee(employee);*/
			//测试删除
			/*boolean flag = employeeMapper.deleteEmployeeById(2);
			System.out.println(flag);*/
			sqlSession.commit();
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void test5() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
			Employee employee = employeeMapper.getEmployeeByIdAndLastName(1, "tom");
			System.out.println(employee);
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void test6() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", 1);
			map.put("lastName", "tom");
			map.put("tableName", "tbl_employee");
			Employee employee = employeeMapper.getEmployeeByMap(map);
			System.out.println(employee);
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void test7() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
			List<Employee> list = employeeMapper.getEmployeesByLastNameLike("%m%");
			for(Employee employee : list) {
				System.out.println(employee);
			}
		}finally {
			sqlSession.close();
		}
	}
	
	//返回值为单条记录并封装成map key为字段名  value为字段值 
	@Test
	public void test8() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
			Map<String, Object> map = employeeMapper.getEmployeeByIdReturnMap(1);
			System.out.println(map);
		}finally {
			sqlSession.close();
		}
}
	// 返回值为多条记录的javabean对象  key为记录的主键  value为javabean对象
	@Test
	public void test9() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
			Map<Integer, Object> map = employeeMapper.getEmployeeByLastNameReturnMap("%m%");
			System.out.println(map);
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	// resultMap使用
	public void test10() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapperPlus employeeMapperPlus = sqlSession.getMapper(EmployeeMapperPlus.class);
			Employee employee = employeeMapperPlus.getEmployeeById(1);
			System.out.println(employee);
			
			/*Employee employee = employeeMapperPlus.getEmpAndDeptById(1);
			System.out.println(employee);
			System.out.println(employee.getDept());*/
			
			/*DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
			Department department = departmentMapper.getDepartmentById(1);
			System.out.println(department);*/
			
			/*EmployeeMapperPlus employeeMapperPlus = sqlSession.getMapper(EmployeeMapperPlus.class);
			Employee employee = employeeMapperPlus.getEmployeeByIdStep(3);
			System.out.println(employee);*/
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void test11() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			DepartmentMapper departmentMapper = sqlSession.getMapper(DepartmentMapper.class);
			/*Department department = departmentMapper.getDepartmentsById(1);
			System.err.println(department);
			System.out.println(department.getEmps());*/
			Department department = departmentMapper.getDepartmentsByIdStep(1);
			System.out.println(department);
			System.out.println(department.getEmps());
		}finally {
			sqlSession.close();
		}
	}
}
