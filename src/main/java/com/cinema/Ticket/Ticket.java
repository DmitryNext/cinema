package com.cinema.Ticket;

import com.cinema.seat.Seat;
import com.cinema.session.Session;
import com.cinema.user.User;

import java.math.BigDecimal;
import java.util.Objects;

public class Ticket {
    private int id;
    private BigDecimal ticketPrice;
    private Session session;
    private Seat seat;
    private User user;

    public Ticket() {
    }

    public Ticket(int id, BigDecimal ticketPrice, Session session, Seat seat, User user) {
        this.id = id;
        this.ticketPrice = ticketPrice;
        this.session = session;
        this.seat = seat;
        this.user = user;
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

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", ticketPrice=" + ticketPrice +
                ", session=" + session +
                ", seat=" + seat +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id
                && Objects.equals(ticketPrice, ticket.ticketPrice)
                && Objects.equals(session, ticket.session)
                && Objects.equals(seat, ticket.seat)
                && Objects.equals(user, ticket.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ticketPrice, session, seat, user);
    }
}