package eu.unipi;

import eu.unipi.controller.MovieController;
import eu.unipi.model.APIError;
import eu.unipi.model.MovieInfo;
import io.javalin.Javalin;
import org.eclipse.jetty.http.HttpStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Hello world! */
public class App {
  static final String SQL_CONN_STRING = "jdbc:mariadb://localhost:3306/movieapp";
  static final String USER = "user1";
  static final String PASS = "pass";

  public static void main(String[] args) {
    Javalin app = Javalin.create().start(8080);

// ALTERNATE WAY OF DECLARING ENDPOINT FUNCTIONALITY
//     app.get("/api/discover/movie", MovieController::handlePopularMovies);
//     app.get("/api/search/movie", MovieController::handleSearchMovies);

    app.get(
        "/api/discover/movie",
        ctx -> {
          String maxResults = ctx.queryParam("limit");
          if(maxResults==null)
        	  maxResults="6";
          try {
            int limit = Integer.parseInt(maxResults);
            ctx.json(getPopularMovies(limit));
          } catch (NumberFormatException e) {
            e.printStackTrace();
            ctx.status(400);
            ctx.json(new APIError("Results limit parameter was invalid."));
          } catch (SQLException e) {
            e.printStackTrace();
            ctx.status(HttpStatus.BAD_REQUEST_400);
            ctx.json(e.getMessage());
          }
        });

    app.get(
        "/api/search/movie",
        ctx -> {
          String maxResults = ctx.queryParam("limit");
          if(maxResults==null)
        	  maxResults="6";
          String searchParam = ctx.queryParam("q");
          if (searchParam == null || searchParam.isBlank()) {
            ctx.status(400);
            ctx.json(new APIError("Search parameter is required"));
          } else {
            try {
              int limit = Integer.parseInt(maxResults);
              ctx.json(searchMovies(searchParam, limit));
            } catch (NumberFormatException e) {
              e.printStackTrace();
              ctx.status(400);
              ctx.json(new APIError("Results limit parameter was invalid."));
            } catch (SQLException e) {
              e.printStackTrace();
              ctx.status(500);
              ctx.json(e.getMessage());
            }
          }
        });
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
