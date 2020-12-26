package io.github.jklingsporn.vertx.jooq.generate.rx.jdbc.guice;

import io.github.jklingsporn.vertx.jooq.generate.AbstractVertxGeneratorTest;
import io.github.jklingsporn.vertx.jooq.generate.HsqldbConfigurationProvider;
import org.jooq.codegen.VertxGeneratorStrategy;
import io.github.jklingsporn.vertx.jooq.generate.rx.RXJDBCGuiceVertxGenerator;

/**
 * Created by jklingsporn on 17.09.16.
 */
public class GeneratorTest extends AbstractVertxGeneratorTest{


    public GeneratorTest() {
        super(RXJDBCGuiceVertxGenerator.class, VertxGeneratorStrategy.class,"rx.jdbc.guice", HsqldbConfigurationProvider.getInstance());
    }

}
