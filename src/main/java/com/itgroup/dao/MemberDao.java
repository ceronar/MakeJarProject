package com.itgroup.dao;

import java.sql.*;

// 데이터 베이스와 직접 연동하여 CRUD 작업을 수행해주는 DAO 클래스
public class MemberDao {
    public MemberDao() {
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

    public int getSize() {
        String sql = "select count(*) as cnt from members";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int cnt = 0; // 검색된 회원 수
        try {
            conn = this.getConnection(); // 접속 객체 구하기
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                cnt = rs.getInt("cnt");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return cnt;
    }
}
