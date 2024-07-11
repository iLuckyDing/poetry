package com.iashin.poetry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iashin.poetry.entity.HistoryInfo;
import com.iashin.poetry.service.HistoryInfoService;
import com.iashin.poetry.mapper.HistoryInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author dingzhen
* @description 针对表【history_info(历史信息)】的数据库操作Service实现
* @createDate 2024-07-10 15:17:02
*/
@Service
public class HistoryInfoServiceImpl extends ServiceImpl<HistoryInfoMapper, HistoryInfo>
    implements HistoryInfoService{

}




