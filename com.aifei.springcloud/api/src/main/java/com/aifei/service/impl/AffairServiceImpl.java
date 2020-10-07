package com.aifei.service.impl;

import com.aifei.mapper.AffairMapper;
import com.aifei.model.Affair;
import com.aifei.model.Cloud;
import com.aifei.service.AffairService;
import com.aifei.service.CloudService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("AffairService")
public class AffairServiceImpl extends ServiceImpl<AffairMapper, Affair> implements AffairService {

    @Autowired
    private AffairService affairService;

    @Autowired
    private CloudService cloudService;


    @Override
    public Boolean transcation(Cloud cloud, Affair affair) {
        Boolean c = cloudService.save(cloud);
        Boolean a = affairService.save(affair);
        if (c && a) {
            return true;
        }
        return false;
    }
}
