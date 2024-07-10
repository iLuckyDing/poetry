package com.iashin.poetry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iashin.poetry.entity.ImChatUserMessage;
import com.iashin.poetry.service.ImChatUserMessageService;
import com.iashin.poetry.mapper.ImChatUserMessageMapper;
import org.springframework.stereotype.Service;

/**
* @author dingzhen
* @description 针对表【im_chat_user_message(单聊记录)】的数据库操作Service实现
* @createDate 2024-07-10 15:17:02
*/
@Service
public class ImChatUserMessageServiceImpl extends ServiceImpl<ImChatUserMessageMapper, ImChatUserMessage>
    implements ImChatUserMessageService{

}



