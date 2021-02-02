package eu.unipi.controller;

import eu.unipi.model.APIError;
import eu.unipi.model.MovieInfo;
import io.javalin.http.Context;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieController {
    static final String SQL_CONN_STRING = "jdbc:mysql://localhost:3306/movieapp";
    static final String USER = "user1";
    static final String PASS = "pass";

    public static void handlePopularMovies(Context ctx){
        String maxResults = ctx.queryParam("limit", "6");
        try {
            int limit = Integer.parseInt(maxResults);
            ctx.json(getPopularMovies(limit));
        } catch (NumberFormatException e) {
            ctx.status(400);
            ctx.json(new APIError("Results limit parameter was invalid."));
        } catch (SQLException e){
            ctx.status(500);
            ctx.json(e.getMessage());
        }
    }

    public static void handleSearchMovies(Context ctx){
        String maxResults = ctx.queryParam("limit", "6");
        String searchParam = ctx.queryParam("q");
        if(searchParam == null){
            ctx.status(400);
            ctx.json(new APIError("Search parameter is required"));
        }
        try {
            int limit = Integer.parseInt(maxResults);
            ctx.json(searchMovies(searchParam, limit));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            ctx.status(400);
            ctx.json(new APIError("Results limit parameter was invalid."));
        } catch (SQLException e){
            e.printStackTrace();
            ctx.status(500);
            ctx.json(e.getMessage());
        }
    }

    private static List<MovieInfo> getPopularMovies(int limit) throws SQLException {
        try (Connection con = DriverManager.getConnection(SQL_CONN_STRING, USER, PASS)) {
            try (PreparedStatement pstmt =
                         con.prepareStatement("SELECT * FROM movies ORDER BY rating DESC LIMIT ?")) {
                pstmt.setInt(1, limit);
                try (ResultSet resultSet = pstmt.executeQuery()) {
                    List<MovieInfo> movieInfos = new ArrayList<>();
                    while (resultSet.next()) {
                        String name = resultSet.getString("name");
                        String details = resultSet.getString("details");
                        String rating = resultSet.getString("rating");
                        String releaseDate = resultSet.getString("release_date");
                        movieInfos.add(new MovieInfo(name, details, rating, releaseDate));
                    }
                    return movieInfos;
                }
            }
        }
    }

    private static List<MovieInfo> searchMovies(String param, int limit) throws SQLException {
        try (Connection con = DriverManager.getConnection(SQL_CONN_STRING, USER, PASS)) {
            try (PreparedStatement pstmt =
                         con.prepareStatement("SELECT * FROM movies WHERE name LIKE ? LIMIT ?")) {
                pstmt.setString(1, "%" + param + "%");
                pstmt.setInt(2, limit);
                try (ResultSet resultSet = pstmt.executeQuery()) {
                    List<MovieInfo> movieInfos = new ArrayList<>();
                    while (resultSet.next()) {
                        String name = resultSet.getString("name");
                        String details = resultSet.getString("details");
                        String rating = resultSet.getString("rating");
                        String releaseDate = resultSet.getString("release_date");
                        movieInfos.add(new MovieInfo(name, details, rating, releaseDate));
                    }
                    return movieInfos;
                }
            }
        }
    }
}
