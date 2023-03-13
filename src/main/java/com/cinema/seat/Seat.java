package com.cinema.seat;

import java.util.Objects;

public class Seat {
    private int id;
    private int rowNumber;
    private int placeNumber;
    private SeatStatus seatStatus;

    public Seat(int id, int rowNumber, int placeNumber, SeatStatus seatStatus) {
        this.id = id;
        this.rowNumber = rowNumber;
        this.placeNumber = placeNumber;
        this.seatStatus = seatStatus;
    }

    public Seat() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", rowNumber=" + rowNumber +
                ", placeNumber=" + placeNumber +
                ", seatStatus=" + seatStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seat)) return false;
        Seat seat = (Seat) o;
        return id == seat.id
                && rowNumber == seat.rowNumber
                && placeNumber == seat.placeNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rowNumber, placeNumber);
    }
}