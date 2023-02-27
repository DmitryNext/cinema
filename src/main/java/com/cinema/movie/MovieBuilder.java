package com.cinema.movie;

import javax.persistence.*;
import java.sql.Time;
import java.time.Duration;

public class MovieBuilder {
    private int id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private Time duration;
    private String poster;


    public MovieBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public MovieBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public MovieBuilder setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public MovieBuilder setDuration(Time duration) {
        this.duration = duration;
        return this;
    }

    public MovieBuilder setPoster(String poster) {
        this.poster = poster;
        return this;
    }

    public Movie build(){
        return new Movie(id, name, genre, duration, poster);
    }
}