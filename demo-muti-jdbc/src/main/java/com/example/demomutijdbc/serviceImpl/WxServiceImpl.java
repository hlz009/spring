package com.example.demomutijdbc.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demomutijdbc.exception.RollbackException;
import com.example.demomutijdbc.service.WxService;

/**
 * 测试事务回滚，多数据源需要指明使用的时那个数据库事务
 * @author xiaozhi009
 *
 */
@Service
public class WxServiceImpl implements WxService{

    @Autowired
	@Qualifier("primaryJdbcTemplate") // 取wx这个数据源
    private JdbcTemplate jdbcTemplate;

	@Override
	@Transactional(value = "wxTxManager")
	public void insertRecord() {
		jdbcTemplate.execute("INSERT INTO test (name) VALUES ('AAA')");
	}

	@Override
	@Transactional(value = "wxTxManager", rollbackFor = RollbackException.class)
	public void insertThenRollback() throws RollbackException {
		jdbcTemplate.execute("INSERT INTO test (name) VALUES ('BBB')");
	        throw new RollbackException();
	}

	/**
	 * 该方法可以写入，因为未回滚
	 * @Transactional 采用的是代理增强的方法，
	 * 由于事务增强处理是交给aop生成的代理类完成，在方法内部调用时是不起作用的
	 * (此时可以理解为普通方法调用)
	 * 有三种办法
	 * 第一种： 在方法头上加上
	 * @Transactional(rollbackFor = RollbackException.class)
	 * 第二种：注入调用者的实例
	 * @Autowired
	 * private WxService wxService;
	 * 以下改写成 wxService.insertThenRollback() 即可 将类的实例直接注入进来
	 * 第三种：获取当前代理对象
	 *  ((WxService) (AopContext.currentProxy())).insertThenRollback();
	 */
	@Override
	public void invokeInsertThenRollback() throws RollbackException {
		insertThenRollback();
	}
}
