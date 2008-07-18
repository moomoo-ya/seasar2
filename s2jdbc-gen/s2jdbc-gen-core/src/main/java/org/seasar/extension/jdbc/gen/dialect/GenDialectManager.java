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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.seasar.extension.jdbc.DbmsDialect;
import org.seasar.extension.jdbc.dialect.Db2390Dialect;
import org.seasar.extension.jdbc.dialect.Db2400Dialect;
import org.seasar.extension.jdbc.dialect.Db2Dialect;
import org.seasar.extension.jdbc.dialect.DerbyDialect;
import org.seasar.extension.jdbc.dialect.FirebirdDialect;
import org.seasar.extension.jdbc.dialect.H2Dialect;
import org.seasar.extension.jdbc.dialect.HsqlDialect;
import org.seasar.extension.jdbc.dialect.InterbaseDialect;
import org.seasar.extension.jdbc.dialect.MaxdbDialect;
import org.seasar.extension.jdbc.dialect.Mssql2005Dialect;
import org.seasar.extension.jdbc.dialect.MysqlDialect;
import org.seasar.extension.jdbc.dialect.OracleDialect;
import org.seasar.extension.jdbc.dialect.Postgre81Dialect;
import org.seasar.extension.jdbc.dialect.PostgreDialect;
import org.seasar.extension.jdbc.dialect.SybaseDialect;
import org.seasar.extension.jdbc.gen.GenDialect;

/**
 * S2JDBC-Genのデータベースごとの方言を管理するクラスです。
 * 
 * @author taedium
 */
public class GenDialectManager {

    /** DB2 390の方言 */
    protected static Db2390GenDialect DB2_390 = new Db2390GenDialect();

    /** DB2 400の方言 */
    protected static Db2400GenDialect DB2_400 = new Db2400GenDialect();

    /** DB2の方言 */
    protected static Db2GenDialect DB2 = new Db2GenDialect();

    /** Derbyの方言 */
    protected static DerbyGenDialect DERBY = new DerbyGenDialect();

    /** Firebirdの方言 */
    protected static FirebirdGenDialect FIREBIRD = new FirebirdGenDialect();

    /** H2の方言 */
    protected static H2GenDialect H2 = new H2GenDialect();

    /** HSQLDBの方言 */
    protected static HsqlGenDialect HSQL = new HsqlGenDialect();

    /** Interbaseの方言 */
    protected static InterbaseGenDialect INTERBASE = new InterbaseGenDialect();

    /** MaxDBの方言 */
    protected static MaxdbGenDialect MAXDB = new MaxdbGenDialect();

    /** MS SQL Server 2005の方言 */
    protected static Mssql2005GenDialect MSSQL_2005 = new Mssql2005GenDialect();

    /** MS SQL Serverの方言 */
    protected static MssqlGenDialect MSSQL = new MssqlGenDialect();

    /** MySQLの方言 */
    protected static MysqlGenDialect MYSQL = new MysqlGenDialect();

    /** Oracleの方言 */
    protected static OracleGenDialect ORACLE = new OracleGenDialect();

    /** PostgreSQLの方言 */
    protected static PostgreGenDialect POSTGRE = new PostgreGenDialect();

    /** PostgreSQL 8.1の方言 */
    protected static Postgre81GenDialect POSTGRE81 = new Postgre81GenDialect();

    /** Sybaseの方言 */
    protected static SybaseGenDialect SYBASE = new SybaseGenDialect();

    /** 標準的な方言 */
    protected static StandardGenDialect STANDARD = new StandardGenDialect();

    /** {@link DbmsDialect}のクラス名をキー、 {@link GenDialect}を値とするマップ */
    protected static Map<String, GenDialect> dialectMap = new ConcurrentHashMap<String, GenDialect>();
    static {
        dialectMap.put(Db2390Dialect.class.getName(), DB2_390);
        dialectMap.put(Db2400Dialect.class.getName(), DB2_400);
        dialectMap.put(Db2Dialect.class.getName(), DB2);
        dialectMap.put(DerbyDialect.class.getName(), DERBY);
        dialectMap.put(FirebirdDialect.class.getName(), FIREBIRD);
        dialectMap.put(H2Dialect.class.getName(), H2);
        dialectMap.put(HsqlDialect.class.getName(), HSQL);
        dialectMap.put(InterbaseDialect.class.getName(), INTERBASE);
        dialectMap.put(MaxdbDialect.class.getName(), MAXDB);
        dialectMap.put(Mssql2005Dialect.class.getName(), MSSQL_2005);
        dialectMap.put(MysqlDialect.class.getName(), MYSQL);
        dialectMap.put(OracleDialect.class.getName(), ORACLE);
        dialectMap.put(PostgreDialect.class.getName(), POSTGRE);
        dialectMap.put(Postgre81Dialect.class.getName(), POSTGRE81);
        dialectMap.put(SybaseDialect.class.getName(), SYBASE);
    }

    private GenDialectManager() {
    }

    /**
     * S2JDBC-Genのデータベースごとの方言を返します。
     * 
     * @param dbmsDialect
     *            データベースごとの方言
     * @return S2JDBC-Genのデータベースごとの方言
     */
    public static GenDialect getGenDialect(DbmsDialect dbmsDialect) {
        if (dbmsDialect == null) {
            throw new NullPointerException("dbmsDialect");
        }
        GenDialect dialect = dialectMap.get(dbmsDialect.getClass().getName());
        if (dialect != null) {
            return dialect;
        }
        return STANDARD;
    }

    /**
     * S2JDBC-Genのデータベースごとの方言を登録します。
     * 
     * @param dbmsDialect
     *            データベースごとの方言
     * @param genDialect
     *            S2JDBC-Genのデータベースごとの方言
     */
    public static void registerGenDialect(DbmsDialect dbmsDialect,
            GenDialect genDialect) {
        if (dbmsDialect == null) {
            throw new NullPointerException("dbmsDialect");
        }
        if (dbmsDialect == null) {
            throw new NullPointerException("genDialect");
        }
        dialectMap.put(dbmsDialect.getClass().getName(), genDialect);
    }

}
