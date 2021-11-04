package cn.keiven.service.GUI;

import cn.keiven.service.dao.Impl.StudentDaoImpl;
import cn.keiven.service.dao.StudentDao;
import cn.keiven.service.domain.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StudentManagement extends JFrame implements  ActionListener {

    static StudentDao studentDao = new StudentDaoImpl();

    JTabbedPane dbTabPane;
    JPanel inputPanel;      //录入面板
    JPanel viewPanel;      //浏览面板
    JPanel updatePanel;      //更新面板
    JPanel deletePanel;      //删除面板

    JButton inputBtn;            //录入
    JButton clearBtn1;
    StudentPanel inputInnerPanel;

    JTextArea viewArea;     //浏览
    JButton viewBtn;

    StudentPanel updateInnerPanel;   //修改数据
    JLabel updateInputLbl;
    JTextField updateInputText;
    JButton updateBtn;

    StudentPanel deleteInnerPanel;
    JLabel inputNoLabel;
    JTextField inputNoField;
    JButton deleteBtn;

    Connection conn;
    Statement stmt;

    public StudentManagement(){
        super("学生基本信息管理系统");
        serGUIComponent();
    }

    public void serGUIComponent() {
        // TODO Auto-generated method stub
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.setSize(500, 500);
        dbTabPane = new JTabbedPane();

        //定义录入面板
        inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputBtn = new JButton("录入");
        clearBtn1 = new JButton("清除");
        /*使用this对象，可以不用声明内部类而直接在本类中实现接口覆盖接口的方法。*/
        inputBtn.addActionListener(this);
        clearBtn1.addActionListener(this);
        inputInnerPanel = new StudentPanel();
        inputPanel.add(inputInnerPanel);
        inputPanel.add(inputBtn);
        inputPanel.add(clearBtn1);
        dbTabPane.add("录入数据", inputPanel);

        //定义浏览面板
        viewPanel = new JPanel();
        viewPanel.setLayout(new BorderLayout());
        viewArea = new JTextArea(6,35);
        viewBtn = new JButton("浏览");
        viewPanel.add(new JScrollPane(viewArea), BorderLayout.CENTER);
        viewPanel.add(viewBtn,BorderLayout.SOUTH);
        viewBtn.addActionListener(this);
        dbTabPane.addTab("浏览数据", viewPanel);

        //定义更新面板
        updateInnerPanel = new StudentPanel();
        updateInputLbl = new JLabel("输入学号：");
        updateInputText = new JTextField(10);
        updateInputText.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //将更新面板的显示内容清除
                viewARecord(updateInputText.getText(), updateInnerPanel);
                updateInputText.setText("");
            }
        });
        updateBtn = new JButton("修改");
        updateBtn.addActionListener(this);
        updatePanel = new JPanel();
        updatePanel.add(updateInnerPanel);
        updatePanel.add(updateInputLbl);
        updatePanel.add(updateInputText);
        updatePanel.add(updateBtn);
        dbTabPane.add("修改数据", updatePanel);

        //定义删除面板
        deleteInnerPanel = new StudentPanel();
        inputNoLabel = new JLabel("输入学号：");
        inputNoField = new JTextField(10);
        inputNoField.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //将删除面板的显示内容清除
                viewARecord(inputNoField.getText(),deleteInnerPanel);
                inputNoField.setText("");
            }
        });
        deleteBtn = new JButton("删除");
        deleteBtn.addActionListener(this);
        deletePanel = new JPanel();
        deletePanel.add(deleteInnerPanel);
        deletePanel.add(inputNoLabel);
        deletePanel.add(inputNoField);
        deletePanel.add(deleteBtn);
        dbTabPane.add("删除数据", deletePanel);

        c.add(BorderLayout.NORTH,dbTabPane);

    }

    public void inputRecords(){      //录入学生数据
        String no = inputInnerPanel.getNo();
        String name = inputInnerPanel.getName();
        String gender = inputInnerPanel.getGender();
        String birth = inputInnerPanel.getBirth();
        String address = inputInnerPanel.getAddress();
        String tel = inputInnerPanel.getTel();


        try{
            Date birthday = StringToDate(birth);
            studentDao.addStudentInfo(Integer.parseInt(no), name, gender, birthday, address, tel);
            JOptionPane.showMessageDialog(null, "插入成功");
        }catch(Exception e){
            e.printStackTrace();
        }finally{
        }
    }

    public void viewARecord(String no, StudentPanel p){
        try{
            String viewSQL = "SELECT * From student where 学号='"+no+"'";
            ResultSet rs = stmt.executeQuery(viewSQL);
            if(rs.next()){
                p.setNo(rs.getString("学号"));
                p.setName(rs.getString("姓名"));
                p.setGender(rs.getString("性别"));
                p.setBirth(rs.getString("出生年月"));
                p.setAddress(rs.getString("家庭住址"));
                p.setTel(rs.getString("联系电话"));
            }
        }catch(SQLException e){
            System.out.println("浏览学生记录失败");
            e.printStackTrace();
        }finally{
        }
    }

    public void viewRecords(){
        try{
            viewArea.setText("");
            String viewString = "";
            String[] params = {"学号   ","姓名    ", "性别     ", "出生年月   ", "家庭地址   ", "联系电话    "};
            //获得字段名称
            for(int i = 0; i <= params.length-1; i++){
                viewString+=params[i] + "\t";
            }
            viewString += "\n";
            List<Student> list = studentDao.findAllStudent();
            for (Student student : list ) {
                viewString += student.getSid() + "\t";
                viewString += student.getSname() + "\t";
                viewString += student.getGender() + "\t";
                viewString += student.getBirthdate() + "\t";
                viewString += student.getAddress() + "\t";
                viewString += student.getTelephone() + "\t";
                viewString += "\n";
            }
            viewArea.setText(viewString);
        }catch(Exception e){
            System.out.println("浏览学生记录失败");
            e.printStackTrace();
        }finally{
        }
    }

    public void updateRecord(String no){    //修改学号为no学生的记录
        try{
            // 设置传参
            String name = updateInnerPanel.getName();
            String gender = updateInnerPanel.getGender();
            String birth = updateInnerPanel.getBirth();
            String address = updateInnerPanel.getAddress();
            String telephone = updateInnerPanel.getTel();
            String sid = updateInnerPanel.getNo();

            //            将字符串转换真Date对象
            Date birthday = StringToDate(birth);
            studentDao.updateStudentInfo(name, gender, birthday, address, telephone,Integer.parseInt(sid.trim()) );
            JOptionPane.showMessageDialog(null, "修改数据成功");
        }catch(Exception e){
            e.printStackTrace();
        }finally{
        }
    }

    public void deleteRecord(String no){
        try{
            studentDao.deleteStudentBySid(Integer.parseInt(no.trim()));
            JOptionPane.showMessageDialog(null, "删除成功");
        }catch(Exception e){
            System.err.println("删除失败");
            e.printStackTrace();
        }finally{
        }
    }

    public static void app() {
        // TODO Auto-generated method stub
        StudentManagement app = new StudentManagement();
        app.setSize(600,200);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource() == inputBtn){
            inputRecords();
        }else if(e.getSource() == viewBtn){
            viewRecords();
        }else if(e.getSource() == updateBtn){
            updateRecord(updateInnerPanel.getNo());
        }else if(e.getSource() == deleteBtn){
            deleteRecord(deleteInnerPanel.getNo());
            deleteInnerPanel.clearContent();
        }else if(e.getSource() == clearBtn1){
            inputInnerPanel.clearContent();
        }
    }

    /*
    * 定义一个字符串转换到Date对象的方法
    * */
    public static java.util.Date StringToDate(String birthdate) throws ParseException {
        SimpleDateFormat formt = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = formt.parse(birthdate);
        return date;
    }
}
