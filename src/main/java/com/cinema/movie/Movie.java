package com.cinema.movie;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

public class Movie {
    private int id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private Time duration;
    private String poster;

    public Movie() {
    }

    public Movie(int id, String name, Genre genre, Time duration, String poster) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.duration = duration;
        this.poster = poster;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genre=" + genre +
                ", duration=" + duration +
                ", poster='" + poster + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return id == movie.id
                && Objects.equals(name, movie.name)
                && genre == movie.genre
                && Objects.equals(duration, movie.duration)
                && Objects.equals(poster, movie.poster);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, genre, duration, poster);
    }
}