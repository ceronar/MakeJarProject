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

    public void selectAll() { // 모든 회원 정보 조회
        List<Member> members = dao.selectAll();
        System.out.println("이름\t급여\t주소");
        for (Member bean : members) {
            System.out.println(bean.getName() + "\t" + bean.getSalary() + "\t" + bean.getAddress());
        }
    }

    public void findByGender(String gender) {
        List<Member> mydata = dao.findByGender(gender);
        // 출력
        System.out.println("이름\t급여\t주소\t성별");
        for (Member bean : mydata) {
            System.out.println(bean.getName() + "\t" + bean.getSalary() + "\t" + bean.getAddress()+ "\t" + bean.getGender());
        }
    }

    public void getMemberOne() {
        String findId = "lee"; // 찾고자 하는 회원(로그인한 회원)
        Member m = dao.getMemberOne(findId);
        String message = "";
        if (m != null) {
            String id = m.getId();
            String name = m.getName();
            String gender = m.getGender();
            message = id + "\t" + name + "\t" + gender;
        } else {
            message = "찾으시는 회원이 존재하지 않습니다.";
        }
        System.out.println(message);
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

    public void deleteData() { // id를 이용한 회원 삭제(탈퇴)
        String id = "yusin";
        int cnt = -1;
        cnt = dao.deleteData(id);

        if (cnt == -1) {
            System.out.println("회원 탈퇴 실패(접속, 네트워크 오류)");
        } else if(cnt == 0) {
            System.out.println("탈퇴할 회원이 존재하지 않습니다.");
        } else if (cnt >= 0) {
            System.out.println("회원 탈퇴 완료");
        } else {

        }
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

    public void deleteAllData() { // 회원 목록 전부 삭제

    }
}
