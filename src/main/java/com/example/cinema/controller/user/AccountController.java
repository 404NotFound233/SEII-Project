package com.example.cinema.controller.user;

import com.example.cinema.blImpl.user.AccountServiceImpl;
import com.example.cinema.config.InterceptorConfiguration;
import com.example.cinema.vo.UserForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.ScheduleBatchDeleteForm;
import com.example.cinema.vo.ScheduleViewForm;
import com.example.cinema.vo.UserChangeVO;
import com.example.cinema.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author huwen
 * @date 2019/3/23
 */
@RestController()
public class AccountController {
    private final static String ACCOUNT_INFO_ERROR="用户名或密码错误";
    @Autowired
    private AccountServiceImpl accountService;
    @PostMapping("/login")
    public ResponseVO login(@RequestBody UserForm userForm, HttpSession session){
        UserVO user = accountService.login(userForm);
        if(user==null){
           return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
        }
        //注册session
        //InterceptorConfiguration.SESSION_KEY指"user"
        session.setAttribute(InterceptorConfiguration.SESSION_KEY,userForm);
        return ResponseVO.buildSuccess(user);
    }
    @PostMapping("/register")
    public ResponseVO registerAccount(@RequestBody UserForm userForm){
        return accountService.registerAccount(userForm);
    }

    @PostMapping("/logout")
    public String logOut(HttpSession session){
        //remove session
        session.removeAttribute(InterceptorConfiguration.SESSION_KEY);
        return "index";
    }
    
    //根据用户名查询用户密码
    @RequestMapping(value = "user/search/{username}", method = RequestMethod.GET)
    public ResponseVO searchUserPassword(@PathVariable String username){
        return accountService.searchUserPassword(username);
    }
  
    //添加用户辣！
    @RequestMapping(value = "user/add", method = RequestMethod.POST)
    public ResponseVO addUser(@RequestBody  UserForm userForm){
        return accountService.addUser(userForm);
    }
    
    //删除用户
    @RequestMapping(value = "user/delete", method = RequestMethod.DELETE)
    public ResponseVO deleteUser(@RequestBody String username){
        return accountService.deleteUser(username.substring(1,username.length()-1));
    }
    
  //修改用户
    @RequestMapping(value = "user/change", method = RequestMethod.POST)
    public ResponseVO changeUser(@RequestBody UserChangeVO userChangeVO){
        return accountService.changeUser(userChangeVO.getPreusername(),userChangeVO.getUsername(),userChangeVO.getPassword());
    }
    
  //添加用户为管理员
    @RequestMapping(value = "user/admin/add", method = RequestMethod.POST)
    public ResponseVO addAdmin(@RequestBody  String username){
        return accountService.addAdmin(username.substring(1,username.length()-1));
    }
    
  //查询用户是否为管理员
    @RequestMapping(value = "user/admin/list", method = RequestMethod.GET)
    public ResponseVO getAllAdmin(){
        return accountService.getAllAdmin();
    }
    
   //删除用户管理员身份
   @RequestMapping(value = "user/admin/delete", method = RequestMethod.DELETE)
   public ResponseVO deleteAdmin(@RequestBody String username){
       return accountService.deleteAdmin(username.substring(1,username.length()-1));
   }
}
