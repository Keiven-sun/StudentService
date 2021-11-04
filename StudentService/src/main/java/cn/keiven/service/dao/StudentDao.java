package cn.keiven.service.dao;

import cn.keiven.service.domain.Student;

import java.util.Date;
import java.util.List;

public interface StudentDao {

    /*
     * 根据学生学号查询学生信息
     * @param sid
     * @return
     * */
    Student findStudentBySid(int sid);

    /*
     * 保存学生信息
     * @Param sid sname gender date address telephone
     *
     * */
    void addStudentInfo(int sid, String sname, String gender, Date date, String address, String telephone);

    /*
     * 更新学生信息
     * @Param sname, score, sid
     *
     * */
    void updateStudentInfo( String sname, String gender, Date date, String address, String telephone, int sid);

    /*
     * 根据学生姓名删除学生信息
     * @param sname
     * @return
     * */
    void deleteStudentBySid(int sid);

    /*
     * 查询所有学生信息
     * @param sname
     * @return
     * */
    List<Student> findAllStudent();


     /*
     * 生成各个数据的标题
     * */
      public void getParams();










}
