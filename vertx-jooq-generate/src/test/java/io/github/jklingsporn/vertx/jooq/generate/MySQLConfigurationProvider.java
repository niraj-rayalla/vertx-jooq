package io.github.jklingsporn.vertx.jooq.generate;

import org.jooq.SQLDialect;
import org.jooq.codegen.VertxGeneratorStrategy;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.meta.jaxb.Configuration;
import org.jooq.meta.jaxb.Jdbc;
import org.jooq.meta.mysql.MySQLDatabase;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by jensklingsporn on 02.11.16.
 */
public class MySQLConfigurationProvider extends AbstractDatabaseConfigurationProvider {


    private static MySQLConfigurationProvider INSTANCE;
    public static MySQLConfigurationProvider getInstance() {
        return INSTANCE == null ? INSTANCE = new MySQLConfigurationProvider() : INSTANCE;
    }

    @Override
    public void setupDatabase() throws Exception {
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/", Credentials.MYSQL.getUser(), Credentials.MYSQL.getPassword())){
            connection.prepareStatement("DROP DATABASE IF EXISTS `vertx`;").execute();
            connection.prepareStatement("CREATE SCHEMA `vertx` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;").execute();
            connection.prepareStatement("USE vertx;").execute();
            connection.prepareStatement("DROP TABLE IF EXISTS something;").execute();
            connection.prepareStatement("CREATE TABLE `vertx`.`something` (\n" +
                    "  `someId` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `someString` VARCHAR(45) NULL,\n" +
                    "  `someHugeNumber` BIGINT(20) NULL,\n" +
                    "  `someSmallNumber` SMALLINT(5) NULL,\n" +
                    "  `someRegularNumber` INT(10) NULL,\n" +
                    "  `someDouble` DOUBLE NULL,\n" +
                    "  `someEnum` ENUM('FOO', 'BAR', 'BAZ') DEFAULT 'FOO' NOT NULL,\n" +
                    "  `someJsonObject` VARCHAR(45) NULL,\n" +
                    "  `someJsonArray` VARCHAR(45) NULL,\n" +
                    "  `someTimestamp` TIMESTAMP NULL,\n" +
                    "  PRIMARY KEY (`someId`));").execute();
            connection.prepareStatement("DROP TABLE IF EXISTS somethingComposite;").execute();
            connection.prepareStatement("CREATE TABLE `somethingComposite` (\n" +
                    "  `someId` INT NOT NULL,\n" +
                    "  `someSecondId` INT NOT NULL,\n" +
                    "  `someJsonObject` VARCHAR(45) NULL,\n" +
                    "  PRIMARY KEY (`someId`, `someSecondId`));\n").execute();
            connection.prepareStatement("DROP TABLE IF EXISTS somethingWithoutJson;").execute();
            connection.prepareStatement("CREATE TABLE `somethingWithoutJson` (\n" +
                    "  `someId` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `someString` varchar(45) COLLATE utf8_bin DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`someId`));\n").execute();
            connection.close();
        }
    }

    @Override
    public Configuration createGeneratorConfig(String generatorName, String packageName, Class<? extends VertxGeneratorStrategy> generatorStrategy){
        Jdbc jdbcConfig = new Jdbc();
        jdbcConfig.setDriver("com.mysql.jdbc.Driver");
        jdbcConfig.setUrl("jdbc:mysql://127.0.0.1:3306/");
        jdbcConfig.setUser(Credentials.MYSQL.getUser());
        jdbcConfig.setPassword(Credentials.MYSQL.getPassword());
        return createGeneratorConfig(generatorName, packageName, generatorStrategy, jdbcConfig, MySQLDatabase.class.getName());
    }

    @Override
    public org.jooq.Configuration createDAOConfiguration() {
        return new DefaultConfiguration().set(SQLDialect.MYSQL);
    }

}
