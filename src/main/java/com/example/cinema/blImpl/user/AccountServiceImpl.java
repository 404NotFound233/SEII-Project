package com.example.cinema.blImpl.user;

import com.example.cinema.bl.user.AccountService;
import com.example.cinema.data.user.AccountMapper;
import com.example.cinema.po.Admin;
import com.example.cinema.po.MostPopularMovies;
import com.example.cinema.po.User;
import com.example.cinema.vo.UserForm;
import com.example.cinema.vo.AdminVO;
import com.example.cinema.vo.MostPopularMoviesVO;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserVO;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author huwen
 * @date 2019/3/23
 */
@Service
public class AccountServiceImpl implements AccountService {
    private final static String ACCOUNT_EXIST = "账号已存在";
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public ResponseVO registerAccount(UserForm userForm) {
        try {
            accountMapper.createNewAccount(userForm.getUsername(), userForm.getPassword());
        } catch (Exception e) {
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public UserVO login(UserForm userForm) {
        User user = accountMapper.getAccountByName(userForm.getUsername());
        if (null == user || !user.getPassword().equals(userForm.getPassword())) {
            return null;
        }
        return new UserVO(user);
    }

    /**
     * 根据用户名查找用户
     */
    @Override
    public ResponseVO searchUserPassword(String username) {
    	try {
    		User user = accountMapper.getAccountByName(username);
    		if (user == null) {
    			return null;
    		}
    		List<UserVO> userVOList = new ArrayList<UserVO>();
    		userVOList.add(new UserVO(user));
    		return ResponseVO.buildSuccess(userVOList);
    	} catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
    
    /**
     * 添加用户
     */
    @Override
    public ResponseVO addUser(UserForm userForm) {
    	try {
    		accountMapper.createNewAccount(userForm.getUsername(), userForm.getPassword());
    		return ResponseVO.buildSuccess();
    	} catch (Exception e){
    		e.printStackTrace();
    		return ResponseVO.buildFailure("失败");
    	}
    }
    
    /**
     * 删除用户
     */
    @Override
    public ResponseVO deleteUser(String username) {
    	try {
    		accountMapper.deleteAccountByName(username);
    		return ResponseVO.buildSuccess();
    	} catch (Exception e){
    		e.printStackTrace();
    		return ResponseVO.buildFailure("失败");
    	}
    }
    
    /**
     * 修改用户
     */
    @Override
    public ResponseVO changeUser(String preusername,String username,String password) {
    	try {
    		accountMapper.changeAccountByName(preusername,username,password);
    		return ResponseVO.buildSuccess();
    	} catch (Exception e){
    		e.printStackTrace();
    		return ResponseVO.buildFailure("失败");
    	}
    }
    
    /**
     * 添加用户为管理员
     */
    @Override
    public ResponseVO addAdmin(String username) {
    	try {
    		accountMapper.addNewAdmin(username);
    		return ResponseVO.buildSuccess();
    	} catch (Exception e){
    		e.printStackTrace();
    		return ResponseVO.buildFailure("失败");
    	}
    }
    
    /**
     * 拉取所有管理员名单
     */
    @Override
    public ResponseVO getAllAdmin() {
    	try {
    		return ResponseVO.buildSuccess(adminList2AdminVOList(accountMapper.getAllAdmin()));
    	} catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
    
    /**
     * 删除用户的管理员身份
     */
    @Override
    public ResponseVO deleteAdmin(String username) {
    	try {
    		accountMapper.deleteAdminByName(username);
    		return ResponseVO.buildSuccess();
    	} catch (Exception e){
    		e.printStackTrace();
    		return ResponseVO.buildFailure("失败");
    	}
    }
    
    private List<AdminVO> adminList2AdminVOList(List<Admin> adminList){
        List<AdminVO> adminVOList = new ArrayList<>();
        for(Admin admin : adminList){
            adminVOList.add(new AdminVO(admin));
        }
        return adminVOList;
    }
}
