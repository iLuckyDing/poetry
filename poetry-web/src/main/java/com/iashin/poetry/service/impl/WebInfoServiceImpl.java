package com.iashin.poetry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iashin.poetry.entity.WebInfo;
import com.iashin.poetry.service.WebInfoService;
import com.iashin.poetry.mapper.WebInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author dingzhen
* @description 针对表【web_info(网站信息表)】的数据库操作Service实现
* @createDate 2024-07-10 15:17:02
*/
@Service
public class WebInfoServiceImpl extends ServiceImpl<WebInfoMapper, WebInfo>
    implements WebInfoService{

}




