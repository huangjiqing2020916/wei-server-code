package com.aifei.service;

import com.aifei.model.Affair;
import com.aifei.model.Cloud;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AffairService extends IService<Affair> {

    Boolean transcation(Cloud cloud, Affair affair);


}
