package eu.unipi.model;


import java.util.Objects;

public class MovieInfo {
    private final String title;
    private final String description;
    private final String rating;
    private final String release_date;

    public MovieInfo(String title, String description, String rating, String release_date) {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getRating() {
        return rating;
    }

    public String getRelease_date() {
        return release_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieInfo movieInfo = (MovieInfo) o;
        return Objects.equals(title, movieInfo.title) && Objects.equals(description, movieInfo.description) && Objects.equals(rating, movieInfo.rating) && Objects.equals(release_date, movieInfo.release_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, rating, release_date);
    }

    @Override
    public String toString() {
        return "MovieInfo{" +
                "title='" + title + "'\n" +
                ", description='" + description + "'\n" +
                ", rating='" + rating + "'\n" +
                ", release_date='" + release_date + "'\n" +
                '}';
    }
}
