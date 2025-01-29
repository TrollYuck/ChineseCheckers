package pwr.tp.server.DB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import pwr.tp.utilityClases.Pair;

import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Repository
public class DatabaseManager {
  private final JdbcTemplate jdbcTemplate;
  private int gameId;

  @Autowired
  public DatabaseManager(JdbcTemplate template) {
    this.jdbcTemplate = template;
  }

  public void createGame(int playerCount, int botsAmount, String gameType) {
    String sql = "INSERT INTO games (start_time, player_count, bots_amount, game_type) VALUES (?, ?, ?, ?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(connection -> {
      var ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
      ps.setInt(2, playerCount);
      ps.setInt(3, botsAmount);
      ps.setString(4, gameType);
      return ps;
    }, keyHolder);
    gameId = Objects.requireNonNull(keyHolder.getKey()).intValue();
  }

  public void recordMove(int playerIndex, String startPosition, String endPosition) {
    String sql = "INSERT INTO moves (game_id, player_index, start_position, end_position, move_time) VALUES (?, ?, ?, ?, ?)";
    jdbcTemplate.update(sql, gameId, playerIndex, startPosition, endPosition, new Timestamp(System.currentTimeMillis()));
  }

  public void endGame(int winnerId) {
    String sql = "UPDATE games SET end_time = ?, winner_id = ? WHERE game_id = ?";
    jdbcTemplate.update(sql, new Timestamp(System.currentTimeMillis()), winnerId, gameId);
  }

  public void setGameId(int gameId) {
    this.gameId = gameId;
  }

  public List<Pair<Integer, Pair<String, String>>> getMoves() {
    String sql = "SELECT start_position, end_position, player_index FROM moves WHERE game_id = ?";
    return jdbcTemplate.query(sql, new Object[]{gameId}, (rs, rowNum) ->
            new Pair<>(rs.getInt("player_index"), new Pair<>(rs.getString("start_position"), rs.getString("end_position"))));
  }

  public List<String> getGamesInProgress(boolean showoff) {
    String sql = "SELECT game_id, player_count, bots_amount, start_time, type FROM games WHERE end_time IS NULL";
    if (showoff) {
      return jdbcTemplate.query(sql, (rs, rowNum) ->
              String.format("Game ID: %d, Players: %d, Bots: %d, Start time: %s, Type: %s",
                      rs.getInt("game_id"), rs.getInt("player_count"), rs.getInt("bots_amount"),
                      rs.getTimestamp("start_time"), rs.getString("type")));
    } else {
      return jdbcTemplate.query(sql, (rs, rowNum) ->
              String.format("%d,%d,%d,%s,%s",
                      rs.getInt("game_id"), rs.getInt("player_count"), rs.getInt("bots_amount"),
                      rs.getTimestamp("start_time"), rs.getString("type")));
    }
  }

  public List<String> getGamesHistory() {
    String sql = "SELECT game_id, player_count, bots_amount, start_time, end_time, winner_id, type FROM games WHERE end_time IS NOT NULL";
    return jdbcTemplate.query(sql, (rs, rowNum) ->
            String.format("Game ID: %d, Players: %d, Bots: %d, Start time: %s, End time: %s, Winner ID: %d, Type: %s",
                    rs.getInt("game_id"), rs.getInt("player_count"), rs.getInt("bots_amount"),
                    rs.getTimestamp("start_time"), rs.getTimestamp("end_time"), rs.getInt("winner_id"), rs.getString("type")));
  }
}
