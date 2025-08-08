package com.waiting.system.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private int ID;             // 예약 고유 ID
    private String phoneNum;    // 고객 전화번호
    private int memberNum;      // 예약 팀 인원 수
    private String createdAt;   // 예약 생성 시간

    /**
     * 생성자
     * - 전화번호와 예약 팀 인원 수를 입력으로 받아 저장
     * - 시간은 생성 시간으로 설정합니다.
     * - ID를 전화번호와 생성 시간 문자열을 합친 것의 해시값으로 설정합니다.
     *
     * @param memberNum
     * @param phoneNum
     */
    Reservation(int memberNum, String phoneNum) {
        this.memberNum = memberNum;

        this.phoneNum = phoneNum;

        // 현재 시간을 받아와서 저장
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH시 mm분 ss초");
        createdAt = LocalTime.now().format(formatter);

        // 전화번호와 현재 시간을 붙인 후 해시값을 추출해 ID로 저장
        String temp = phoneNum + createdAt;
        this.ID = temp.hashCode();
    }

    public int getCustomerId() {
        return ID;
    }

    public int getMemberNum() {
        return memberNum;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    @Override
    public String toString(){
        return "{ID: " + getCustomerId() + ", memberNum: " + memberNum + ", createdAt: " + getCreatedAt() + ", " + getPhoneNum() + "}";
    }
}
