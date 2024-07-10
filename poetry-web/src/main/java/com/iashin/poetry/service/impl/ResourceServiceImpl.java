package com.iashin.poetry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iashin.poetry.entity.Resource;
import com.iashin.poetry.service.ResourceService;
import com.iashin.poetry.mapper.ResourceMapper;
import org.springframework.stereotype.Service;

/**
* @author dingzhen
* @description 针对表【resource(资源信息)】的数据库操作Service实现
* @createDate 2024-07-10 15:17:02
*/
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource>
    implements ResourceService{

}




