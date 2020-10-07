package com.aifei.dao;

import com.aifei.entity.User;

public interface UserMapper {
    User selectUserByUsernameAndPassword(String username,String password);
}