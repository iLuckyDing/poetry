package com.iashin.poetry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iashin.poetry.entity.ImChatUserGroupMessage;
import com.iashin.poetry.service.ImChatUserGroupMessageService;
import com.iashin.poetry.mapper.ImChatUserGroupMessageMapper;
import org.springframework.stereotype.Service;

/**
* @author dingzhen
* @description 针对表【im_chat_user_group_message(群聊记录)】的数据库操作Service实现
* @createDate 2024-07-10 15:17:02
*/
@Service
public class ImChatUserGroupMessageServiceImpl extends ServiceImpl<ImChatUserGroupMessageMapper, ImChatUserGroupMessage>
    implements ImChatUserGroupMessageService{

}




