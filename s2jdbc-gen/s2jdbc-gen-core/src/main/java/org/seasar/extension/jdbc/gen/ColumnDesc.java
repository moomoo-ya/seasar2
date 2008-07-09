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

/**
 * カラム記述です。
 * 
 * @author taedium
 */
public class ColumnDesc {

    protected final Key key = new Key();

    /** 名前 */
    protected String name;

    /** 定義 */
    protected String definition;

    /** {@code null}可能ならば{@code true} */
    protected boolean nullable;

    /** 一意ならば{@code true} */
    protected boolean unique;

    /**
     * 名前を返します。
     * 
     * @return 名前
     */
    public String getName() {
        return name;
    }

    /**
     * 名前を設定します。
     * 
     * @param name
     *            名前
     */
    public void setName(String name) {
        this.name = name;
        key.setName(name);
    }

    /**
     * {@code null}可能の場合{@code true}を返します。
     * 
     * @return {@code null}可能の場合{@code true}、そうでない場合{@code false}
     */
    public boolean isNullable() {
        return nullable;
    }

    /**
     * {@code null}可能の場合{@code true}を設定します。
     * 
     * @param nullable
     *            {@code null}可能の場合{@code true}、そうでない場合{@code false}
     */
    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    /**
     * カラム定義を返します。
     * 
     * @return カラム定義
     */
    public String getDefinition() {
        return definition;
    }

    /**
     * カラム定義を設定します。
     * 
     * @param definition
     *            カラム定義
     */
    public void setDefinition(String definition) {
        this.definition = definition;
    }

    /**
     * 一意の場合{@code true}、そうでない場合{@code false}を返します。
     * 
     * @return 一意の場合{@code true}、そうでない場合{@code false}
     */
    public boolean isUnique() {
        return unique;
    }

    /**
     * 一意の場合{@code true}、そうでない場合{@code false}を設定します。
     * 
     * @param unique
     *            一意の場合{@code true}、そうでない場合{@code false}
     */
    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ColumnDesc other = (ColumnDesc) obj;
        return key.equals(other.key);
    }

    protected static class Key {

        private String name;

        public void setName(String name) {
            if (name != null) {
                this.name = name.toLowerCase();
            }
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Key other = (Key) obj;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name)) {
                return false;
            }
            return true;
        }
    }

}
