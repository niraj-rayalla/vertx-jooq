/*
 * This file is generated by jOOQ.
 */
package generated.classic.reactive.regular;


import generated.classic.reactive.regular.tables.Something;
import generated.classic.reactive.regular.tables.Somethingcomposite;
import generated.classic.reactive.regular.tables.Somethingwithoutjson;
import generated.classic.reactive.regular.tables.records.SomethingRecord;
import generated.classic.reactive.regular.tables.records.SomethingcompositeRecord;
import generated.classic.reactive.regular.tables.records.SomethingwithoutjsonRecord;

import org.jooq.Identity;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>vertx</code> schema.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<SomethingRecord, Integer> IDENTITY_SOMETHING = Identities0.IDENTITY_SOMETHING;
    public static final Identity<SomethingwithoutjsonRecord, Integer> IDENTITY_SOMETHINGWITHOUTJSON = Identities0.IDENTITY_SOMETHINGWITHOUTJSON;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<SomethingRecord> SOMETHING_PKEY = UniqueKeys0.SOMETHING_PKEY;
    public static final UniqueKey<SomethingcompositeRecord> SOMETHINGCOMPOSITE_PKEY = UniqueKeys0.SOMETHINGCOMPOSITE_PKEY;
    public static final UniqueKey<SomethingwithoutjsonRecord> SOMETHINGWITHOUTJSON_PKEY = UniqueKeys0.SOMETHINGWITHOUTJSON_PKEY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<SomethingRecord, Integer> IDENTITY_SOMETHING = Internal.createIdentity(Something.SOMETHING, Something.SOMETHING.SOMEID);
        public static Identity<SomethingwithoutjsonRecord, Integer> IDENTITY_SOMETHINGWITHOUTJSON = Internal.createIdentity(Somethingwithoutjson.SOMETHINGWITHOUTJSON, Somethingwithoutjson.SOMETHINGWITHOUTJSON.SOMEID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<SomethingRecord> SOMETHING_PKEY = Internal.createUniqueKey(Something.SOMETHING, "something_pkey", new TableField[] { Something.SOMETHING.SOMEID }, true);
        public static final UniqueKey<SomethingcompositeRecord> SOMETHINGCOMPOSITE_PKEY = Internal.createUniqueKey(Somethingcomposite.SOMETHINGCOMPOSITE, "somethingComposite_pkey", new TableField[] { Somethingcomposite.SOMETHINGCOMPOSITE.SOMEID, Somethingcomposite.SOMETHINGCOMPOSITE.SOMESECONDID }, true);
        public static final UniqueKey<SomethingwithoutjsonRecord> SOMETHINGWITHOUTJSON_PKEY = Internal.createUniqueKey(Somethingwithoutjson.SOMETHINGWITHOUTJSON, "somethingWithoutJson_pkey", new TableField[] { Somethingwithoutjson.SOMETHINGWITHOUTJSON.SOMEID }, true);
    }
}