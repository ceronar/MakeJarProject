package com.itgroup;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DataManager manager = new DataManager();
        while (true) {
            System.out.println("메뉴 선택");
            System.out.println("0.종료, 1.목록조회, 2.가입, 3.수정, 4.총회원수, 5.탈퇴, 6.회원 정보, 7.성별조회, 8.회원초기화");
            System.out.println("11.게시물전체, 12.등록, 13.수정, 14.전체 건수, 15.삭제, 16.1건정보, 17.짝수번호조회");
            int menu = Integer.parseInt(sc.nextLine()); // 선택한 메뉴
            switch (menu) {
                case 0:
                    sc.close();
                    System.out.println("프로그램을 종료합니다.");
                    System.exit(0); // 운영체제에게 종료됨을 알리고 빠져 나가기
                case 1:
                    manager.selectAll();
                    break;
                case 2:
                    manager.insertData(sc);
                    break;
                case 3:
                    manager.updateData(sc);
                    break;
                case 4:
                    manager.getSize();
                    break;
                case 5:
                    manager.deleteData();
                    break;
                case 6:
                    manager.getMemberOne();
                    break;
                case 7:
                    manager.findByGender(sc);
                    break;
                case 8:
                    manager.deleteAllData();
                    break;
                case 11:
                    manager.selectAllBoard();
                    break;
                case 12:
                    break;
                case 13:
                    break;
                case 14:
                    break;
                case 15:
                    break;
                case 16:
                    break;
                case 17:
                    manager.selectEvenData();
                    break;
                default:
            }
        }
    }
}