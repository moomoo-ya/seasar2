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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.seasar.extension.jdbc.EntityMeta;
import org.seasar.extension.jdbc.PropertyMeta;
import org.seasar.extension.jdbc.TableMeta;
import org.seasar.extension.jdbc.gen.ColumnDesc;
import org.seasar.extension.jdbc.gen.ColumnDescFactory;
import org.seasar.extension.jdbc.gen.ForeignKeyDesc;
import org.seasar.extension.jdbc.gen.ForeignKeyDescFactory;
import org.seasar.extension.jdbc.gen.IdTableDescFactory;
import org.seasar.extension.jdbc.gen.PrimaryKeyDesc;
import org.seasar.extension.jdbc.gen.PrimaryKeyDescFactory;
import org.seasar.extension.jdbc.gen.SequenceDesc;
import org.seasar.extension.jdbc.gen.SequenceDescFactory;
import org.seasar.extension.jdbc.gen.TableDesc;
import org.seasar.extension.jdbc.gen.TableDescFactory;
import org.seasar.extension.jdbc.gen.UniqueKeyDesc;
import org.seasar.extension.jdbc.gen.UniqueKeyDescFactory;
import org.seasar.extension.jdbc.gen.util.AnnotationUtil;

/**
 * {@link TableDescFactory}の実装クラスです。
 * 
 * @author taedium
 */
public class TableDescFactoryImpl implements TableDescFactory {

    /** テーブルの完全修飾名をキー、テーブル記述を値とするマップ */
    protected ConcurrentMap<String, TableDesc> tableDescMap = new ConcurrentHashMap<String, TableDesc>(
            200);

    /** カラム記述のファクトリ */
    protected ColumnDescFactory columnDescFactory;

    /** 主キー記述のファクトリ */
    protected PrimaryKeyDescFactory primaryKeyDescFactory;

    /** 外部キー記述のファクトリ */
    protected ForeignKeyDescFactory foreignKeyDescFactory;

    /** 一意キー記述のファクトリ */
    protected UniqueKeyDescFactory uniqueKeyDescFactory;

    /** シーケンス記述のファクトリ */
    protected SequenceDescFactory sequenceDescFactory;

    /** 識別子生成用のテーブル記述のファクトリ */
    protected IdTableDescFactory idTableDescFactory;

    /**
     * インスタンスを構築します。
     * 
     * @param columnDescFactory
     *            カラム記述のファクトリ
     * @param primaryKeyDescFactory
     *            主キー記述のファクトリ
     * @param uniqueKeyDescFactory
     *            一意キー記述のファクトリ
     * @param foreignKeyDescFactory
     *            外部キー記述のファクトリ
     * @param sequenceDescFactory
     *            シーケンス記述のファクトリ
     * @param idTableDescFactory
     *            識別子生成用のテーブル記述のファクトリ
     */
    public TableDescFactoryImpl(ColumnDescFactory columnDescFactory,
            PrimaryKeyDescFactory primaryKeyDescFactory,
            UniqueKeyDescFactory uniqueKeyDescFactory,
            ForeignKeyDescFactory foreignKeyDescFactory,
            SequenceDescFactory sequenceDescFactory,
            IdTableDescFactory idTableDescFactory) {
        if (columnDescFactory == null) {
            throw new NullPointerException("columnDescFactory");
        }
        if (primaryKeyDescFactory == null) {
            throw new NullPointerException("primaryKeyDescFactory");
        }
        if (uniqueKeyDescFactory == null) {
            throw new NullPointerException("uniqueKeyDescFactory");
        }
        if (foreignKeyDescFactory == null) {
            throw new NullPointerException("foreignKeyDescFactory");
        }
        if (sequenceDescFactory == null) {
            throw new NullPointerException("sequenceDescFactory");
        }
        if (idTableDescFactory == null) {
            throw new NullPointerException("idTableDescFactory");
        }
        this.columnDescFactory = columnDescFactory;
        this.primaryKeyDescFactory = primaryKeyDescFactory;
        this.uniqueKeyDescFactory = uniqueKeyDescFactory;
        this.foreignKeyDescFactory = foreignKeyDescFactory;
        this.sequenceDescFactory = sequenceDescFactory;
        this.idTableDescFactory = idTableDescFactory;
    }

    public TableDesc getTableDesc(EntityMeta entityMeta) {
        String fullName = entityMeta.getTableMeta().getFullName().toLowerCase();
        TableDesc tableDesc = tableDescMap.get(fullName);
        if (tableDesc != null) {
            return tableDesc;
        }
        tableDesc = createTableDesc(entityMeta);
        tableDescMap.put(fullName, tableDesc);
        return tableDesc;
    }

    /**
     * テーブル記述を作成します。
     * 
     * @param entityMeta
     *            エンティティメタデータ
     * @return テーブル記述
     */
    protected TableDesc createTableDesc(EntityMeta entityMeta) {
        Table table = getTable(entityMeta);
        TableDesc tableDesc = new TableDesc();
        doName(entityMeta, tableDesc, table);
        doColumnDesc(entityMeta, tableDesc, table);
        doPrimaryKeyDesc(entityMeta, tableDesc, table);
        doForeignKeyDesc(entityMeta, tableDesc, table);
        doUniqueKeyDesc(entityMeta, tableDesc, table);
        doSequenceDesc(entityMeta, tableDesc, table);
        doIdTableDesc(entityMeta, tableDesc, table);
        return tableDesc;
    }

    /**
     * 名前を処理します。
     * 
     * @param entityMeta
     *            エンティティメタデータ
     * @param tableDesc
     *            テーブル記述
     * @param table
     *            テーブル
     */
    protected void doName(EntityMeta entityMeta, TableDesc tableDesc,
            Table table) {
        TableMeta tableMeta = entityMeta.getTableMeta();
        tableDesc.setCatalogName(tableMeta.getCatalog());
        tableDesc.setSchemaName(tableMeta.getSchema());
        tableDesc.setName(tableMeta.getName());
    }

    /**
     * カラム記述を処理します。
     * 
     * @param entityMeta
     *            エンティティメタデータ
     * @param tableDesc
     *            テーブル記述
     * @param table
     *            テーブル
     */
    protected void doColumnDesc(EntityMeta entityMeta, TableDesc tableDesc,
            Table table) {
        for (int i = 0; i < entityMeta.getColumnPropertyMetaSize(); i++) {
            PropertyMeta propertyMeta = entityMeta.getColumnPropertyMeta(i);
            ColumnDesc columnDesc = columnDescFactory
                    .getColumnDesc(propertyMeta);
            if (columnDesc != null) {
                tableDesc.addColumnDesc(columnDesc);
            }
        }
    }

    /**
     * 主キー記述を処理します。
     * 
     * @param entityMeta
     *            エンティティメタデータ
     * @param tableDesc
     *            テーブル記述
     * @param table
     *            テーブル
     */
    protected void doPrimaryKeyDesc(EntityMeta entityMeta, TableDesc tableDesc,
            Table table) {
        PrimaryKeyDesc primaryKeyDesc = primaryKeyDescFactory
                .getPrimaryKeyDesc(entityMeta.getIdPropertyMetaList());
        if (primaryKeyDesc != null) {
            tableDesc.setPrimaryKeyDesc(primaryKeyDesc);
        }
    }

    /**
     * 外部キー記述を処理します。
     * 
     * @param entityMeta
     *            エンティティメタデータ
     * @param tableDesc
     *            テーブル記述
     * @param table
     *            テーブル
     */
    protected void doForeignKeyDesc(EntityMeta entityMeta, TableDesc tableDesc,
            Table table) {
        for (int i = 0; i < entityMeta.getPropertyMetaSize(); i++) {
            PropertyMeta propertyMeta = entityMeta.getPropertyMeta(i);
            ForeignKeyDesc foreignKeyDesc = foreignKeyDescFactory
                    .getForeignKeyDesc(propertyMeta);
            if (foreignKeyDesc != null) {
                tableDesc.addForeignKeyDesc(foreignKeyDesc);
            }
        }
    }

    /**
     * 一意キー記述を処理します。
     * 
     * @param entityMeta
     *            エンティティメタデータ
     * @param tableDesc
     *            テーブル記述
     * @param table
     *            テーブル
     */
    protected void doUniqueKeyDesc(EntityMeta entityMeta, TableDesc tableDesc,
            Table table) {
        for (ColumnDesc columnDesc : tableDesc.getColumnDescList()) {
            UniqueKeyDesc uniqueKeyDesc = uniqueKeyDescFactory
                    .getSingleUniqueKeyDesc(columnDesc);
            if (uniqueKeyDesc != null) {
                tableDesc.addUniqueKeyDesc(uniqueKeyDesc);
            }
        }
        for (UniqueConstraint uc : table.uniqueConstraints()) {
            UniqueKeyDesc uniqueKeyDesc = uniqueKeyDescFactory
                    .getCompositeUniqueKeyDesc(uc);
            if (uniqueKeyDesc != null) {
                tableDesc.addUniqueKeyDesc(uniqueKeyDesc);
            }
        }
    }

    /**
     * シーケンス記述を処理します。
     * 
     * @param entityMeta
     *            エンティティメタデータ
     * @param tableDesc
     *            テーブル記述
     * @param table
     *            テーブル
     */
    protected void doSequenceDesc(EntityMeta entityMeta, TableDesc tableDesc,
            Table table) {
        for (PropertyMeta propertyMeta : entityMeta.getIdPropertyMetaList()) {
            SequenceDesc sequenceDesc = sequenceDescFactory.getSequenceDesc(
                    entityMeta, propertyMeta);
            if (sequenceDesc != null) {
                tableDesc.addSequenceDesc(sequenceDesc);
            }
        }
    }

    /**
     * 識別子生成用のテーブル記述を処理します。
     * 
     * @param entityMeta
     *            エンティティメタデータ
     * @param tableDesc
     *            テーブル記述
     * @param table
     *            テーブル
     */
    protected void doIdTableDesc(EntityMeta entityMeta, TableDesc tableDesc,
            Table table) {
        for (PropertyMeta propertyMeta : entityMeta.getIdPropertyMetaList()) {
            TableDesc idTableDesc = idTableDescFactory.getTableDesc(entityMeta,
                    propertyMeta);
            if (idTableDesc == null) {
                continue;
            }
            tableDesc.addIdTableDesc(idTableDesc);

            String fullName = idTableDesc.getFullName().toLowerCase();
            TableDesc cache = tableDescMap.get(fullName);
            if (cache == null) {
                tableDescMap.put(fullName, idTableDesc);
            } else {
                cache.setCatalogName(idTableDesc.getCatalogName());
                cache.setSchemaName(idTableDesc.getSchemaName());
                cache.setName(idTableDesc.getName());
                cache.setPrimaryKeyDesc(idTableDesc.getPrimaryKeyDesc());
                for (ColumnDesc columnDesc : idTableDesc.getColumnDescList()) {
                    cache.addColumnDesc(columnDesc);
                }
                for (UniqueKeyDesc uniqueKeyDesc : idTableDesc
                        .getUniqueKeyDescList()) {
                    cache.addUniqueKeyDesc(uniqueKeyDesc);
                }
            }
        }
    }

    /**
     * テーブルを取得します。
     * 
     * @param entityMeta
     *            エンティティメタデータ
     * @return テーブル
     */
    protected Table getTable(EntityMeta entityMeta) {
        Class<?> clazz = entityMeta.getEntityClass();
        Table table = clazz.getAnnotation(Table.class);
        return table != null ? table : AnnotationUtil.getDefaultTable();
    }

}
