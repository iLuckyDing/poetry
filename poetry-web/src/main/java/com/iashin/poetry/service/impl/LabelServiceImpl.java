package com.iashin.poetry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iashin.poetry.entity.Label;
import com.iashin.poetry.service.LabelService;
import com.iashin.poetry.mapper.LabelMapper;
import org.springframework.stereotype.Service;

/**
* @author dingzhen
* @description 针对表【label(标签)】的数据库操作Service实现
* @createDate 2024-07-10 15:17:02
*/
@Service
public class LabelServiceImpl extends ServiceImpl<LabelMapper, Label>
    implements LabelService{

}




