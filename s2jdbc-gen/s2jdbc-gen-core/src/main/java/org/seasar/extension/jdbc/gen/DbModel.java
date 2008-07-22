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
package org.seasar.extension.jdbc.gen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * データベースのモデルです。
 * 
 * @author taedium
 */
public class DbModel {

    /** 方言 */
    protected GenDialect dialect;

    /** 区切り文字 */
    protected char delimiter;

    /** テーブル記述のリスト */
    protected List<TableDesc> tableDescList = new ArrayList<TableDesc>();

    /** シーケンス記述のリスト */
    protected List<SequenceDesc> sequenceDescList = new ArrayList<SequenceDesc>();

    /**
     * 方言を返します。
     * 
     * @return 方言
     */
    public GenDialect getDialect() {
        return dialect;
    }

    /**
     * 方言を設定します。
     * 
     * @param dialect
     *            方言
     */
    public void setDialect(GenDialect dialect) {
        this.dialect = dialect;
    }

    /**
     * 区切り文字を返します。
     * 
     * @return 区切り文字
     */
    public char getDelimiter() {
        return delimiter;
    }

    /**
     * 区切り文字を設定します。
     * 
     * @param delimiter
     *            区切り文字
     */
    public void setDelimiter(char delimiter) {
        this.delimiter = delimiter;
    }

    /**
     * テーブル記述を追加します。
     * 
     * @param tableDesc
     *            テーブル記述
     */
    public void addTableDesc(TableDesc tableDesc) {
        if (tableDescList.contains(tableDesc)) {
            return;
        }
        tableDescList.add(tableDesc);
    }

    /**
     * テーブル記述のリストを返します。
     * 
     * @return テーブル記述のリスト
     */
    public List<TableDesc> getTableDescList() {
        return Collections.unmodifiableList(tableDescList);
    }

    /**
     * シーケンス記述を追加します。
     * 
     * @param sequenceDesc
     *            シーケンス記述
     */
    public void addSequenceDesc(SequenceDesc sequenceDesc) {
        if (sequenceDescList.contains(sequenceDesc)) {
            return;
        }
        sequenceDescList.add(sequenceDesc);
    }

    /**
     * シーケンス記述のリストを返します。
     * 
     * @return シーケンス記述のリスト
     */
    public List<SequenceDesc> getSequenceDescList() {
        return Collections.unmodifiableList(sequenceDescList);
    }

    /**
     * シーケンス定義の断片を返します。
     * 
     * @param sequenceDesc
     *            シーケンス記述
     * @return シーケンス定義の断片
     */
    public String getSequenceDefinitionFragment(SequenceDesc sequenceDesc) {
        return dialect.getSequenceDefinitionFragment(sequenceDesc.getDataType(),
                sequenceDesc.getInitialValue(), sequenceDesc
                        .getAllocationSize());
    }

    /**
     * IDENTITYカラムの定義を返します。
     * 
     * @return IDENTITYカラムの定義
     */
    public String getIdentityColumnDefinition() {
        return dialect.getIdentityColumnDefinition();
    }

}