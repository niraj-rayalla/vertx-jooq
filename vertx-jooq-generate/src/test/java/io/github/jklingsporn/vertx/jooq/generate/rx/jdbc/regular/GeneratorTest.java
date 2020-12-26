package io.github.jklingsporn.vertx.jooq.generate.rx.jdbc.regular;

import io.github.jklingsporn.vertx.jooq.generate.AbstractVertxGeneratorTest;
import io.github.jklingsporn.vertx.jooq.generate.HsqldbConfigurationProvider;
import org.jooq.codegen.VertxGeneratorStrategy;
import io.github.jklingsporn.vertx.jooq.generate.rx.RXJDBCVertxGenerator;

/**
 * Created by jklingsporn on 17.09.16.
 */
public class GeneratorTest extends AbstractVertxGeneratorTest{


    public GeneratorTest() {
        super(RXJDBCVertxGenerator.class, VertxGeneratorStrategy.class,"rx.jdbc.regular", HsqldbConfigurationProvider.getInstance());
    }

}
