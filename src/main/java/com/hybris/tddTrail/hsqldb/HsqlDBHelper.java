package com.hybris.tddTrail.hsqldb;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class HsqlDBHelper {
	
	private Connection conn;
	
	private final String HSQLDB = "jdbc:hsqldb:file:./../hybris-commerce-suite-6.2.0.1/hybris/data/hsqldb/mydb";
	
	public HsqlDBHelper() throws Exception{
       Class.forName("org.hsqldb.jdbcDriver");        // Load the HSQL Database Engine JDBC driver
       conn = DriverManager.getConnection(HSQLDB,  "sa",   "");   
	 
	}

    public void shutdown( ) throws SQLException {
        Statement st = conn.createStatement();
        st.execute("SHUTDOWN");
        conn.close();  
    }

    public synchronized String select( String expression) throws SQLException {
        Statement st = null;
        ResultSet rs = null;
        st = conn.createStatement(); 
        rs = st.executeQuery(expression);  
        // do something with the result set.
        String res = dump(rs);
        st.close();   
        return res;
    }

    public synchronized void update( String expression) throws SQLException { //  for SQL commands CREATE, DROP, INSERT and UPDATE
        Statement st = null;
        st = conn.createStatement();    // statements
        int i = st.executeUpdate(expression);    // run the query
        if (i == -1) {
            System.out.println("db error : " + expression);
        }
        st.close();
    }    

    public String dump(ResultSet rs) throws SQLException {
        ResultSetMetaData meta   = rs.getMetaData();
        int               colmax = meta.getColumnCount();
        int               i;
        String            o;
        String result = "";     
        while(rs.next()) {
            for (i = 1; i <= colmax; i++) {
            	if (i>1)
            		result = result.concat(" ");
                o = rs.getObject(i ).toString();    
                result = result.concat(o);
            }
            result.concat("\n");
        }
        return result;
    }                                      

    	
}  