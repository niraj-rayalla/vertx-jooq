package io.github.jklingsporn.vertx.jooq.generate;

import io.github.jklingsporn.vertx.jooq.shared.JsonArrayConverter;
import io.github.jklingsporn.vertx.jooq.shared.JsonObjectConverter;
import io.github.jklingsporn.vertx.jooq.shared.ObjectToJsonArrayBinding;
import io.github.jklingsporn.vertx.jooq.shared.ObjectToJsonObjectBinding;
import io.github.jklingsporn.vertx.jooq.shared.internal.AbstractVertxDAO;
import io.github.jklingsporn.vertx.jooq.shared.internal.VertxPojo;
import io.github.jklingsporn.vertx.jooq.shared.postgres.PgConverter;
import io.vertx.core.Vertx;
import io.vertx.core.impl.Arguments;
import org.jooq.Constants;
import org.jooq.Record;
import org.jooq.codegen.*;
import org.jooq.impl.SQLDataType;
import org.jooq.meta.*;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;

import java.io.File;
import java.time.*;
import java.util.*;
import java.util.function.Function;

/**
 * Created by jklingsporn on 17.10.16.
 * Extension of the jOOQ's <code>JavaGenerator</code>.
 * By default, it generates POJO's that have a <code>#fromJson</code> and a <code>#toJson</code>-method which takes/generates a <code>JsonObject</code> out of the generated POJO.
 * When you've enabled Interface-generation, these methods are added to the generated Interface as default-methods.
 * Besides these method there is also a constructor generated which takes a <code>JsonObject</code>.
 * It also generates DAOs which implement <code>VertxDAO</code> and allow you to execute CRUD-operations asynchronously.
 */
public abstract class VertxGenerator extends KotlinGenerator {

    private static final JooqLogger logger = JooqLogger.getLogger(VertxGenerator.class);

    private final boolean generateJson;
    protected VertxGeneratorStrategy vertxGeneratorStrategy;

    public VertxGenerator() {
        this(true);
    }

    public VertxGenerator(boolean generateJson) {
        this.generateJson = generateJson;
        this.setGeneratePojos(true);
    }

    public String getJavaTypePublic(DataTypeDefinition type, JavaWriter out) {
        return this.getJavaType(type, out);
    }

    /**
     * Overwrite this method to handle your custom type. This is needed especially when you have custom converters.
     * @param column the column definition
     * @param setter the setter name
     * @param columnType the type of the column
     * @param javaMemberName the java member name
     * @param out the JavaWriter
     * @return <code>true</code> if the column was handled.
     * @see #generateFromJson(TableDefinition, JavaWriter, org.jooq.codegen.GeneratorStrategy.Mode)
     */
    protected boolean handleCustomTypeFromJson(TypedElementDefinition<?> column, String setter, String columnType, String javaMemberName, JavaWriter out){
        return false;
    }

    /**
     * Overwrite this method to handle your custom type. This is needed especially when you have custom converters.
     * @param column the column definition
     * @param getter the getter name
     * @param columnType the type of the column
     * @param javaMemberName the java member name
     * @param out the JavaWriter
     * @return <code>true</code> if the column was handled.
     * @see #generateToJson(TableDefinition, JavaWriter, org.jooq.codegen.GeneratorStrategy.Mode)
     */
    protected boolean handleCustomTypeToJson(TypedElementDefinition<?> column, String getter, String columnType, String javaMemberName, JavaWriter out) {
        return false;
    }


    /**
     * @param pType the POJO type
     * @return the type returned by {@code QueryExecutor#insertReturningPrimary}.
     */
    protected abstract String renderFindOneType(String pType);

    /**
     *
     * @param pType the POJO type
     * @return the type returned by {@code QueryExecutor#findMany}.
     */
    protected abstract String renderFindManyType(String pType);

    /**
     * @return the type returned by {@code QueryExecutor#execute}.
     */
    protected abstract String renderExecType();

    /**
     * @param tType the primary key type
     * @return the type returned by {@code QueryExecutor#insertReturningPrimary}.
     */
    protected abstract String renderInsertReturningType(String tType);

    /**
     *
     * @param rType the record type
     * @param pType the POJO type
     * @param tType the primary key type
     * @return the {@code QueryExecutor} used for query execution.
     */
    protected abstract String renderQueryExecutor(String rType, String pType, String tType);

    /**
     * @param rType the record type
     * @param pType the POJO type
     * @param tType the primary key type
     * @return the interface implemented by the generated DAO.
     */
    protected abstract String renderDAOInterface(String rType, String pType, String tType);

    /**
     * Write the DAO constructor.
     * @param out the JavaWriter
     * @param className the class name of the generated DAO
     * @param tableIdentifier the table identifier
     * @param rType the record type
     * @param pType the POJO type
     * @param tType the primary key type
     * @param schema
     */
    protected abstract void writeDAOConstructor(JavaWriter out, String className, String tableIdentifier, String rType, String pType, String tType, String schema);

    /**
     * Write imports in the DAO.
     * @param out the JavaWriter
     */
    protected void writeDAOImports(JavaWriter out){}

    /**
     * Write annotations on the DAOs class signature.
     * @param out the JavaWriter
     */
    protected void writeDAOClassAnnotation(JavaWriter out){}

    /**
     * Write annotations on the DAOs constructor.
     * @param out the JavaWriter
     */
    protected void writeDAOConstructorAnnotation(JavaWriter out){}

    /**
     * Can be used to overwrite certain methods, e.g. AsyncXYZ-strategies shouldn't
     * allow insertReturning for non-numeric or compound primary keys due to limitations
     * of the AsyncMySQL/Postgres client.
     * @param schema
     * @param out the JavaWriter
     * @param className the class name of the generated DAO
     * @param tableIdentifier the table identifier
     * @param rType the record type
     * @param pType the POJO type
     * @param tType the primary key type
     */
    protected void overwriteDAOMethods(SchemaDefinition schema, JavaWriter out, String className, String tableIdentifier, String rType, String pType, String tType){}

    /**
     * @return the fully qualified class name of the DAO's extension
     */
    protected String renderDaoExtendsClassName(){
        return AbstractVertxDAO.class.getName();
    }

    /**
     * @return the fully qualified class name of the vertx instance
     */
    protected String renderFQVertxName(){
        return Vertx.class.getName();
    }

    /**
     * Write some extra data during code generation
     * @param definition the schema
     * @param writerGenerator a Function that returns a new JavaWriter based on a File.
     * @return a Collection of JavaWriters with data init.
     */
    protected Collection<JavaWriter> writeExtraData(SchemaDefinition definition, Function<File,JavaWriter> writerGenerator){
        return Collections.emptyList();
    }

    @Override
    protected void generatePojoMultiConstructor(Definition tableOrUDT, JavaWriter out) {
        super.generatePojoMultiConstructor(tableOrUDT, out);
        if(generateJson) {
            generateFromJsonConstructor(tableOrUDT, out, GeneratorStrategy.Mode.POJO);
        }
    }

    @Override
    protected void generatePojoClassFooter(TableDefinition table, JavaWriter out) {
        super.generatePojoClassFooter(table, out);
        if(generateJson){
            if(!generateInterfaces()){
                generateFromJson(table,out, GeneratorStrategy.Mode.POJO);
                generateToJson(table, out, GeneratorStrategy.Mode.POJO);
            }
        }
    }

    @Override
    protected void generateInterfaceClassFooter(TableDefinition table, JavaWriter out) {
        super.generateInterfaceClassFooter(table, out);
        if(generateJson && generateInterfaces()){
            generateFromJson(table, out, GeneratorStrategy.Mode.INTERFACE);
            generateToJson(table, out, GeneratorStrategy.Mode.INTERFACE);
        }
    }

    @Override
    protected void generateRecordClassFooter(TableDefinition table, JavaWriter out) {
        super.generateRecordClassFooter(table, out);
        if(generateJson){
            generateFromJsonConstructor(table, out, GeneratorStrategy.Mode.RECORD);
            if(!generateInterfaces()){
                generateFromJson(table,out, GeneratorStrategy.Mode.RECORD);
                generateToJson(table, out, GeneratorStrategy.Mode.RECORD);
            }
        }
    }

    @Override
    protected JavaWriter newJavaWriter(File file) {
        return new VertxJavaWriter(file, generateFullyQualifiedTypes(), targetEncoding);
    }


    @Override
    protected void printPackage(JavaWriter out, Definition definition, GeneratorStrategy.Mode mode) {
        super.printPackage(out, definition, mode);
        if(mode.equals(GeneratorStrategy.Mode.DAO)){
            out.println("import %s;",List.class.getName());
            writeDAOImports(out);
        }else if(generateJson && generateInterfaces() && mode.equals(GeneratorStrategy.Mode.INTERFACE)) {
            writeUnexpectedJsonValueTypeImport(out);
        }else if(generateJson && mode.equals(GeneratorStrategy.Mode.POJO)) {
            writeUnexpectedJsonValueTypeImport(out);
        }else if(generateJson && mode.equals(GeneratorStrategy.Mode.RECORD)) {
            writeUnexpectedJsonValueTypeImport(out);
        }
    }

    private void writeUnexpectedJsonValueTypeImport(JavaWriter out){
        out.println("import static %s.*;", VertxPojo.class.getName());
    }

    @Override
    protected void generateDaos(SchemaDefinition schema) {
        super.generateDaos(schema);
        writeExtraData(schema);
    }

    private void writeExtraData(SchemaDefinition definition){
        Collection<JavaWriter> writers = writeExtraData(definition, this::newJavaWriter);
        writers.forEach(this::closeJavaWriter);
    }

    private void generateFromJson(TableDefinition table, JavaWriter out, GeneratorStrategy.Mode mode){
        out.println();
        out.tab(1).override();
        String className = getStrategy().getJavaClassName(table, mode);
        out.tab(1).println("public %s%s fromJson(io.vertx.core.json.JsonObject json) {", mode == GeneratorStrategy.Mode.INTERFACE?"default ":"",className);
        for (TypedElementDefinition<?> column : table.getColumns()) {
            String setter = getStrategy().getJavaSetterName(column, GeneratorStrategy.Mode.INTERFACE);
            String columnType = getJavaType(column.getType(), out);
            String javaMemberName = getJsonKeyName(column);
            String jsonValueExtractor = null;
            //converters are handled in ComponentBasedVertxGenerator
            if(handleCustomTypeFromJson(column, setter, columnType, javaMemberName, out)) {
                //handled by user
            }else if(isType(columnType, Integer.class)){
                jsonValueExtractor = "json::getInteger";
            }else if(isType(columnType, Short.class)){
                jsonValueExtractor = "key -> {Integer i = json.getInteger(key); return i==null?null:i.shortValue();}";
            }else if(isType(columnType, Byte.class)){
                jsonValueExtractor = "key -> {Integer i = json.getInteger(key); return i==null?null:i.byteValue();}";
            }else if(isType(columnType, Long.class)){
                jsonValueExtractor = "json::getLong";
            }else if(isType(columnType, Float.class)){
                jsonValueExtractor = "json::getFloat";
            }else if(isType(columnType, Double.class)){
                jsonValueExtractor = "json::getDouble";
            }else if(isType(columnType, Boolean.class)){
                jsonValueExtractor = "json::getBoolean";
            }else if(isType(columnType, String.class)){
                jsonValueExtractor = "json::getString";
            }else if(columnType.equals(byte.class.getName()+"[]")){
                jsonValueExtractor = "json::getBinary";
            }else if(isType(columnType,Instant.class)){
                jsonValueExtractor = "json::getInstant";
            }else if(isJavaTimeType(columnType)){
                jsonValueExtractor = String.format("key -> {String s = json.getString(key); return s==null?null:%s.parse(s);}",columnType);
            }else if(isEnum(table, column)) {
                //if this is an enum from the database (no converter) it has getLiteral defined
                if(column.getType().getConverter() == null){
                    jsonValueExtractor = String.format("key -> java.util.Arrays.stream(%s.values()).filter(td -> td.getLiteral().equals(json.getString(key))).findFirst().orElse(null)",columnType);
                //otherwise just use valueOf
                }else{
                    jsonValueExtractor = String.format("key -> {String s = json.getString(key); return s==null?null:%s.valueOf(s);}",columnType);
                }
            }else if((column.getType().getConverter() != null && isType(column.getType().getConverter(),JsonObjectConverter.class)) ||
                    (column.getType().getBinding() != null && isType(column.getType().getBinding(),ObjectToJsonObjectBinding.class))){
                jsonValueExtractor = "json::getJsonObject";
            }else if((column.getType().getConverter() != null && isType(column.getType().getConverter(),JsonArrayConverter.class)) ||
                    (column.getType().getBinding() != null && isType(column.getType().getBinding(), ObjectToJsonArrayBinding.class))){
                jsonValueExtractor = "json::getJsonArray";
            }else if(isType(columnType,List.class)){
                String genericType = columnType.substring(columnType.indexOf("<")+1,columnType.lastIndexOf(">"));
                jsonValueExtractor = String.format("key -> {io.vertx.core.json.JsonArray arr = json.getJsonArray(key); return arr==null?null:new java.util.ArrayList<%s>(arr.getList());}",genericType);
            }else{
                logger.warn(String.format("Omitting unrecognized type %s for column %s in table %s!",columnType,column.getName(),table.getName()));
                out.tab(2).println(String.format("// Omitting unrecognized type %s for column %s!",columnType,column.getName()));
            }
            if(jsonValueExtractor != null){
                out.tab(2).println("setOrThrow(this::%s,%s,\"%s\",\"%s\");", setter, jsonValueExtractor, javaMemberName, columnType);
            }
        }
        out.tab(2).println("return this;");
        out.tab(1).println("}");
        out.println();
    }

    public boolean isEnum(TableDefinition table, TypedElementDefinition<?> column) {
        return table.getDatabase().getEnum(table.getSchema(), column.getType().getUserType()) != null ||
                (column.getType().getConverter()!= null && column.getType().getConverter().endsWith("EnumConverter"));
    }

    public boolean isJson(DataTypeDefinition columnType) {
        return columnType.getType().equals(SQLDataType.JSON.getTypeName()) || columnType.getType().equals(SQLDataType.JSONB.getTypeName());
    }

    protected boolean isType(String columnType, Class<?> clazz) {
        int i = columnType.indexOf("<");
        return (i==-1?columnType:columnType.substring(0, i )).equals(clazz.getName());
    }

    protected Class<?> tryGetPgConverterFromType(String columnType, String converter) {
        try {
            Class<?> converterClazz = Class.forName(converter);
            if(PgConverter.class.isAssignableFrom(converterClazz)){
                PgConverter<?,?,?> converterInstance = (PgConverter<?, ?, ?>) converterClazz.newInstance();
                return converterInstance.rowConverter().fromType();
            }
            return null;
        } catch (ClassNotFoundException e) {
            logger.info(String.format("'%s' to map '%s' could not be accessed from code generator.",converter,columnType));
            return null;
        } catch (IllegalAccessException | InstantiationException e) {
            logger.info(String.format("'%s' to map '%s' could not be instantiated from code generator.",converter,columnType));
            return null;
        }
    }

    private void generateToJson(TableDefinition table, JavaWriter out, GeneratorStrategy.Mode mode){
        out.println();
        out.tab(1).override();
        out.tab(1).println("public %sio.vertx.core.json.JsonObject toJson() {",mode== GeneratorStrategy.Mode.INTERFACE?"default ":"");
        out.tab(2).println("io.vertx.core.json.JsonObject json = new io.vertx.core.json.JsonObject();");
        for (TypedElementDefinition<?> column : table.getColumns()) {
            String getter = getStrategy().getJavaGetterName(column, GeneratorStrategy.Mode.INTERFACE);
            String columnType = getJavaType(column.getType(), out);
            if(handleCustomTypeToJson(column,getter,columnType, getJsonKeyName(column), out)) {
                //handled by user
            }else if(isEnum(table,column)){
                //if enum is handled by custom type, try getLiteral() is not available
                if(column.getType().getConverter() == null){
                    out.tab(2).println("json.put(\"%s\",%s()==null?null:%s().getLiteral());", getJsonKeyName(column),getter,getter);
                }else{
                    out.tab(2).println("json.put(\"%s\",%s()==null?null:%s().name());", getJsonKeyName(column),getter,getter);
                }
            }else if(isAllowedJsonType(column, columnType)){
                out.tab(2).println("json.put(\"%s\",%s());", getJsonKeyName(column),getter);
            }else if(isJavaTimeType(columnType)){
                out.tab(2).println("json.put(\"%s\",%s()==null?null:%s().toString());", getJsonKeyName(column),getter,getter);
            }else if(isCollectionType(columnType)){
                out.tab(2).println("json.put(\"%s\",%s()==null?null: new io.vertx.core.json.JsonArray(%s()));", getJsonKeyName(column),getter,getter);
            }else{
                logger.warn(String.format("Omitting unrecognized type %s for column %s in table %s!",columnType,column.getName(),table.getName()));
                out.tab(2).println(String.format("// Omitting unrecognized type %s for column %s!",columnType,column.getName()));
            }
        }
        out.tab(2).println("return json;");
        out.tab(1).println("}");
        out.println();
    }

    /**
     * @param column
     * @return the JSON-key name of this column. Starting from version 2.4.0
     * this defaults to the name of that database column. There are different ways to change this behaviour:<br>
     * - subclass and override this method<br>
     * - subclass and override <code>VertxGeneratorStrategy#getJsonKeyName</code><br>
     * - plug-in a custom GeneratorStrategy into the <code>VertxGeneratorStrategy</code> that returns a strategy of
     * your choice for <code>GeneratorStrategy#getJavaMemberName(column, DefaultGeneratorStrategy.Mode.POJO)</code>
     */
    protected String getJsonKeyName(TypedElementDefinition<?> column) {
        return vertxGeneratorStrategy.getJsonKeyName(column);
    }

    private boolean isAllowedJsonType(TypedElementDefinition<?> column, String columnType){
        return isType(columnType, Integer.class) || isType(columnType, Short.class) || isType(columnType, Byte.class) ||
                isType(columnType, Long.class) || isType(columnType,Float.class) || isType(columnType, Double.class) ||
                isType(columnType, Boolean.class) || isType(columnType,String.class) || isType(columnType, Instant.class) ||
                columnType.equals(byte.class.getName()+"[]") || (column.getType().getConverter() != null &&
                (isType(column.getType().getConverter(),JsonObjectConverter.class) || isType(column.getType().getConverter(),JsonArrayConverter.class)))
                || (column.getType().getBinding() != null && isType(column.getType().getBinding(),ObjectToJsonObjectBinding.class));
    }

    private boolean isJavaTimeType(String columnType){
        return isType(columnType, LocalDateTime.class) || isType(columnType, LocalTime.class)
                || isType(columnType, ZonedDateTime.class) || isType(columnType, OffsetDateTime.class)
                || isType(columnType, LocalDate.class);
    }

    private boolean isCollectionType(String columnType){
        return isType(columnType,Collection.class) || isType(columnType,List.class) || isType(columnType,Set.class);
    }


    @Override
    public void setStrategy(GeneratorStrategy strategy) {
        Arguments.require(strategy instanceof VertxGeneratorStrategy, "Requires instance of VertxGeneratorStrategy");
        super.setStrategy(strategy);
        this.vertxGeneratorStrategy = (VertxGeneratorStrategy) strategy;
    }

    /**
     * @return the VertxGeneratorStrategy used. Unfortunately {@code getStrategy()} cannot be used because every
     * {@code GeneratorStrategy} is wrapped into a package local jOOQ-class, so casting doesn't work.
     */
    public VertxGeneratorStrategy getVertxGeneratorStrategy() {
        return vertxGeneratorStrategy;
    }

    private void generateFromJsonConstructor(Definition table, JavaWriter out, GeneratorStrategy.Mode mode){
        final String className = getStrategy().getJavaClassName(table, mode);
        out.println();
        out.tab(1).println("public %s(io.vertx.core.json.JsonObject json) {", className);
        out.tab(2).println("this();"); //call default constructor
        out.tab(2).println("fromJson(json);");
        out.tab(1).println("}");
    }

    /**
     * Copied (more ore less) from JavaGenerator.
     * Generates fetchByCYZ- and fetchOneByCYZ-methods
     * @param table
     * @param out
     */
    protected void generateFetchMethods(TableDefinition table, JavaWriter out){
        VertxJavaWriter vOut = (VertxJavaWriter) out;
        String pType = vOut.ref(getStrategy().getFullJavaClassName(table, GeneratorStrategy.Mode.POJO));
        UniqueKeyDefinition primaryKey = table.getPrimaryKey();
        ColumnDefinition firstPrimaryKeyColumn = primaryKey.getKeyColumns().get(0);
        List<IndexDefinition> indexes = table.getIndexes();
        for (ColumnDefinition column : table.getColumns()) {
            final String colName = column.getOutputName();
            final String colClass = getStrategy().getJavaClassName(column);
            final String colType = vOut.ref(getJavaType(column.getType(), out));
            final String colIdentifier = vOut.ref(getStrategy().getFullJavaIdentifier(column), colRefSegments(column));


            //fetchById is already defined in VertxDAO
            if(!firstPrimaryKeyColumn.equals(column)){


                // fetchBy[Column]([T]...)
                // -----------------------

                generateFindManyByMethods(out, pType, colName, colClass, colType, colIdentifier);
                generateFindManyByLimitMethods(out, pType, colName, colClass, colType, colIdentifier);
            }

        }
        for (IndexDefinition index : indexes) {
            if(index.isUnique()
                    && index.getIndexColumns().size() == 1
            ){
                ColumnDefinition column = index.getIndexColumns().get(0).getColumn();
                if(column.equals(firstPrimaryKeyColumn)){
                    continue;
                }
                final String colName = column.getOutputName();
                final String colClass = getStrategy().getJavaClassName(column);
                final String colType = vOut.ref(getJavaType(column.getType(), out));
                final String colIdentifier = vOut.ref(getStrategy().getFullJavaIdentifier(column), colRefSegments(column));
                generateFindOneByMethods(out, pType, colName, colClass, colType, colIdentifier);
            }
        }
    }

    protected void generateFindOneByMethods(JavaWriter out, String pType, String colName, String colClass, String colType, String colIdentifier) {
        out.tab(1).javadoc("Find a unique record that has <code>%s = value</code> asynchronously", colName);
        out.tab(1).println("public %s findOneBy%s(%s value) {", renderFindOneType(pType),colClass, colType);
        out.tab(2).println("return findOneByCondition(%s.eq(value));", colIdentifier);
        out.tab(1).println("}");
    }

    protected void generateFindManyByMethods(JavaWriter out, String pType, String colName, String colClass, String colType, String colIdentifier) {
        out.tab(1).javadoc("Find records that have <code>%s IN (values)</code> asynchronously", colName);
        out.tab(1).println("public %s findManyBy%s(%s<%s> values) {", renderFindManyType(pType), colClass, Collection.class, colType);
        //out.tab(2).println("return findMany(%s, values);", colIdentifier);
        out.tab(2).println("return findManyByCondition(%s.in(values));", colIdentifier);
        out.tab(1).println("}");
    }

    protected void generateFindManyByLimitMethods(JavaWriter out, String pType, String colName, String colClass, String colType, String colIdentifier) {
        out.tab(1).javadoc("Find records that have <code>%s IN (values)</code> asynchronously limited by the given limit", colName);
        out.tab(1).println("public %s findManyBy%s(%s<%s> values, int limit) {", renderFindManyType(pType), colClass, Collection.class, colType);
        //out.tab(2).println("return findMany(%s, values);", colIdentifier);
        out.tab(2).println("return findManyByCondition(%s.in(values),limit);", colIdentifier);
        out.tab(1).println("}");
    }

    /**
     * Copied from JavaGenerator
     * @param key
     * @return
     */
    public String getKeyType(UniqueKeyDefinition key, JavaWriter out){
        String tType;

        List<ColumnDefinition> keyColumns = key.getKeyColumns();

        if (keyColumns.size() == 1) {
            tType = getJavaType(keyColumns.get(0).getType(), out);
        }
        else if (keyColumns.size() <= Constants.MAX_ROW_DEGREE) {
            String generics = "";
            String separator = "";

            for (ColumnDefinition column : keyColumns) {
                generics += separator + (getJavaType(column.getType(), out));
                separator = ", ";
            }

            tType = Record.class.getName() + keyColumns.size() + "<" + generics + ">";
        }
        else {
            tType = Record.class.getName();
        }

        return tType;
    }

    /**
     * Copied from JavaGenerator
     * @param column
     * @return
     */
    private int colRefSegments(TypedElementDefinition<?> column) {
        if (column != null && column.getContainer() instanceof UDTDefinition)
            return 2;

        if (!getStrategy().getInstanceFields())
            return 2;

        return 3;
    }

    /**
     * copied from jOOQ's JavaGenerator
     * @param table
     * @param out1
     */
    @Override
    protected void generateDao(TableDefinition table, JavaWriter out1) {
        UniqueKeyDefinition key = table.getPrimaryKey();
        if (key == null) {
            logger.info("Skipping DAO generation", out1.file().getName());
            return;
        }
        VertxJavaWriter out = (VertxJavaWriter) out1;
        generateDAO(key, table, out);
    }

    //TODO --> remove copy from JavaGenerator start

    @Override
    protected void generatePojo(TableDefinition tableOrUDT, JavaWriter out) {
        final String className = getStrategy().getJavaClassName(tableOrUDT, GeneratorStrategy.Mode.POJO);
        final String interfaceName = generateInterfaces()
                ? out.ref(getStrategy().getFullJavaClassName(tableOrUDT, GeneratorStrategy.Mode.INTERFACE))
                : "";
        final String superName = out.ref(getStrategy().getJavaClassExtends(tableOrUDT, GeneratorStrategy.Mode.POJO));
        final List<String> interfaces = out.ref(getStrategy().getJavaClassImplements(tableOrUDT, GeneratorStrategy.Mode.POJO));
        final List<String> superTypes = list(superName, interfaces);

        if (generateInterfaces())
            interfaces.add(interfaceName);

        printPackage(out, tableOrUDT, GeneratorStrategy.Mode.POJO);

        if (tableOrUDT instanceof TableDefinition)
            generatePojoClassJavadoc((TableDefinition) tableOrUDT, out);
        else
            generateUDTPojoClassJavadoc((UDTDefinition) tableOrUDT, out);

        printClassAnnotations(out, tableOrUDT.getSchema());
        //THE FOLLOWING LINE HAS BEEN ADDED
        generatePojoClassAnnotations(out,tableOrUDT);

        if (tableOrUDT instanceof TableDefinition)
            printTableJPAAnnotation(out, (TableDefinition) tableOrUDT);

        int maxLength = 0;
        for (TypedElementDefinition<?> column : getTypedElements(tableOrUDT))
            maxLength = Math.max(maxLength, out.ref(getJavaType(column.getType(resolver(out, GeneratorStrategy.Mode.POJO)), out, GeneratorStrategy.Mode.POJO)).length());

        out.println("public class %s[[before= extends ][%s]][[before= implements ][%s]] {", className, list(superName), interfaces);

        if (generateSerializablePojos() || generateSerializableInterfaces())
            out.printSerial();

        out.println();

        for (TypedElementDefinition<?> column : getTypedElements(tableOrUDT)) {
            out.tab(1).println("private %s%s %s;",
                    generateImmutablePojos() ? "final " : "",
                    StringUtils.rightPad(out.ref(getJavaType(column.getType(resolver(out, GeneratorStrategy.Mode.POJO)), out, GeneratorStrategy.Mode.POJO)), maxLength),
                    getStrategy().getJavaMemberName(column, GeneratorStrategy.Mode.POJO));
        }

        // Constructors
        // ---------------------------------------------------------------------

        // Default constructor
        if (!generateImmutablePojos())
            generatePojoDefaultConstructor(tableOrUDT, out);

        // [#1363] [#7055] copy constructor
        generatePojoCopyConstructor(tableOrUDT, out);

        // Multi-constructor
        generatePojoMultiConstructor(tableOrUDT, out);

        List<? extends TypedElementDefinition<?>> elements = getTypedElements(tableOrUDT);
        for (int i = 0; i < elements.size(); i++) {
            TypedElementDefinition<?> column = elements.get(i);

            if (tableOrUDT instanceof TableDefinition)
                generatePojoGetter(column, i, out);
            else
                generateUDTPojoGetter(column, i, out);

            // Setter
            if (!generateImmutablePojos())
                if (tableOrUDT instanceof TableDefinition)
                    generatePojoSetter(column, i, out);
                else
                    generateUDTPojoSetter(column, i, out);
        }

        if (generatePojosEqualsAndHashCode())
            generatePojoEqualsAndHashCode(tableOrUDT, out);

        if (generatePojosToString())
            generatePojoToString(tableOrUDT, out);

        if (generateInterfaces() && !generateImmutablePojos())
            printFromAndInto(out, tableOrUDT);

        if (tableOrUDT instanceof TableDefinition)
            generatePojoClassFooter((TableDefinition) tableOrUDT, out);
        else
            generateUDTPojoClassFooter((UDTDefinition) tableOrUDT, out);

        out.println("}");
        closeJavaWriter(out);
    }

    private static final <T> List<T> list(T... objects) {
        List<T> result = new ArrayList<>();

        if (objects != null)
            for (T object : objects)
                if (object != null && !"".equals(object))
                    result.add(object);

        return result;
    }

    private static final <T> List<T> list(T first, List<T> remaining) {
        List<T> result = new ArrayList<>();

        result.addAll(list(first));
        result.addAll(remaining);

        return result;
    }

    private List<? extends TypedElementDefinition<? extends Definition>> getTypedElements(Definition definition) {
        if (definition instanceof TableDefinition)
            return ((TableDefinition) definition).getColumns();
        else if (definition instanceof EmbeddableDefinition)
            return ((EmbeddableDefinition) definition).getColumns();
        else if (definition instanceof UDTDefinition)
            return ((UDTDefinition) definition).getAttributes();
        else if (definition instanceof RoutineDefinition)
            return ((RoutineDefinition) definition).getAllParameters();
        else
            throw new IllegalArgumentException("Unsupported type : " + definition);
    }

    //TODO remove copy from JavaGenerator end <--

    protected void generatePojoClassAnnotations(JavaWriter out, TableDefinition schema) {
    }

    private void generateDAO(UniqueKeyDefinition key, TableDefinition table, VertxJavaWriter out) {
        final String className = getStrategy().getJavaClassName(table, GeneratorStrategy.Mode.DAO);
        final List<String> interfaces = out.ref(getStrategy().getJavaClassImplements(table, GeneratorStrategy.Mode.DAO));
        final String tableRecord = out.ref(getStrategy().getFullJavaClassName(table, GeneratorStrategy.Mode.RECORD));
        final String daoImpl = out.ref(renderDaoExtendsClassName());
        final String tableIdentifier = out.ref(getStrategy().getFullJavaIdentifier(table), 2);

        String tType = "Void";
        String pType = out.ref(getStrategy().getFullJavaClassName(table, GeneratorStrategy.Mode.POJO));

        List<ColumnDefinition> keyColumns = key.getKeyColumns();

        if (keyColumns.size() == 1) {
            tType = getJavaType(keyColumns.get(0).getType(), out);
        }
        else if (keyColumns.size() <= Constants.MAX_ROW_DEGREE) {
            String generics = "";
            String separator = "";

            for (ColumnDefinition column : keyColumns) {
                generics += separator + out.ref(getJavaType(column.getType(), out));
                separator = ", ";
            }

            tType = Record.class.getName() + keyColumns.size() + "<" + generics + ">";
        }
        else {
            tType = Record.class.getName();
        }

        tType = out.ref(tType);
        interfaces.add(renderDAOInterface(tableRecord, pType, tType)); //let DAO implement the right DAO-interface

        printPackage(out, table, GeneratorStrategy.Mode.DAO);
        generateDaoClassJavadoc(table, out);
        printClassAnnotations(out, table.getSchema());

        if (generateSpringAnnotations())
            out.println("@%s", out.ref("org.springframework.stereotype.Repository"));
        writeDAOClassAnnotation(out);
        out.println("public class %s extends %s<%s, %s, %s, %s, %s, %s, %s>[[before= implements ][%s]] {",
                className,
                daoImpl,
                tableRecord,
                pType,
                tType,
                renderFindManyType(pType),
                renderFindOneType(pType),
                renderExecType(),
                renderInsertReturningType(tType),
                interfaces);

        // Only one constructor
        // ------------------------

        if (generateSpringAnnotations()){
            out.tab(1).println("@%s", out.ref("org.springframework.beans.factory.annotation.Autowired"));
        }

        writeDAOConstructorAnnotation(out);
        writeDAOConstructor(out, className, tableIdentifier, tableRecord, pType, tType, table.getSchema().getName());

        // Template method implementations
        // -------------------------------
        out.tab(1).overrideInherit();
        out.tab(1).println("protected %s getId(%s object) {", tType, pType);

        if (keyColumns.size() == 1) {
            out.tab(2).println("return object.%s();", getStrategy().getJavaGetterName(keyColumns.get(0), GeneratorStrategy.Mode.POJO));
        }

        // [#2574] This should be replaced by a call to a method on the target table's Key type
        else {
            String params = "";
            String separator = "";

            for (ColumnDefinition column : keyColumns) {
                params += separator + "object." + getStrategy().getJavaGetterName(column, GeneratorStrategy.Mode.POJO) + "()";

                separator = ", ";
            }

            out.tab(2).println("return compositeKeyRecord(%s);", params);
        }

        out.tab(1).println("}");
        generateFetchMethods(table,out);
        generateDaoClassFooter(table, out);
        overwriteDAOMethods(table.getSchema(),out, className, tableIdentifier, tableRecord, pType, tType);
        out.println("}");
    }

}
