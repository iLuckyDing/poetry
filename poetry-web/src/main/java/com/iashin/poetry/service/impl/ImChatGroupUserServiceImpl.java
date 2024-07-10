package com.iashin.poetry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iashin.poetry.entity.ImChatGroupUser;
import com.iashin.poetry.service.ImChatGroupUserService;
import com.iashin.poetry.mapper.ImChatGroupUserMapper;
import org.springframework.stereotype.Service;

/**
* @author dingzhen
* @description 针对表【im_chat_group_user(聊天群成员)】的数据库操作Service实现
* @createDate 2024-07-10 15:17:02
*/
@Service
public class ImChatGroupUserServiceImpl extends ServiceImpl<ImChatGroupUserMapper, ImChatGroupUser>
    implements ImChatGroupUserService{

}




