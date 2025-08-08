package com.waiting.system.model;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 이 클래스는 예약 객체들을 queue에 넣어 관리합니다.
 * 자료구조로는 reservationQueue를 사용해 동시성을 보장합니다.
 * 예약을 추가, 삭제, 조회할 수 있습니다.
 */
public class ReservationManager {
    private final Queue<Reservation> reservationQueue;

    public ReservationManager() {
        this.reservationQueue = new ConcurrentLinkedQueue<>();
    }

    public int getWaitingLength() {
        return reservationQueue.size();
    }

    /**
     * 예약자 전화번호와 인원 수를 입력받아 새로운 예약을 생성해 큐에 추가하고 반환합니다.
     * 입력값 검증이 필요할 시 입력값 검증 후 함수를 호출해주세요.
     *
     * @param phoneNum 예약자 전화번호
     * @param memberNum 예약 팀 인원 수
     * @return 생성된 예약 객체
     */
    public Reservation addReservation(String phoneNum, int memberNum) {
        Reservation newReservation = new Reservation(memberNum, phoneNum);

        reservationQueue.add(newReservation);
        return newReservation;
    }

    /**
     * 전화번호를 받아 해당 번호를 갖는 예약 객체들을 "모두" 삭제합니다.
     * @param phoneNum 예약자 전화번호
     * @return 삭제 성공 여부 (삭제된 항목이 하나라도 있으면 true, 없으면 false)
     */
    public boolean cancel(String phoneNum) {
        return reservationQueue.removeIf(r -> phoneNum != null && phoneNum.equals(r.getPhoneNum()));
    }


    /**
     * 큐의 가장 앞에 있는 팀의 예약 객체를 반환합니다.
     * 큐가 비어있다면 null을 반환합니다.
     * @return 큐 가장 앞에 있는 예약 객체
     */
    public Reservation call() {
        return reservationQueue.poll();
    }

    /**
     * 가장 먼저 만나는 입력받은 인원 수와 같거나 적은 인원수를 가진 팀을 큐에서 제거하고 반환합니다.
     * 큐가 비어있거나 조건에 맞는 팀이 없다면 null을 반환합니다.
     * @param memberNum
     * @return 예약 객체
     */
    public Reservation callByNum(int memberNum) {
        for (Reservation r : reservationQueue) {
            if (r.getMemberNum() <= memberNum) {
                if (reservationQueue.remove(r)) { // 안전하게 제거
                    return r;
                }
            }
        }
        return null;
    }

    /**
     * 전화번호를 입력받고 해당 전화번호를 갖는 예약 객체가 얼마나 뒤에 있는지 반환합니다.
     * 반환 인덱스는 1부터 시작합니다.
     * 큐가 비어있거나 해당 예약이 없다면 -1을 반환합니다.
     * @param phoneNum
     * @return 남은 대기 팀 수
     */
    public int getHowFar(String phoneNum) {
        if (phoneNum == null) return -1;
        int count = 0;
        for(Reservation r : reservationQueue) {
            if(r.getPhoneNum().equals(phoneNum)) {
                return count + 1;
            }
            count++;
        }
        return -1;
    }

    /**
     * 큐 가장 앞에 있는 팀의 인원 수를 반환합니다.
     * 큐가 비어있다면 -1을 반환합니다.
     * @return
     */
    public int getFirstMemberNum() {
        Reservation peek = reservationQueue.peek();
        if(peek != null)
            return peek.getMemberNum();
        else
            return -1;
    }
}
