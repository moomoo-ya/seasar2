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
package org.seasar.extension.jdbc.gen.dialect;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Arrays;

import org.junit.Test;
import org.seasar.extension.jdbc.gen.GenDialect.Type;

import static org.junit.Assert.*;

/**
 * @author taedium
 * 
 */
public class OracleGenDialectTest {

    private OracleGenDialect dialect = new OracleGenDialect();

    /**
     * 
     * @throws Exception
     */
    @Test
    public void testType_decimal() throws Exception {
        Type type = dialect.getType(Types.DECIMAL);
        assertEquals("number(10,5)", type.getColumnDefinition(0, 10, 5, null));
        assertEquals(Integer.class, type.getJavaClass(0, 8, 0, "NUMBER"));
        assertEquals(BigDecimal.class, type.getJavaClass(0, 8, 2, "NUMBER"));
        assertEquals(BigDecimal.class, type.getJavaClass(0, 11, 0, "NUMBER"));

    }

    /**
     * 
     * @throws Exception
     */
    @Test
    public void testType_binary() throws Exception {
        Type dbType = dialect.getType(Types.BINARY);
        assertEquals("raw(2000)", dbType.getColumnDefinition(2000, 0, 0, null));
        assertEquals("long raw", dbType.getColumnDefinition(2001, 0, 0, null));
    }

    /**
     * 
     * @throws Exception
     */
    @Test
    public void testType_varchar() throws Exception {
        Type dbType = dialect.getType(Types.VARCHAR);
        assertEquals("varchar2(4000)", dbType.getColumnDefinition(4000, 0, 0,
                null));
        assertEquals("long", dbType.getColumnDefinition(4001, 0, 0, null));
    }

    /**
     * 
     * @throws Exception
     */
    @Test
    public void testType_bigint() throws Exception {
        Type type = dialect.getType(Types.BIGINT);
        assertEquals("numeric(10,0)", type.getColumnDefinition(0, 10, 0, null));
    }

    /**
     * 
     * @throws Exception
     */
    @Test
    public void testIsSqlBlockStartWords() throws Exception {
        assertTrue(dialect.isSqlBlockStartWords(Arrays.asList("create", "or",
                "replace", "procedure")));
        assertFalse(dialect.isSqlBlockStartWords(Arrays.asList("drop",
                "procedure")));
        assertTrue(dialect.isSqlBlockStartWords(Arrays.asList("begin")));
    }
}