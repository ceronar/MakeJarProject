package com.itgroup.dao;

import com.itgroup.bean.Board;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardDao extends SuperDao {
    public BoardDao() {
        super();
    }

    private Board makeBean(ResultSet rs) {
        // ResultSet에서 데이터를 읽어와서 Bean 객체에 담아 반환
        Board bean = null;
        try {
            bean = new Board();
            bean.setNo(rs.getInt("no"));
            bean.setWriter(rs.getString("writher"));
            bean.setPassword(rs.getString("password"));
            bean.setSubject(rs.getString("subject"));
            bean.setContent(rs.getString("content"));
            bean.setReadhit(rs.getInt("readhit"));
            bean.setRegdate(String.valueOf(rs.getDate("regdate")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bean;
    }

    public List<Board> selectAllBoard() {
        // 전체 게시물을 최신 항목부터 조회하여 반환
        List<Board> boardList = new ArrayList<>();
        String sql = "SELECT * FROM BOARD ORDER BY NO DESC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Board bean = this.makeBean(rs);
                boardList.add(bean);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            super.close(rs, pstmt, conn);
        }
        return boardList;
    }

    public List<Board> selectEvemData() {
        // 게시물 번호가 짝수인 항목들만 조회
        List<Board> boardList = new ArrayList<>();
        String sql = "SELECT * FROM BOARD WHERE MOD(NO, 2) = 0 ORDER BY NO DESC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Board bean = this.makeBean(rs);
                boardList.add(bean);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            super.close(rs, pstmt, conn);
        }
        return boardList;
    }
}
