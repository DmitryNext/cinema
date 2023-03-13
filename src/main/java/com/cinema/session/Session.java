package com.cinema.session;

import com.cinema.movie.Movie;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Session {
    private int id;
    private BigDecimal ticketPrice;
    private LocalDate date;
    private LocalTime time;
    private Movie movie;
    int freeSeats;

    public Session(int id, BigDecimal ticketPrice, LocalDate date, LocalTime time, Movie movie, int freeSeats) {
        this.id = id;
        this.ticketPrice = ticketPrice;
        this.date = date;
        this.time = time;
        this.movie = movie;
        this.freeSeats = freeSeats;
    }

    public Session() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", ticketPrice=" + ticketPrice +
                ", date=" + date +
                ", time=" + time +
                ", movie=" + movie +
                ", freeSeats=" + freeSeats +
                ", allSeats=" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Session)) return false;
        Session session = (Session) o;
        return id == session.id
                && Objects.equals(ticketPrice, session.ticketPrice)
                && Objects.equals(date, session.date)
                && Objects.equals(time, session.time)
                && Objects.equals(movie, session.movie)
                && Objects.equals(freeSeats, session.freeSeats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ticketPrice, date, time, movie, freeSeats);
    }
}