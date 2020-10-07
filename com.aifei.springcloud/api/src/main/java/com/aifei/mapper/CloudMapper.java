package com.aifei.mapper;

import com.aifei.model.Cloud;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CloudMapper extends BaseMapper<Cloud> {
    List<Cloud> selectCloudAll();
}
