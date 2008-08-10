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
import java.util.Map;

import org.seasar.framework.util.CaseInsensitiveMap;

/**
 * データベース記述です。
 * 
 * @author taedium
 */
public class DatabaseDesc {

    /** テーブル記述のリスト */
    protected List<TableDesc> tableDescList = new ArrayList<TableDesc>();

    /** 完全なテーブル名をキー、テーブル記述を値としたマップ */
    @SuppressWarnings("unchecked")
    protected Map<String, TableDesc> tableDescMap = new CaseInsensitiveMap();

    /**
     * テーブル記述のリストを返します。
     * 
     * @return テーブル記述のリスト
     */
    public List<TableDesc> getTableDescList() {
        return Collections.unmodifiableList(tableDescList);
    }

    /**
     * テーブル記述を追加します。
     * 
     * @param tableDesc
     *            テーブル記述
     */
    public void addTableDesc(TableDesc tableDesc) {
        if (!tableDescList.contains(tableDesc)) {
            tableDescList.add(tableDesc);
            tableDescMap.put(tableDesc.getFullName(), tableDesc);
        }
    }

    /**
     * テーブル記述を返します。
     * 
     * @param fullTableName
     *            完全なテーブル名
     * @return 存在する場合テーブル記述、存在しない場合{@code null}
     */
    public TableDesc getTableDesc(String fullTableName) {
        return tableDescMap.get(fullTableName);
    }
}
