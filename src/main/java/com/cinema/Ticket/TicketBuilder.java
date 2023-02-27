package com.cinema.Ticket;

import com.cinema.seat.Seat;
import com.cinema.session.Session;
import com.cinema.user.User;

import java.math.BigDecimal;

public class TicketBuilder {
    private int id;
    private BigDecimal ticketPrice;
    private Session session;
    private Seat seat;
    private User user;

    public TicketBuilder setId(int id) {
        this.id = id;
        return this;
    }
    public TicketBuilder setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
        return this;
    }

    public TicketBuilder setSession(Session session) {
        this.session = session;
        return this;
    }

    public TicketBuilder setSeat(Seat seat) {
        this.seat = seat;
        return this;
    }

    public TicketBuilder setUser(User user) {
        this.user = user;
        return this;
    }


    public Ticket build() {
        return new Ticket(id, ticketPrice, session, seat, user);
    }
}