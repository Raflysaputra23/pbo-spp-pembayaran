/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelompok.spp.model.object;

/**
 *
 * @author rafly
 */

interface User {
    public void setUserId(String id);
    public void setUsername(String name);
    public void setPassword(String pass);
    public void setEmail(String email);
    public void setNoTelp(String no);
    public void setRole(String role);
    public String getUserId();
    public String getUsername();
    public String getPassword();
    public String getEmail();
    public String getNoTelp();
    public String getRole();
}

public class UserModel implements User {
    private String userId;
    private String username;
    private String password;
    private String email;
    private String noTelp;
    private String role;

    
    public UserModel(String userId, String username, String password, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    @Override
    public void setUserId(String id){
        this.userId = id;
    };
    
    @Override
    public void setUsername(String name){
        this.username = name;
    };
    
    @Override
    public void setPassword(String pass){
        this.password = pass;
    };
    
    @Override
    public void setEmail(String email){
        
        this.email = email;
    };
    
    @Override
    public void setNoTelp(String no){
        this.noTelp = no;
    };
    
    @Override
    public void setRole(String role){
        this.role = role;
    };
    
    @Override
    public String getUserId(){
        return this.userId;
    };
    
    @Override
    public String getUsername(){
        return this.username;
    };
    
    @Override
    public String getPassword(){
        return this.password;
    };
    
    @Override
    public String getEmail(){
        return this.email;
    };
    
    @Override
    public String getNoTelp(){
        return this.noTelp;
    };
    
    @Override
    public String getRole(){
        return this.role;
    };
    
    
}
