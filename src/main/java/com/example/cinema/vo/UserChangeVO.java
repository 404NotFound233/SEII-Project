package com.example.cinema.vo;

/**
 * @author gumingzheng
 * @date 2019/5/31
 */
public class UserChangeVO {
    
	private String preusername;
	/**
     * 用户名，不可重复
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;

    public String getPreusername() {
    	return preusername;
    }
    
    public void setPreusername(String preusername) {
    	this.preusername = preusername;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
