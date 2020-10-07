package com.aifei.service.impl;

import com.aifei.mapper.CloudMapper;
import com.aifei.model.Cloud;
import com.aifei.service.CloudService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@CacheConfig(cacheNames = "cloud")
@Service("cloudService")
public class CloudServiceImpl extends ServiceImpl<CloudMapper, Cloud> implements CloudService {

    @Autowired
    private CloudMapper cloudMapper;

    /**
     * @Cacheable 缓存有数据时，从缓存获取；没有数据时，执行方法，并将返回值保存到缓存中
     * @Cacheable 一般在查询中使用
     * 1) cacheNames 指定缓存区，没有配置使用@CacheConfig指定的缓存区
     * 2) key 指定缓存区的key
     * 3) 注解的值使用SpEL表达式
     * eq ==
     * lt <
     * le <=
     * gt >
     * ge >=
     */

    @Cacheable(cacheNames = "cloud2", key = "#id")
    public Cloud selectCloudById(String id) {
        return cloudMapper.selectById(id);
    }

    @Cacheable(key = "'list'")
    public List<Cloud> getCloudAll() {
        return cloudMapper.selectCloudAll();
    }

    @CachePut(key = "'all'")
    public List<Cloud> getCloudAlls() {
        return cloudMapper.selectCloudAll();
    }

    /**
     * condition 满足条件缓存数据
     */
    @Cacheable(key = "#id", condition = "#number ge 20") // >= 20
    public Cloud selectCloudByIdWithCondition(String id, int number) {
        return cloudMapper.selectById(id);
    }

    /**
     * unless 满足条件时否决缓存数据
     */
    @Cacheable(key = "#id", unless = "#number lt 20") // < 20
    public Cloud selectCloudByIdWithUnless(String id, int number) {
        return cloudMapper.selectById(id);
    }

    /**
     * 　　　* @CachePut 一定会执行方法，并将返回值保存到缓存中
     *
     * @CachePut 一般在新增和修改中使用
     */
    @CachePut(key = "#cloud.id")
    public Cloud insertCloud(Cloud cloud) {
        cloudMapper.insert(cloud);
        return cloud;
    }


    /**
     * 根据key删除缓存区中的数据
     */
    @CacheEvict(key = "#id")
    public void deleteCloutById(String id) {
        cloudMapper.deleteById(id);
    }

    /**
     * allEntries = true ：删除整个缓存区的所有值，此时指定的key无效
     * beforeInvocation = true ：默认false，表示调用方法之后删除缓存数据；true时，在调用之前删除缓存数据(如方法出现异常)
     */
    @CacheEvict(key = "#id", allEntries = true)
    public void deleteCloudByIdAndCleanCache(String id) {
        cloudMapper.deleteById(id);
    }

}
