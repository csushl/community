package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    /* 查到的事多个帖子，所以是集合，这个userId是为了开发我的主页的时候才需要，但是首页的时候就不传，
    得到的是0，那么对应的就是一个动态的sql*/
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);
    // @Param 用来为参数取别名
    //如果需要动态的拼一个条件，在<if>里面使用，并且这个方法有且只有一个参数，这个参数前面必须取别名
    // 这个userId和上面是一样的功能
    int selectDiscussPostRows(@Param("userId") int userId);

//    int insertDiscussPost(DiscussPost discussPost);

}
