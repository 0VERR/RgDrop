package pl.overr.drop.storage;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.file.FileConfiguration;
import pl.overr.drop.DropPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Hikari {

    private final HikariDataSource hikariDataSource;
    private final FileConfiguration cfg = DropPlugin.getDropPlugin().getConfig();

    private final String username = cfg.getString("MySQL.username");
    private final String password = cfg.getString("MySQL.password");
    private final String host = cfg.getString("MySQL.host");
    private final int port = cfg.getInt("MySQL.port");
    private final String database = cfg.getString("MySQL.database");

    public Hikari(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        this.hikariDataSource = new HikariDataSource(hikariConfig);
        createTable();
    }


    public Connection getConnection(){
        try {
            return this.hikariDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createTable(){
        try (Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS rgdrop (" +
                    "id int PRIMARY KEY AUTO_INCREMENT,"+
                    "playerUUID VARCHAR(36) NOT NULL," +
                    "playerSettings TEXT," +
                    "playerLVL int NOT NULL," +
                    "activeMessage TEXT NOT NULL)");
            statement.executeUpdate();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
