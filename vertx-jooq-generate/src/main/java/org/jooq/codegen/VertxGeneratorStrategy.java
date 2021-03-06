package org.jooq.codegen;

import io.github.jklingsporn.vertx.jooq.shared.internal.VertxPojo;
import org.jooq.meta.Definition;
import org.jooq.meta.TypedElementDefinition;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * Created by jensklingsporn on 08.02.18.
 */
public class VertxGeneratorStrategy implements GeneratorStrategy {

    private final GeneratorStrategy delegate;

    public VertxGeneratorStrategy(GeneratorStrategy delegate) {
        this.delegate = delegate;
    }

    public VertxGeneratorStrategy(){
        this(new DefaultGeneratorStrategy());
    }

    public String getJsonKeyName(TypedElementDefinition<?> column) {
        return column.getName();
    }

    public String getRowMappersSubPackage(){
        return "mappers";
    }

    @Override
    public List<String> getJavaClassImplements(Definition definition, Mode mode) {
        List<String> javaClassImplements = delegate.getJavaClassImplements(definition, mode);
        if(mode.equals(Mode.INTERFACE) || mode.equals(Mode.POJO) || mode.equals(Mode.RECORD)) {
            //let POJO and RECORD also implement VertxPojo to fix #37
            javaClassImplements.add(VertxPojo.class.getName());
        }
        return javaClassImplements;
    }

    @Override
    public String getGlobalReferencesJavaClassName(Definition definition, Class<? extends Definition> aClass) {
        return delegate.getGlobalReferencesJavaClassName(definition, aClass);
    }

    @Override
    public String getFileName(Definition definition) {
        return delegate.getFileName(definition);
    }

    @Override
    public String getFileName(Definition definition, Mode mode) {
        return delegate.getFileName(definition, mode);
    }

    @Override
    public File getFileRoot() {
        return delegate.getFileRoot();
    }

    @Override
    public File getGlobalReferencesFile(Definition definition, Class<? extends Definition> aClass) {
        return delegate.getGlobalReferencesFile(definition, aClass);
    }

    @Override
    public File getFile(Definition definition) {
        return delegate.getFile(definition);
    }

    @Override
    public File getFile(Definition definition, Mode mode) {
        return delegate.getFile(definition, mode);
    }

    @Override
    public File getFile(String fileName) {
        return delegate.getFile(fileName);
    }

    @Override
    public String getGlobalReferencesFileHeader(Definition definition, Class<? extends Definition> aClass) {
        return delegate.getGlobalReferencesFileHeader(definition, aClass);
    }

    @Override
    public String getFileHeader(Definition definition) {
        return delegate.getFileHeader(definition);
    }

    @Override
    public String getFullJavaIdentifier(Definition definition) {
        return delegate.getFullJavaIdentifier(definition);
    }
    @Override
    public String getJavaSetterName(Definition definition) {
        return delegate.getJavaSetterName(definition);
    }
    @Override
    public String getJavaGetterName(Definition definition) {
        return delegate.getJavaGetterName(definition);
    }
    @Override
    public String getJavaMethodName(Definition definition) {
        return delegate.getJavaMethodName(definition);
    }
    @Override
    public String getJavaClassExtends(Definition definition) {
        return delegate.getJavaClassExtends(definition);
    }
    @Override
    public List<String> getJavaClassImplements(Definition definition) {
        return delegate.getJavaClassImplements(definition);
    }
    @Override
    public String getJavaClassName(Definition definition) {
        return delegate.getJavaClassName(definition);
    }
    @Override
    public String getJavaPackageName(Definition definition) {
        return delegate.getJavaPackageName(definition);
    }
    @Override
    public String getJavaMemberName(Definition definition) {
        return delegate.getJavaMemberName(definition);
    }

    @Override
    public String getFullJavaClassName(Definition definition) {
        return delegate.getFullJavaClassName(definition);
    }

    @Override
    public String getFullJavaClassName(Definition definition, Mode mode) {
        return delegate.getFullJavaClassName(definition, mode);
    }

    @Override
    public String getGlobalReferencesFileName(Definition definition, Class<? extends Definition> aClass) {
        return delegate.getGlobalReferencesFileName(definition, aClass);
    }

    @Override
    public List<String> getJavaIdentifiers(Collection<? extends Definition> definitions) {
        return delegate.getJavaIdentifiers(definitions);
    }
    @Override
    public List<String> getJavaIdentifiers(Definition... definitions) {
        return delegate.getJavaIdentifiers(definitions);
    }
    @Override
    public List<String> getFullJavaIdentifiers(Collection<? extends Definition> definitions) {
        return delegate.getFullJavaIdentifiers(definitions);
    }
    @Override
    public List<String> getFullJavaIdentifiers(Definition... definitions) {
        return delegate.getFullJavaIdentifiers(definitions);
    }
    @Override
    public void setInstanceFields(boolean instanceFields) {
        delegate.setInstanceFields(instanceFields);
    }
    @Override
    public boolean getInstanceFields() {
        return delegate.getInstanceFields();
    }
    @Override
    public void setJavaBeansGettersAndSetters(boolean javaBeansGettersAndSetters) {
        delegate.setJavaBeansGettersAndSetters(javaBeansGettersAndSetters);
    }
    @Override
    public boolean getJavaBeansGettersAndSetters() {
        return delegate.getJavaBeansGettersAndSetters();
    }
    @Override
    public String getTargetDirectory() {
        return delegate.getTargetDirectory();
    }
    @Override
    public void setTargetDirectory(String directory) {
        delegate.setTargetDirectory(directory);
    }
    @Override
    public String getTargetPackage() {
        return delegate.getTargetPackage();
    }
    @Override
    public void setTargetPackage(String packageName) {
        delegate.setTargetPackage(packageName);
    }

    @Override
    public Locale getTargetLocale() {
        return delegate.getTargetLocale();
    }

    @Override
    public void setTargetLocale(Locale locale) {
        delegate.setTargetLocale(locale);
    }

    @Override
    public AbstractGenerator.Language getTargetLanguage() {
        return delegate.getTargetLanguage();
    }

    @Override
    public void setTargetLanguage(AbstractGenerator.Language language) {
        delegate.setTargetLanguage(language);
    }

    @Override
    public String getFileHeader(Definition definition, Mode mode) {
        return delegate.getFileHeader(definition, mode);
    }
    @Override
    public String getJavaIdentifier(Definition definition) {
        return delegate.getJavaIdentifier(definition);
    }
    @Override
    public String getJavaSetterName(Definition definition, Mode mode) {
        return delegate.getJavaSetterName(definition, mode);
    }
    @Override
    public String getJavaGetterName(Definition definition, Mode mode) {
        return delegate.getJavaGetterName(definition, mode);
    }
    @Override
    public String getJavaMethodName(Definition definition, Mode mode) {
        return delegate.getJavaMethodName(definition, mode);
    }

    @Override
    public String getGlobalReferencesJavaClassExtends(Definition definition, Class<? extends Definition> aClass) {
        return delegate.getGlobalReferencesJavaClassExtends(definition, aClass);
    }

    @Override
    public String getJavaClassExtends(Definition definition, Mode mode) {
        return delegate.getJavaClassExtends(definition, mode);
    }

    @Override
    public List<String> getGlobalReferencesJavaClassImplements(Definition definition, Class<? extends Definition> aClass) {
        return delegate.getGlobalReferencesJavaClassImplements(definition, aClass);
    }

    @Override
    public String getJavaClassName(Definition definition, Mode mode) {
        return delegate.getJavaClassName(definition, mode);
    }

    @Override
    public String getGlobalReferencesJavaPackageName(Definition definition, Class<? extends Definition> aClass) {
        return delegate.getGlobalReferencesJavaPackageName(definition, aClass);
    }

    @Override
    public String getJavaPackageName(Definition definition, Mode mode) {
        return delegate.getJavaPackageName(definition, mode);
    }
    @Override
    public String getJavaMemberName(Definition definition, Mode mode) {
        return delegate.getJavaMemberName(definition, mode);
    }

    @Override
    public String getGlobalReferencesFullJavaClassName(Definition definition, Class<? extends Definition> aClass) {
        return delegate.getGlobalReferencesFullJavaClassName(definition, aClass);
    }

    @Override
    public String getOverloadSuffix(Definition definition, Mode mode, String overloadIndex) {
        return delegate.getOverloadSuffix(definition, mode, overloadIndex);
    }

}
