package com.itgroup.dao;

import com.itgroup.bean.Member;
import oracle.jdbc.proxy.annotation.Pre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                e.printStackTrace();
            }
        }
        return cnt;
    }

    public List<Member> selectAll() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM MEMBERS ORDER BY NAME ASC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Member bean = new Member();
                bean.setId(rs.getString("id"));
                bean.setName(rs.getString("name"));
                bean.setPassword(rs.getString("password"));
                bean.setGender(rs.getString("gender"));
                bean.setBirth(rs.getString("birth"));
                bean.setMarriage(rs.getString("marriage"));
                bean.setSalary(rs.getInt("salary"));
                bean.setAddress(rs.getString("address"));
                bean.setManager(rs.getString("manager"));
                members.add(bean);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.close(rs, pstmt, conn);
        }
        return members;
    }

    public List<Member> findByGender(String gender) {
        // 성별 컬럼 GENDER를 사용하여 특정 성별의 회원들만 조회
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM MEMBERS WHERE GENDER = ? ORDER BY NAME ASC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, gender);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Member bean = new Member();
                bean.setId(rs.getString("id"));
                bean.setName(rs.getString("name"));
                bean.setPassword(rs.getString("password"));
                bean.setGender(rs.getString("gender"));
                bean.setBirth(rs.getString("birth"));
                bean.setMarriage(rs.getString("marriage"));
                bean.setSalary(rs.getInt("salary"));
                bean.setAddress(rs.getString("address"));
                bean.setManager(rs.getString("manager"));
                members.add(bean);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.close(rs, pstmt, conn);
        }
        return members;
    }

    public Member getMemberOne(String id) {
        // 로그인한 사용자 id 정보를 이용하여 해당 사용자의 정보를 bean 형태로 반환
        Member bean = null; // 찾고자 하는 회원 정보
        String sql = "SELECT * FROM MEMBERS WHERE ID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                bean = new Member();
                bean.setId(rs.getString("id"));
                bean.setName(rs.getString("name"));
                bean.setPassword(rs.getString("password"));
                bean.setGender(rs.getString("gender"));
                bean.setBirth(rs.getString("birth"));
                bean.setMarriage(rs.getString("marriage"));
                bean.setSalary(rs.getInt("salary"));
                bean.setAddress(rs.getString("address"));
                bean.setManager(rs.getString("manager"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.close(rs, pstmt, conn);
        }
        return bean;
    }

    public int deleteData(String id) { // 기본키를 사용하여 회원 탈퇴를 시도합니다.
        int cnt = -1;
        String sql = "delete from members where id = ?";
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            cnt = pstmt.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
           e.printStackTrace();
        } finally {
            this.close(null, pstmt, conn);
        }
        return cnt;
    }
}
