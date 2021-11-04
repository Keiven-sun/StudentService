package cn.keiven.service.dao.Impl;

import cn.keiven.service.dao.StudentDao;
import cn.keiven.service.domain.Student;
import cn.keiven.service.utils.JDBCUtils;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import javax.swing.event.TreeExpansionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StudentDaoImpl implements StudentDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());


    @Override
    public Student findStudentBySid(int sid) {
        Student student = null;
        try{
            // 1. 定义SQL
            String sql  = "select * from studentinfo where sid= ?";
            student = template.queryForObject(sql, new BeanPropertyRowMapper<Student>(Student.class), sid);
            // 2. 执行SQL
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public void addStudentInfo(int sid, String sname, String gender, Date date, String address, String telephone) {
        try{
            // 1. 定义SQL
            String sql  = "insert into studentinfo values(?, ?, ?, ?, ?, ?)";
            template.update(sql, sid, sname,gender, date, address, telephone);
            // 2. 执行SQL
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("成功添加"+sname+"的相关信息到数据库中，请查看...");

    }

    @Override
    public void updateStudentInfo(String sname, String gender, Date date, String address, String telephone, int sid) { ;
        try{
            // 1. 定义SQL
            String sql  = "update studentinfo set sname= ?,  gender= ?, birthday = ?, address = ?, telephone = ?  where sid = ?";
            template.update(sql, sname, gender, date, address ,telephone, sid);
            // 2. 执行SQL
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("成功修改"+sname+"的相关信息到数据库中，请查看...");
    }
    @Override
    public void deleteStudentBySid(int sid) {
        try{
            // 1. 定义SQL
            String sql  = "delete from studentinfo where sid = ?";
            template.update(sql, sid);
            // 2. 执行SQL
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("成功删除学号为"+sid+"的学生的相关信息，请到数据库中查看...");

    }

    @Override
    public List<Student> findAllStudent() {

        List<Student> studentList = new ArrayList<Student>();;
        // 1. 编写SQL
        String sql = "select * from studentinfo";
        // 2. 执行SQL
        List<Student> list = template.query(sql, new ResultSetExtractor<List<Student>>() {
            @Override
            public List<Student> extractData(ResultSet rs) throws SQLException, DataAccessException {
//                studentList = new ArrayList<Student>();
                while (rs.next()) {;
                    int sid = rs.getInt("sid");
                    String sname = rs.getString("sname");
                    String  gender  = rs.getString("gender");
                    Date birthdate = rs.getDate("birthday");
                    String address = rs.getNString("address");
                    String telephone = rs.getString("telephone");
                    // 创建学生对象
                    Student student = new Student();
                    // 传输从数据库获得的值，后存储泛型列表中
                    student.setSid(sid);
                    student.setSname(sname);
                    student.setGender(gender);
                    student.setBirthdate(birthdate);
                    student.setAddress(address);
                    student.setTelephone(telephone);
                    // 将学生对象添加到泛型列表中
                    studentList.add(student);
                }
                return null;
            }
        });
        return studentList;
    }

    public void getParams() {


    }


    /*
     * 测试单一查询方法
     * */
    @Test
    public void test() {
        Student student = findStudentBySid(1);
        System.out.println(student.toString());
    }

    /*
     * 测试查询全部方法
     * */
    @Test
    public void test1() throws ParseException {
        List<Student> list = findAllStudent();
        for (Student student : list ) {


        }
//        System.out.println(list.toString());
//        Map map = new HashMap();
//        map.put("key1", "value1");
//        map.put("key2", "value2");
//        List list = new ArrayList<>();
//        list.add(map);
//        for (int i=0;i<list.size();i++)
//        {
//            Map  map1=(Map)list.get(i);
//            Iterator iterator = map1.keySet().iterator();
//            while (iterator.hasNext())
//            {
//                String key = (String) iterator.next();
//                Object object = map1.get(key);
//                System.out.println(object);
//            }
//        }

        /*
         * 测试插入方法
         * */
//    @Test
//    public void test2() throws ParseException {
//        Date date = new SimpleDateFormat("yyyy年MM月dd日").parse("2020年2月2日");
//        addStudentInfo(3, "王老二", "男",  date,  "南京市"
//        ,"19083407099");
//    }
    }
}
