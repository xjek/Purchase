package ru.groupstp.procurementcommission.model;

import io.realm.RealmObject;

/**
 * Комиссия
 */
public class Commission extends RealmObject {

    public static final String FIRST_VOTE = "fVote";
    public static final String SECOND_VOTE = "sVote";
    public static final String CHM_VOTE = "chmVote";

    private String id;
    private String name;
    private String vote;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getVote() {
        if (name == null || name.isEmpty()) {
            switch (vote) {
                case FIRST_VOTE:
                    return "Руководитель 1";
                case SECOND_VOTE:
                    return "Руководитель 2";
                case CHM_VOTE:
                    return "Председатель";
                default:
                    return "Неизвестно";
            }
        } else
            return name;
    }

    @Override
    public String toString() {
        return "Commission{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", vote='" + vote + '\'' +
                '}';
    }
}
