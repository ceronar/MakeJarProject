package com.itgroup;

import com.itgroup.bean.Member;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MemberManager manager = new MemberManager();
        while (true) {
            System.out.println("메뉴 선택");
            System.out.println("0.종료, 1.목록 조회, 2.가입, 3.수정, 4.총 회원 수, 5.탈퇴, 6.회원 정보, 7.XX, 8.XX");
            int menu = Integer.parseInt(sc.nextLine()); // 선택한 메뉴
            switch (menu) {
                case 0:
                    System.out.println("프로그램을 종료합니다.");
                    System.exit(0); // 운영체제에게 종료됨을 알리고 빠져 나가기
                case 1:
                    manager.selectAll();
                    break;
                case 2:
                    manager.insertData(new Member());
                    break;
                case 3:
                    manager.updateData(new Member());
                    break;
                case 4:
                    manager.getSize();
                    break;
                case 5:
                    manager.deleteData("");
                    break;
                case 6:
                    manager.getOne("");
                    break;
                case 7:
                    break;
                case 8:
                    break;
                default:
            }
        }


    }
}