/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.extension.jdbc.gen.desc;

import java.util.List;

import org.seasar.extension.jdbc.EntityMeta;
import org.seasar.extension.jdbc.EntityMetaFactory;
import org.seasar.extension.jdbc.gen.ColumnDescFactory;
import org.seasar.extension.jdbc.gen.DatabaseDesc;
import org.seasar.extension.jdbc.gen.DatabaseDescFactory;
import org.seasar.extension.jdbc.gen.EntityMetaReader;
import org.seasar.extension.jdbc.gen.ForeignKeyDescFactory;
import org.seasar.extension.jdbc.gen.GenDialect;
import org.seasar.extension.jdbc.gen.IdTableDescFactory;
import org.seasar.extension.jdbc.gen.PrimaryKeyDescFactory;
import org.seasar.extension.jdbc.gen.SequenceDescFactory;
import org.seasar.extension.jdbc.gen.TableDesc;
import org.seasar.extension.jdbc.gen.TableDescFactory;
import org.seasar.extension.jdbc.gen.UniqueKeyDescFactory;

/**
 * {@link DatabaseDescFactory}の実装です。
 * 
 * @author taedium
 */
public class DatabaseDescFactoryImpl implements DatabaseDescFactory {

    /** エンティティメタデータのファクトリ */
    protected EntityMetaFactory entityMetaFactory;

    /** エンティティメタデータのリーダ */
    protected EntityMetaReader entityMetaReader;

    /** 方言 */
    protected GenDialect dialect;

    /** テーブル記述のファクトリ */
    protected TableDescFactory tableDescFactory;

    /**
     * インスタンスを構築します。
     * 
     * @param entityMetaFactory
     *            エンティティメタデータのファクトリ
     * @param entityMetaReader
     *            エンティティメタデータのリーダ
     * @param dialect
     *            方言
     */
    public DatabaseDescFactoryImpl(EntityMetaFactory entityMetaFactory,
            EntityMetaReader entityMetaReader, GenDialect dialect) {
        this.entityMetaFactory = entityMetaFactory;
        this.entityMetaReader = entityMetaReader;
        this.dialect = dialect;
        this.tableDescFactory = createTableDescFactory();
    }

    public DatabaseDesc getDatabaseDesc() {
        DatabaseDesc databaseDesc = new DatabaseDesc();
        List<EntityMeta> entityMetaList = entityMetaReader.read();
        for (EntityMeta entityMeta : entityMetaList) {
            TableDesc tableDesc = tableDescFactory.getTableDesc(entityMeta);
            databaseDesc.addTableDesc(tableDesc);
            for (TableDesc idTableDesc : tableDesc.getIdTableDescList()) {
                databaseDesc.addTableDesc(idTableDesc);
            }
        }
        return databaseDesc;
    }

    /**
     * テーブル記述のファクトリを作成します。
     * 
     * @return テーブル記述のファクトリ
     */
    protected TableDescFactory createTableDescFactory() {
        ColumnDescFactory colFactory = new ColumnDescFactoryImpl(dialect);
        PrimaryKeyDescFactory pkFactory = new PrimaryKeyDescFactoryImpl(dialect);
        UniqueKeyDescFactory ukFactory = new UniqueKeyDescFactoryImpl();
        ForeignKeyDescFactory fkFactory = new ForeignKeyDescFactoryImpl(
                entityMetaFactory);
        SequenceDescFactory seqFactory = new SequenceDescFactoryImpl(dialect);
        IdTableDescFactory idTabFactory = new IdTableDescFactoryImpl(dialect,
                colFactory, pkFactory, ukFactory);
        return new TableDescFactoryImpl(colFactory, pkFactory, ukFactory,
                fkFactory, seqFactory, idTabFactory);
    }

}
