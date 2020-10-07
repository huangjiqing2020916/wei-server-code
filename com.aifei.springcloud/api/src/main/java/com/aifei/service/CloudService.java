package com.aifei.service;

import com.aifei.model.Cloud;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CloudService extends IService<Cloud> {
    Cloud selectCloudById(String id);

    List<Cloud> getCloudAll();

    List<Cloud> getCloudAlls();

    Cloud selectCloudByIdWithCondition(String id, int number);

    public Cloud selectCloudByIdWithUnless(String id, int number);

    Cloud insertCloud(Cloud cloud);

    void deleteCloutById(String id);

    void deleteCloudByIdAndCleanCache(String id);
}
