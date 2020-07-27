/*
 * This file is generated by jOOQ.
 */
package generated.classic.reactive.mysql.tables.daos;


import generated.classic.reactive.mysql.tables.Somethingwithoutjson;
import generated.classic.reactive.mysql.tables.records.SomethingwithoutjsonRecord;

import io.github.jklingsporn.vertx.jooq.shared.reactive.AbstractReactiveVertxDAO;

import java.util.Collection;

import org.jooq.Configuration;


import java.util.List;
import io.vertx.core.Future;
import io.github.jklingsporn.vertx.jooq.classic.reactivepg.ReactiveClassicQueryExecutor;
/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SomethingwithoutjsonDao extends AbstractReactiveVertxDAO<SomethingwithoutjsonRecord, generated.classic.reactive.mysql.tables.pojos.Somethingwithoutjson, Integer, Future<List<generated.classic.reactive.mysql.tables.pojos.Somethingwithoutjson>>, Future<generated.classic.reactive.mysql.tables.pojos.Somethingwithoutjson>, Future<Integer>, Future<Integer>> implements io.github.jklingsporn.vertx.jooq.classic.VertxDAO<SomethingwithoutjsonRecord,generated.classic.reactive.mysql.tables.pojos.Somethingwithoutjson,Integer> {

    /**
     * @param configuration Used for rendering, so only SQLDialect must be set and must be one of the POSTGREs types.
     * @param delegate A configured AsyncSQLClient that is used for query execution
     */
    public SomethingwithoutjsonDao(Configuration configuration, io.vertx.sqlclient.SqlClient delegate) {
        super(Somethingwithoutjson.SOMETHINGWITHOUTJSON, generated.classic.reactive.mysql.tables.pojos.Somethingwithoutjson.class, new ReactiveClassicQueryExecutor<SomethingwithoutjsonRecord,generated.classic.reactive.mysql.tables.pojos.Somethingwithoutjson,Integer>(configuration,delegate,generated.classic.reactive.mysql.tables.mappers.RowMappers.getSomethingwithoutjsonMapper()));
    }

    @Override
    protected Integer getId(generated.classic.reactive.mysql.tables.pojos.Somethingwithoutjson object) {
        return object.getSomeid();
    }

    /**
     * Find records that have <code>someString IN (values)</code> asynchronously
     */
    public Future<List<generated.classic.reactive.mysql.tables.pojos.Somethingwithoutjson>> findManyBySomestring(Collection<String> values) {
        return findManyByCondition(Somethingwithoutjson.SOMETHINGWITHOUTJSON.SOMESTRING.in(values));
    }

    /**
     * Find records that have <code>someString IN (values)</code> asynchronously limited by the given limit
     */
    public Future<List<generated.classic.reactive.mysql.tables.pojos.Somethingwithoutjson>> findManyBySomestring(Collection<String> values, int limit) {
        return findManyByCondition(Somethingwithoutjson.SOMETHINGWITHOUTJSON.SOMESTRING.in(values),limit);
    }

    @Override
    public ReactiveClassicQueryExecutor<SomethingwithoutjsonRecord,generated.classic.reactive.mysql.tables.pojos.Somethingwithoutjson,Integer> queryExecutor(){
        return (ReactiveClassicQueryExecutor<SomethingwithoutjsonRecord,generated.classic.reactive.mysql.tables.pojos.Somethingwithoutjson,Integer>) super.queryExecutor();
    }

    @Override
    protected java.util.function.Function<io.vertx.sqlclient.RowSet<io.vertx.sqlclient.Row>,Long> extractMysqlLastInsertProperty(){
        return rs -> rs.property(io.vertx.mysqlclient.MySQLClient.LAST_INSERTED_ID);
    }
}
