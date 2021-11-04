package cn.keiven.service.dao;

import cn.keiven.service.domain.User;

public interface UserDao {

    User findUserByName(String name);

    void addUser(String name, String password, int uid, String role);

    void updateUser(String name, String password, int uid, String role);

    void deleteUserByName(String name);

    User findUserByNameAndPassword(String name, String password);
}
