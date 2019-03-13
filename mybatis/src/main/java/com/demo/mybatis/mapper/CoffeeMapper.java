package com.demo.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import com.demo.mybatis.model.Coffee;

/**
 * Mapper接口
 * @Mapper 也可以不用配置，在配置文件中，配置@MapperScan
 * interface 默认权限是 public
 * @author xiaozhi009
 *
 */

@Mapper 
public interface CoffeeMapper {
	@Insert("insert into t_coffee (name, price, create_time, update_time)"
            + "values (#{name}, #{price}, now(), now())")
    @Options(useGeneratedKeys = true)
    Long save(Coffee coffee);

    @Select("select * from t_coffee where id = #{id}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "create_time", property = "createTime"),
            // map-underscore-to-camel-case = true 可以实现一样的效果
            // @Result(column = "update_time", property = "updateTime"),
    })
    // 这里的@param是mybatis的  注解和xml中要使用mybatis对应的写法 #{id}
    Coffee findById(@Param("id") Long id);

    @Select("select * from t_coffee order by id")
    List<Coffee> findAllWithRowBounds(RowBounds rowBounds);

    @Select("select * from t_coffee order by id")
    List<Coffee> findAllWithParam(@Param("pageNum") int pageNum,
                                  @Param("pageSize") int pageSize);

    @Select("select * from t_coffee order by id")
    List<Coffee> findAll();
}
