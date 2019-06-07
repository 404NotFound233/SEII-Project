package com.example.cinema.data.user;

import com.example.cinema.po.Admin;
import com.example.cinema.po.User;

import java.util.*;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author huwen
 * @date 2019/3/23
 */
@Mapper
public interface AccountMapper {

    /**
     * 创建一个新的账号
     * @param username
     * @param password
     * @return
     */
    public int createNewAccount(@Param("username") String username, @Param("password") String password);

    /**
     * 根据用户名查找账号
     * @param username
     * @return
     */
    public User getAccountByName(@Param("username") String username);
    
    /**
     * 根据用户名删除账号
     * @param username
     * @return
     */
    public int deleteAccountByName(@Param("username") String username);
    
    /**
     * 根据用户名修改账号
     * @param preusername
     * @param username
     * @param password
     * @return
     */
    public int changeAccountByName(@Param("preusername") String preusername,@Param("username") String username,@Param("password") String password);
    
    /**
     * 根据用户名添加账号进admin
     * @param username
     * @return
     */
    public int addNewAdmin(@Param("username") String username);
    
    /**
     * 拉取所有管理员名单
     * @return
     */
    public List<Admin> getAllAdmin();
    
    /**
     * 根据用户名删除账号管理员身份
     * @param username
     * @return
     */
    public int deleteAdminByName(@Param("username") String username);
}

