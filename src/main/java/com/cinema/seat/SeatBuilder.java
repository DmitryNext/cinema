package com.cinema.seat;

public class SeatBuilder {
    private int id;
    private int rowNumber;
    private int placeNumber;
    private SeatStatus seatStatus;

    public SeatBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public SeatBuilder setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
        return this;
    }

    public SeatBuilder setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
        return this;
    }

    public SeatBuilder setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
        return this;
    }


    public Seat build(){
        return new Seat(id, rowNumber, placeNumber, seatStatus);
    }
}