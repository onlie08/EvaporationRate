package com.ch.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;
    //姓名
    @Unique
    private String name;
    //姓名
    @NotNull
    private String password;

    private int role;

    private String remark;

    @Generated(hash = 616137657)
    public User(Long id, String name, @NotNull String password, int role,
            String remark) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
        this.remark = remark;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return this.role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}
