package com.cinema.session;

import com.cinema.movie.Movie;
import com.cinema.seat.Seat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SessionBuilder {
    private int id;
    private BigDecimal ticketPrice;
    private LocalDate date;
    private LocalTime time;
    private Movie movie;
    int freeSeats;

    public SessionBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public SessionBuilder setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
        return this;
    }

    public SessionBuilder setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public SessionBuilder setTime(LocalTime time) {
        this.time = time;
        return this;
    }

    public SessionBuilder setMovie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public SessionBuilder setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
        return this;
    }

    public Session build() {
        return new Session(id, ticketPrice, date, time, movie, freeSeats);
    }
}