package com.iashin.poetry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iashin.poetry.entity.Family;
import com.iashin.poetry.service.FamilyService;
import com.iashin.poetry.mapper.FamilyMapper;
import org.springframework.stereotype.Service;

/**
* @author dingzhen
* @description 针对表【family(家庭信息)】的数据库操作Service实现
* @createDate 2024-07-10 15:17:02
*/
@Service
public class FamilyServiceImpl extends ServiceImpl<FamilyMapper, Family>
    implements FamilyService{

}




