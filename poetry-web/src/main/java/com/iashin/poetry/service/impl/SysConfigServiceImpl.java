package com.iashin.poetry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iashin.poetry.entity.SysConfig;
import com.iashin.poetry.service.SysConfigService;
import com.iashin.poetry.mapper.SysConfigMapper;
import org.springframework.stereotype.Service;

/**
* @author dingzhen
* @description 针对表【sys_config(参数配置表)】的数据库操作Service实现
* @createDate 2024-07-10 15:17:02
*/
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig>
    implements SysConfigService{

}




