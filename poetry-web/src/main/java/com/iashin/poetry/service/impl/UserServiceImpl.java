package com.iashin.poetry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iashin.poetry.entity.User;
import com.iashin.poetry.service.UserService;
import com.iashin.poetry.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author dingzhen
* @description 针对表【user(用户信息表)】的数据库操作Service实现
* @createDate 2024-07-10 15:17:02
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




