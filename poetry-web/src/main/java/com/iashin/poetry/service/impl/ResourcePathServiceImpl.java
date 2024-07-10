package com.iashin.poetry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iashin.poetry.entity.ResourcePath;
import com.iashin.poetry.service.ResourcePathService;
import com.iashin.poetry.mapper.ResourcePathMapper;
import org.springframework.stereotype.Service;

/**
* @author dingzhen
* @description 针对表【resource_path(资源聚合)】的数据库操作Service实现
* @createDate 2024-07-10 15:17:02
*/
@Service
public class ResourcePathServiceImpl extends ServiceImpl<ResourcePathMapper, ResourcePath>
    implements ResourcePathService{

}




