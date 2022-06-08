package com.cas.Interceptor;

import com.cas.bean.PageWrapper;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 下午10:39 2022/06/09
 * @version: V1.0
 * @review: mybatis分页插件
 */
@Intercepts(
        {
                @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),
        })
public class PageMybatisInterceptor implements Interceptor {
    private static final Logger log = LoggerFactory.getLogger(PageMybatisInterceptor.class);
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 1、检测当前是否满足分页条件
        // 带上分页参数
        StatementHandler target = (StatementHandler) invocation.getTarget();
        // SQL包，sql，参数，参数映射
        BoundSql boundSql = target.getBoundSql();
        Object parameterObject = boundSql.getParameterObject();

        PageWrapper page = null;

        if (parameterObject instanceof PageWrapper) {
            page = (PageWrapper) parameterObject;
        } else if (parameterObject instanceof Map) {
            page = (PageWrapper)((Map) parameterObject).values().stream().filter(v -> v instanceof PageWrapper).findFirst().orElse(null);
        }

        if (page == null) {
            return invocation.proceed();
        }

        // 2、设置总行数
        // select count(0) from (sql)
        page.setTotal(selectCount(invocation));

        // 3、修改原有的sql
        // select * from table_name offset 0, limit 50
        String newSql = String.format("%s limit %s offset %s", boundSql.getSql(), page.getPageSize(), page.getPageSize() * (page.getPageNum()-1));
        SystemMetaObject.forObject(boundSql).setValue("sql", newSql);
        return invocation.proceed();
    }


    private int selectCount(Invocation invocation) throws SQLException {
        int count = 0;
        StatementHandler target = (StatementHandler) invocation.getTarget();
        // SQL包，sql，参数，参数映射
        String countSql = String.format("select count(1) from (%s) as _page", target.getBoundSql().getSql());

        // JDBC
        Connection connection = (Connection) invocation.getArgs()[0];
        PreparedStatement preparedStatement = connection.prepareStatement(countSql);

        target.getParameterHandler().setParameters(preparedStatement);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            count = resultSet.getInt(1);
        }
        resultSet.close();
        preparedStatement.close();
        return count;
    }



    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
