package com.iashin.poetry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iashin.poetry.entity.Comment;
import com.iashin.poetry.service.CommentService;
import com.iashin.poetry.mapper.CommentMapper;
import org.springframework.stereotype.Service;

/**
* @author dingzhen
* @description 针对表【comment(文章评论表)】的数据库操作Service实现
* @createDate 2024-07-10 15:17:02
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

}



