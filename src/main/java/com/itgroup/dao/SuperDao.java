package com.itgroup.dao;

import java.sql.*;

public class SuperDao {
    public SuperDao() {
        // 드라이버 관련 OracleDriver 클래스는 ojdbc6.jar 파일에 포함되어 있는 자바 클래스
        String driver = "oracle.jdbc.driver.OracleDriver";
        try {
            Class.forName(driver); // 동적 객체를 생성하는 문법
        } catch (ClassNotFoundException e) {
            System.out.println("해당 드라이브가 존재하지 않습니다.");
            e.printStackTrace();
        }
    }
    public Connection getConnection() {
        Connection conn = null; // 접속 객체
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String id = "oraman";
        String pw = "oracle";
        try {
            conn = DriverManager.getConnection(url, id, pw);
            System.out.println("접속 성공");
        } catch (SQLException e) {
            System.out.println("접속 실패");
            e.printStackTrace();
        }
        return conn;
    }
    public void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        if(rs != null) try { rs.close();} catch(SQLException ex) {}
        if(pstmt != null) try { pstmt.close();} catch(SQLException ex) {}
        if(conn != null) try { conn.close();} catch(SQLException ex) {}
    }
}
