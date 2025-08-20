package com.itgroup;

import com.itgroup.bean.Board;
import com.itgroup.bean.Member;
import com.itgroup.dao.BoardDao;
import com.itgroup.dao.MemberDao;

import java.util.List;
import java.util.Scanner;

// 메인 클래스 대신 실제 모든 업무를 총 책임하는 매니저 클래스
public class DataManager {
    private MemberDao mdao = null; // 실제 데이터 베이스와 연동하는 다오 클래스
    private BoardDao bdao = null;

    public DataManager() {
        this.mdao = new MemberDao();
        this.bdao = new BoardDao();
    }

    public void selectAll() { // 모든 회원 정보 조회
        List<Member> members = mdao.selectAll();
        if (members != null) {
            System.out.println("이름\t급여\t주소");
            for (Member bean : members) {
                System.out.println(bean.getName() + "\t" + bean.getSalary() + "\t" + bean.getAddress());
            }
        } else {
            System.out.println("조회된 정보 없음.");
        }
    }

    public void findByGender(Scanner sc) {
        List<Member> mydata = null;
        System.out.printf("성별 입력(1:남자, 2:여자) : ");
        int gender = Integer.parseInt(sc.nextLine());
        if(gender == 1) {
            mydata = mdao.findByGender("남자");
        } else if(gender == 2) {
            mydata = mdao.findByGender("여자");
        } else {
            System.out.println("잘못된 입력");
        }
        if (mydata != null) {
            // 출력
            System.out.println("이름\t급여\t주소\t성별");
            for (Member bean : mydata) {
                System.out.println(bean.getName() + "\t" + bean.getSalary() + "\t" + bean.getAddress()+ "\t" + bean.getGender());
            }
        } else {
            System.out.println("조회된 정보 없음.");
        }
    }

    public void getMemberOne() {
        String findId = "lee"; // 찾고자 하는 회원(로그인한 회원)
        Member m = mdao.getMemberOne(findId);
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
        int cnt = mdao.getSize();
        String message;
        if (cnt != 0) {
            message = "검색된 회원은 총 " + cnt + "명 입니다.";
        } else {
            message = "검색된 회원이 존재하지 않습니다.";
        }
        System.out.println(message);
    }

    public void deleteData() { // id를 이용한 회원 삭제(탈퇴)
        String id = "yusin";
        int cnt = -1;
        cnt = mdao.deleteData(id);

        if (cnt == -1) {
            System.out.println("회원 탈퇴 실패(접속, 네트워크 오류)");
        } else if (cnt == 0) {
            System.out.println("탈퇴할 회원이 존재하지 않습니다.");
        } else if (cnt >= 0) {
            System.out.println("회원 탈퇴 완료");
        } else {

        }
    }

    public void insertData(Scanner sc) { // 회원 1명 추가(가입)
        Member bean = new Member();
        int cnt = -1;

        // 편의상 2~3개만 입력
        System.out.printf("id 입력 : ");
        String id = sc.nextLine();
        System.out.printf("이름 입력 : ");
        String name = sc.nextLine();

        // 다음은 회원 가입 페이지에서 기입한 내용이라 가정
        bean.setId(id);
        bean.setName(name);
        bean.setPassword("abc123");
        bean.setGender("남자");
        bean.setBirth("2025/08/20");
        bean.setMarriage("결혼");
        bean.setSalary(100);
        bean.setAddress("서대문");
        bean.setManager(null);

        cnt = mdao.insertData(bean);

        if (cnt == -1) {
            System.out.println("회원 가입 실패");
        } else if (cnt == 0) {
            System.out.println("아이디 중복 가입 실패.");
        } else if (cnt == 1) {
            System.out.println("회원 아이디 " + id + "로 가입 완료");
        } else {

        }
    }

    public void updateData(Scanner sc) { // 회원 정보 수정
        int cnt = -1;
        System.out.printf("수정하고자 하는 회원 id 입력 : ");
        String findId = sc.nextLine();

        // 여기서 bean은 이전에 입력했던 나의 정보
        Member bean = mdao.getMemberOne(findId);

        if(bean != null) {
            // 편의상 내 이름과 결혼 여부를 변경
            System.out.printf("이름 입력 : ");
            String name = sc.nextLine();
            System.out.printf("결혼 여부 입력 : ");
            String marriage = sc.nextLine();

            bean.setName(name);
            bean.setMarriage(marriage);

            cnt = mdao.updateData(bean);
        }

        if (cnt == -1) {
            System.out.println("업데이트 실패");
        } else if (cnt == 1) {
            System.out.println("업데이트 성공");
        } else {

        }
    }

    public void deleteAllData() { // 회원 목록 전부 삭제
        int cnt = -1;
        cnt = mdao.deleteAllData();

        if (cnt == -1) {
            System.out.println("삭제 실패");
        } else if (cnt >= 1) {
            System.out.println("삭제 성공");
        } else if (cnt == 0) {
            System.out.println("삭제할 데이터가 없음");
        }
    }

    public void selectAllBoard() {
        // 모든 게시물 조회
        List<Board> boardList = bdao.selectAllBoard();
        if (boardList != null) {
            System.out.println("글번호\t작성자\t제목\t글내용");
            for (Board bean : boardList) {
                int no = bean.getNo();
                String writer = bean.getWriter();
                String subject = bean.getSubject();
                String content = bean.getContent();
                System.out.println(no + "\t" + writer + "\t" + subject + "\t" + content);
            }
        } else {
            System.out.println("조회된 정보 없음.");
        }
    }

    public void selectEvenData() {
        // 게시물 번호가 짝수인 항목들만 조회
        List<Board> boardList = bdao.selectEvemData();
        if (boardList != null) {
            System.out.println("글번호\t작성자\t제목\t글내용");
            for (Board bean : boardList) {
                int no = bean.getNo();
                String writer = bean.getWriter();
                String subject = bean.getSubject();
                String content = bean.getContent();
                System.out.println(no + "\t" + writer + "\t" + subject + "\t" + content);
            }
        } else {
            System.out.println("조회된 정보 없음.");
        }
    }
}
