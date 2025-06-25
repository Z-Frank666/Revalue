package com.frank.revaluebackend.mapper;

import com.frank.revaluebackend.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    /*
     * 新增用户信息
     * */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into user(name, password, email) " +
            "values (#{name},#{password},#{email})")
    void insert(User user);

    /**
     * 根据邮箱和密码查询员工信息
     */
    @Select("select * from user where email = #{email} and password = #{password}")
    User getUser(User user);

    /**
     * 根据邮箱查询员工信息
     */
    @Select("select * from user where email = #{email}")
    User getUserByEmail(String email);

    @Select("select * from user where id = #{id}")
    User getById(Integer id);

    @Update("update user set name=#{name},nickname=#{nickname},gender=#{gender},email=#{email},avatar=#{avatar},phone=#{phone},birthday=#{birthday},balance=#{balance},description=#{description},address=#{address} where id=#{id}")
    void updateById(User user);

    @Select("select avatar from user where id = #{id}")
    String getAvatar(Integer id);

    @Select("select nickname from user where id = #{id}")
    String getNickname(Integer id);

    @Select("select name from user where id = #{id}")
    String getUsername(Integer id);
}
