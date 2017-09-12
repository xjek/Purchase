package ru.groupstp.procurementcommission.model;

import io.realm.RealmObject;

/**
 * Пользователь
 */
public class User extends RealmObject {

    public static final String CHAIRMAN = "chairman" ;
    public static final String MEMBER_1 = "member1";
    public static final String MEMBER_2 = "member2";

    private String id;
    private String login;
    private String password;
    private String username;
    private String userEmail;
    private String vote;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }
}
