package cn.keiven.service.dao.Impl;

import cn.keiven.service.dao.UserDao;
import cn.keiven.service.domain.User;
import cn.keiven.service.utils.JDBCUtils;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findUserByName(String name) {
        User user = null;
        try{
            // 1. 定义SQL
            String sql  = "select * from user where name= ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), name);
            // 2. 执行SQL
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }


    @Override
    public void addUser(String name, String password, int uid, String role) {
        try{
            // 1. 定义SQL
            String sql  = "insert into user values(?, ?, ?, ?)";
            template.update(sql, name, password, uid, role);
            // 2. 执行SQL
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("成功添加"+name+"的相关信息到数据库中，请查看...");

    }


    @Override
    public void updateUser(String name, String password, int uid, String role) { ;
        try{
            // 1. 定义SQL
            String sql  = "update user set password= ?,  uid= ? , role= ? where name = ?";
            template.update(sql,  password, uid, role, name);
            // 2. 执行SQL
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("成功修改"+name+"的相关信息到数据库中，请查看...");
    }

    @Override
    public void deleteUserByName(String name) {
        try{
            // 1. 定义SQL
            String sql  = "delete from user where name = ?";
            template.update(sql, name);
            // 2. 执行SQL
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("成功删除"+name+"的相关信息，请到数据库中查看...");
    }




    @Override
    public User findUserByNameAndPassword(String name, String password) {
        User user = null;
        try{
            // 1. 定义SQL
            String sql  = "select * from user where name= ? and password = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), name, password);
            // 2. 执行SQL
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }


    /*
     * 测试方法
     * */
    @Test
    public void test() {
        User user = findUserByName("admin");
        System.out.println(user.toString());
    }
}
