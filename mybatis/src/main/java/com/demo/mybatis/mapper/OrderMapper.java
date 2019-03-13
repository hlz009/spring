package com.demo.mybatis.mapper;

import com.demo.mybatis.model.Order;
import com.demo.mybatis.model.OrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.session.RowBounds;

public interface OrderMapper {
    long countByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    @Insert({
        "insert into t_order (create_time, update_time, ",
        "customer, state)",
        "values (#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP}, ",
        "#{customer,jdbcType=VARCHAR}, #{state,jdbcType=INTEGER})"
    })
    @SelectKey(statement="CALL IDENTITY()", keyProperty="id", before=false, resultType=Long.class)
    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExampleWithRowbounds(OrderExample example, RowBounds rowBounds);

    List<Order> selectByExample(OrderExample example);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);
}