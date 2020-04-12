package pl.overr.drop.managers;

import pl.overr.drop.storage.Hikari;
import pl.overr.drop.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager {
    private final Hikari hikari;
    public HashMap<UUID, User> getUserHashMap() {
        return userHashMap;
    }

    public boolean checkContains(UUID uuid){
        return userHashMap.containsKey(uuid);
    }

    public String getDrops(User user){
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Boolean> stringBooleanEntry : user.getActiveDrop().entrySet()) {
            stringBuilder.append(stringBooleanEntry.getKey()).append(":").append(stringBooleanEntry.getValue()).append(",");
        }
        return stringBuilder.toString();
    }

    public void insertIntoMySQL(User user){
        try (Connection connection = hikari.getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO rgdrop VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1,0);
            statement.setString(2,user.getPlayerUUID().toString());
            statement.setString(3,getDrops(user));
            statement.setInt(4,0);
            statement.setString(5,"true");
            statement.executeUpdate();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void updateSQL(User user){
        try (Connection connection = hikari.getConnection()){
            PreparedStatement statement = connection.prepareStatement("UPDATE rgdrop SET playerSettings = ?, activeMessage = ?, playerLVL = ? WHERE playerUUID ='" + user.getPlayerUUID() + "';");
            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry<String, Boolean> stringBooleanEntry : user.getActiveDrop().entrySet()) {
                stringBuilder.append(stringBooleanEntry.getKey()).append(":").append(stringBooleanEntry.getValue()).append(",");
            }
            String text = stringBuilder.toString();

            statement.setString(1,text);
            statement.setString(2,String.valueOf(user.isActiveMessage()));
            statement.setInt(3,user.getMinedStone());
            statement.executeUpdate();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void loadUsers(){
        try (Connection connection = hikari.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM rgdrop");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                UUID playerUUID = UUID.fromString(resultSet.getString("playerUUID"));
                int level = resultSet.getInt("playerLVL");
                boolean activeMessage = Boolean.parseBoolean(resultSet.getString("activeMessage"));
                String optionsConvert = resultSet.getString("playerSettings");
                Map<String,Boolean> optionsMap = new HashMap<>();
                for (String s : optionsConvert.split(",")){
                        String optionString = s.split(":")[0];
                        String var = s.split(":")[1];
                        optionsMap.put(optionString, Boolean.valueOf(var));
                    }
                User user = new User(playerUUID, activeMessage, optionsMap,level);
                addToUsers(user);
                }

        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    private final HashMap<UUID, User> userHashMap;

    public UserManager(Hikari hikari) {
        this.hikari = hikari;
        this.userHashMap = new HashMap<>();
    }

    public void addToUsers(User user){
            userHashMap.put(user.getPlayerUUID(),user);
        }


    public User getUser(UUID uuid){
        return userHashMap.get(uuid);
    }
}
