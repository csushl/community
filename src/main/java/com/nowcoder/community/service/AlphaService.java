package com.nowcoder.community.service;

import com.nowcoder.community.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
@Scope //("prototype"):表示Bean实例有多个，每次get就实例化一个。默认单例。
public class AlphaService {

    @Autowired
    private AlphaDao alphaDao;
    //被Spring容器管理的bean只被实例化一次，因为它是单例的
    public AlphaService() {
        System.out.println("实例化AlphaService");
    }
    //初始化在构造器之后
    @PostConstruct
    public void init() {
        System.out.println("初始化AlphaService");
    }
    //销毁之前调用，释放某些资源
    @PreDestroy
    public void destroy() {
        System.out.println("销毁AlphaService");
    }

    public String find() {
        return alphaDao.select();
    }

}
