package dev.nikee.database;

import org.bson.Document;

public class UserData {

    private String id;
    private String player;
    private double money;

    public UserData(String id, String player, double money) {
        this.id = id;
        this.player = player;
        this.money = money;
    }

    public String getUsername() {
        return player;
    }

    public void setUsername(String username) {
        this.player = username;
    }
//
    public void addBalance(double amount) {
        this.money += amount;
    }

    public void setBalance(double amount) {
        this.money = amount;
    }

    public void removeBalance(double amount) {
        this.money -= amount;
    }

    public double getBalance() {
        return money;
    }

    public Document toDocument() {
        Document doc = new Document();
        if (id != null) doc.append("_id", id);
        doc.append("player", player);
        doc.append("money", money);
        return doc;
    }

    public static UserData fromDocument(Document doc) {
        return new UserData(
                doc.getString("_id"),
                doc.getString("username"),
                doc.getInteger("money", 0)
        );
    }
}
