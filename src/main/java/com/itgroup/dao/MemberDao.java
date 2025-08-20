package com.itgroup.dao;

import com.itgroup.bean.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// 데이터 베이스와 직접 연동하여 CRUD 작업을 수행해주는 DAO 클래스
public class MemberDao extends SuperDao {
    public MemberDao() {
        super();
    }

    private Member makeBean(ResultSet rs) {
        Member bean = null;
        try {
            bean = new Member();
            bean.setId(rs.getString("id"));
            bean.setName(rs.getString("name"));
            bean.setPassword(rs.getString("password"));
            bean.setGender(rs.getString("gender"));
            bean.setBirth(String.valueOf(rs.getDate("birth")));
            bean.setMarriage(rs.getString("marriage"));
            bean.setSalary(rs.getInt("salary"));
            bean.setAddress(rs.getString("address"));
            bean.setManager(rs.getString("manager"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bean;
    }

    public int getSize() {
        String sql = "select count(*) as cnt from members";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int cnt = 0; // 검색된 회원 수
        try {
            conn = super.getConnection(); // 접속 객체 구하기
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                cnt = rs.getInt("cnt");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(rs, pstmt, conn);
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
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Member bean = this.makeBean(rs);
                members.add(bean);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            super.close(rs, pstmt, conn);
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
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, gender);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Member bean = this.makeBean(rs);
                members.add(bean);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            super.close(rs, pstmt, conn);
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
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                bean = this.makeBean(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            super.close(rs, pstmt, conn);
        }
        return bean;
    }

    public int deleteData(String id) { // 기본키를 사용하여 회원 탈퇴를 시도합니다.
        int cnt = -1;
        String sql = "delete from members where id = ?";
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            conn = super.getConnection();
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
            super.close(null, pstmt, conn);
        }
        return cnt;
    }

    public int insertData(Member member) {
        // 웹 페이지에서 회원 정보를 입력하고 '가입' 버튼을 누름
        int cnt = -1;
        String sql = "insert into members(id, name, password, gender, birth, marriage, salary, address, manager)";
        sql += " values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, member.getId());
            pstmt.setString(2, member.getName());
            pstmt.setString(3, member.getPassword());
            pstmt.setString(4, member.getGender());
            pstmt.setString(5, member.getBirth());
            pstmt.setString(6, member.getMarriage());
            pstmt.setInt(7, member.getSalary());
            pstmt.setString(8, member.getAddress());
            pstmt.setString(9, member.getManager());
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
            super.close(null, pstmt, conn);
        }
        return cnt;
    }

    public int updateData(Member bean) {
        // 수정된 나의 정보 bean을 사용하여 데이터 베이스에 수정
        int cnt = -1;

        String sql ="update members set name = ?, password = ?, gender = ?, birth = ?, marriage = ?, salary = ?, address = ?, manager = ?";
        sql += " where id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bean.getName());
            pstmt.setString(2, bean.getPassword());
            pstmt.setString(3, bean.getGender());
            pstmt.setString(4, bean.getBirth());
            pstmt.setString(5, bean.getMarriage());
            pstmt.setInt(6, bean.getSalary());
            pstmt.setString(7, bean.getAddress());
            pstmt.setString(8, bean.getManager());
            pstmt.setString(9, bean.getId());
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
            super.close(null, pstmt, conn);
        }

        return cnt;
    }

    public int deleteAllData() {
        int cnt = -1;
        String sql = "delete from members";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            cnt = pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } finally {
            super.close(null, pstmt, conn);
        }
        return cnt;
    }
}
