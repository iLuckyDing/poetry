package com.iashin.poetry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iashin.poetry.entity.Article;
import com.iashin.poetry.service.ArticleService;
import com.iashin.poetry.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

/**
* @author dingzhen
* @description 针对表【article(文章表)】的数据库操作Service实现
* @createDate 2024-07-10 15:07:43
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{

}




