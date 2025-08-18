package com.itgroup;

import com.itgroup.bean.Member;
import com.itgroup.dao.MemberDao;

import java.util.List;

// 메인 클래스 대신 실제 모든 업무를 총 책임하는 매니저 클래스
public class MemberManager {
    private MemberDao dao = null; // 실제 데이터 베이스와 연동하는 다오 클래스

    public MemberManager() {
        this.dao = new MemberDao();
    }

    public List<Member> selectAll() { // 모든 회원 정보 조회
        return null;
    }

    public void getSize() { // 총 회원 수 조회
        int cnt = dao.getSize();
        String message;
        if(cnt != 0) {
            message = "검색된 회원은 총 " + cnt + "명 입니다.";
        } else {
            message = "검색된 회원이 존재하지 않습니다.";
        }
        System.out.println(message);
    }

    public int insertData(Member m) { // 회원 1명 추가(가입)
        return 0;
    }

    public int updateData(Member m) { // 회원 정보 수정
        return 0;
    }

    public Member getOne(String id) { // 1명의 정보 조회
        return null;
    }

    public int deleteData(String id) { // id를 이용한 회원 삭제(탈퇴)
        return 0;
    }

    public void deleteAllData() { // 회원 목록 전부 삭제

    }
}
