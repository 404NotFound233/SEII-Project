package com.example.cinema.bl.user;

import com.example.cinema.vo.UserForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserVO;

/**
 * @author huwen
 * @date 2019/3/23
 */
public interface AccountService {

    /**
     * 注册账号
     * @return
     */
    public ResponseVO registerAccount(UserForm userForm);

    /**
     * 用户登录，登录成功会将用户信息保存再session中
     * @return
     */
    public UserVO login(UserForm userForm);
    
    /**
     * 根据admin键入的用户名查找用户及密码
     * @return
     */
    public ResponseVO searchUserPassword(String username);
    
    /**
     * 添加用户
     * @return
     */
    public ResponseVO addUser(UserForm userForm);

    /**
     * 删除用户
     * @return
     */
    public ResponseVO deleteUser(String username);
    
    /**
     * 修改用户
     * @return
     */
    public ResponseVO changeUser(String preusername,String username,String password);
    
    /**
     * 添加用户为管理员
     * @return
     */
    public ResponseVO addAdmin(String username);
    
    /**
     * 拉取所有管理员名单
     * @return
     */
    public ResponseVO getAllAdmin();
    
    /**
     * 删除用户管理员身份
     * @return
     */
    public ResponseVO deleteAdmin(String username);
}
