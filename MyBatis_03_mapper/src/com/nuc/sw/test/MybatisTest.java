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
			Employee employee = openSession.selectOne("nuc.sw.mybatis.EmployeeMapper.getEmployeeById", 1);
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
	
	@Test
	public void test3() throws IOException {
		// ��ȡSqlSessionFactory����
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		//��ȡSqlSession����
		SqlSession sqlSession = sqlSessionFactory.openSession();
		//��ȡ�ӿڵ�ʵ�������
		try{
			//��Ϊ�ӿڴ���һ��������󣬴�����������ɾ�Ĳ�
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
	 * 1.mybatis������ɾ�ķ���ֱ�Ӷ������·���ֵ����
	 *   Boolean Integer Long  void
	 * 2.�ֶ��ύsqlSessionFactory.openSession();
	 * 	 �Զ��ύsqlSessionFactory.openSession(true);
	 * @throws IOException
	 */
	public void test4() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 1.���ֶ������ύ
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try{
			EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
			//�������
			Employee employee = new Employee(null, "���ı�", "��", "wenbing_jia@163.com");
			employeeMapper.addEmployee(employee);
			System.out.println(employee.getId());
			//�����޸�
			/*Employee employee = new Employee(2, "�ź���", "��", "hangming_zhang@163.com");
			employeeMapper.updateEmployee(employee);*/
			//����ɾ��
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
	
	//����ֵΪ������¼����װ��map keyΪ�ֶ���  valueΪ�ֶ�ֵ 
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
	// ����ֵΪ������¼��javabean����  keyΪ��¼������  valueΪjavabean����
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
	// resultMapʹ��
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
