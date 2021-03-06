/*
 * This file is generated by jOOQ.
 */
package generated.classic.jdbc.regular.vertx.tables;


import generated.classic.jdbc.regular.vertx.Keys;
import generated.classic.jdbc.regular.vertx.Vertx;
import generated.classic.jdbc.regular.vertx.tables.records.SomethingcompositeRecord;

import io.github.jklingsporn.vertx.jooq.shared.JsonObjectConverter;
import io.vertx.core.json.JsonObject;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Somethingcomposite extends TableImpl<SomethingcompositeRecord> {

    private static final long serialVersionUID = -617371516;

    /**
     * The reference instance of <code>VERTX.SOMETHINGCOMPOSITE</code>
     */
    public static final Somethingcomposite SOMETHINGCOMPOSITE = new Somethingcomposite();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SomethingcompositeRecord> getRecordType() {
        return SomethingcompositeRecord.class;
    }

    /**
     * The column <code>VERTX.SOMETHINGCOMPOSITE.SOMEID</code>.
     */
    public final TableField<SomethingcompositeRecord, Integer> SOMEID = createField(DSL.name("SOMEID"), org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>VERTX.SOMETHINGCOMPOSITE.SOMESECONDID</code>.
     */
    public final TableField<SomethingcompositeRecord, Integer> SOMESECONDID = createField(DSL.name("SOMESECONDID"), org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>VERTX.SOMETHINGCOMPOSITE.SOMEJSONOBJECT</code>.
     */
    public final TableField<SomethingcompositeRecord, JsonObject> SOMEJSONOBJECT = createField(DSL.name("SOMEJSONOBJECT"), org.jooq.impl.SQLDataType.VARCHAR(45), this, "", new JsonObjectConverter());

    /**
     * Create a <code>VERTX.SOMETHINGCOMPOSITE</code> table reference
     */
    public Somethingcomposite() {
        this(DSL.name("SOMETHINGCOMPOSITE"), null);
    }

    /**
     * Create an aliased <code>VERTX.SOMETHINGCOMPOSITE</code> table reference
     */
    public Somethingcomposite(String alias) {
        this(DSL.name(alias), SOMETHINGCOMPOSITE);
    }

    /**
     * Create an aliased <code>VERTX.SOMETHINGCOMPOSITE</code> table reference
     */
    public Somethingcomposite(Name alias) {
        this(alias, SOMETHINGCOMPOSITE);
    }

    private Somethingcomposite(Name alias, Table<SomethingcompositeRecord> aliased) {
        this(alias, aliased, null);
    }

    private Somethingcomposite(Name alias, Table<SomethingcompositeRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Somethingcomposite(Table<O> child, ForeignKey<O, SomethingcompositeRecord> key) {
        super(child, key, SOMETHINGCOMPOSITE);
    }

    @Override
    public Schema getSchema() {
        return Vertx.VERTX;
    }

    @Override
    public UniqueKey<SomethingcompositeRecord> getPrimaryKey() {
        return Keys.SYS_PK_10182;
    }

    @Override
    public List<UniqueKey<SomethingcompositeRecord>> getKeys() {
        return Arrays.<UniqueKey<SomethingcompositeRecord>>asList(Keys.SYS_PK_10182);
    }

    @Override
    public Somethingcomposite as(String alias) {
        return new Somethingcomposite(DSL.name(alias), this);
    }

    @Override
    public Somethingcomposite as(Name alias) {
        return new Somethingcomposite(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Somethingcomposite rename(String name) {
        return new Somethingcomposite(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Somethingcomposite rename(Name name) {
        return new Somethingcomposite(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, Integer, JsonObject> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
