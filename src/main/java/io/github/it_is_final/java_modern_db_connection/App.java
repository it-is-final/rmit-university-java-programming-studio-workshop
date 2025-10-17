package io.github.it_is_final.java_modern_db_connection;

import java.sql.SQLException;
import java.util.ArrayList;
import org.sqlite.SQLiteDataSource;

/**
 * Hello world!
 *
 */
public class App {
    private static final String databaseUrl = "jdbc:sqlite:database/Movies.db";

    public static ArrayList<Movie> getMovies() {
        var movies = new ArrayList<Movie>();
        var dataSource = new SQLiteDataSource();
        String query = "SELECT mvnumb, mvtitle, yrmde, mvtype FROM movie";
        dataSource.setUrl(databaseUrl);

        try (
            var connection = dataSource.getConnection();
            var statement = connection.createStatement();
            var results = statement.executeQuery(query)
        ) {
            while (results.next()) {
                var movie = new Movie(
                                results.getInt("mvnumb"),
                                results.getString("mvtitle"),
                                results.getInt("yrmde"),
                                results.getString("mvtype")
                            );
                movies.add(movie);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return movies;
    }

    public static void main(String[] args) {
        var movies = getMovies();
        for (var movie : movies) {
            System.out.printf(
                "%s (%d) - %s%n",
                movie.title(), movie.year(), movie.genre()
            );
        }
    }
}
