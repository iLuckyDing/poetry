package com.iashin.poetry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iashin.poetry.entity.Sort;
import com.iashin.poetry.service.SortService;
import com.iashin.poetry.mapper.SortMapper;
import org.springframework.stereotype.Service;

/**
* @author dingzhen
* @description 针对表【sort(分类)】的数据库操作Service实现
* @createDate 2024-07-10 15:17:02
*/
@Service
public class SortServiceImpl extends ServiceImpl<SortMapper, Sort>
    implements SortService{

}




