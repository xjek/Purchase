package ru.groupstp.procurementcommission.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmList;
import io.realm.RealmObject;

import static ru.groupstp.procurementcommission.app.Common.plural;

/**
 * Позиции
 */
public class Nomenclature extends RealmObject {

    private final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private final static String FORMAT_OUT_DATE = "dd.MM.yy";
    private final static String FORMAT_OUT_TIME = "HH:mm";
    private final static Date dateWhenItemOver = getDateWhenItemOver();

    private final static String COMMENTS_TECH = "Техническое задание:";
    private final static String COMMENTS_COMMENT = "Комментарий:";

    public static final String ORG = "org";
    public static final String SUB = "sub";
    public static final String USER = "user";

    private String id;
    private String name;
    private String user;
    private String sub;
    private String organization;
    private int count;
    private String comment;
    private Date timestamp;
    private RealmList<Supplier> suppliers;

    private static Date getDateWhenItemOver() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -5);
        return calendar.getTime();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String  getCount() {
        return String.valueOf(count);
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setTimestamp(String timestamp) {
        DateFormat format = new SimpleDateFormat(DATE_FORMAT, new Locale("ru"));
        try {
            this.timestamp = format.parse(timestamp);
        } catch (ParseException e) {

        }
    }

    public void addSupplierr(Supplier supplier) {
        if (suppliers == null) {
            suppliers = new RealmList<>();
        }
        suppliers.add(supplier);
    }

    public RealmList<Supplier> getSuppliers() {
        return suppliers;
    }

    public boolean isSoonTimeWillEnd() {
        return timestamp.before(dateWhenItemOver);
    }

    public String getDateTimestamp() {
        return new SimpleDateFormat(FORMAT_OUT_DATE, new Locale("ru")).format(timestamp);
    }

    public String getTimeTimestamp() {
        return new SimpleDateFormat(FORMAT_OUT_TIME, new Locale("ru")).format(timestamp);
    }

    public String[] getComments() {
        String[] comments = new String[2];

        try {
            if (comment != null && comment.length() != 0 && !comment.equals("null")) {
                int indexTech = comment.indexOf(COMMENTS_TECH);
                int indexComm = comment.indexOf(COMMENTS_COMMENT);

                String comment = getComment(this.comment, indexComm);
                String tech = getTechTask(this.comment, indexTech, indexComm);
                if (tech.length() > 0)
                    comments[0] = tech;
                if (comment.length() > 0)
                    comments[1] = comment;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comments;
    }

    private String getTechTask(String value, int indexTech, int indexComment) {
        String tech = "";
        if (indexTech != -1) {
            if (indexComment != -1)
                tech = value.substring(indexTech + COMMENTS_TECH.length(), indexComment);
            else
                tech = value.substring(indexTech + COMMENTS_TECH.length());
            if (tech.length() > 4)
                tech = correctComment(tech);
            else
                tech = "";
        }
        return tech;
    }

    private String getComment(String value, int indexComment) {
        String comment = "";
        if (indexComment > -1)
            comment = value.substring(indexComment + COMMENTS_COMMENT.length());
        if (comment.length() > 4)
            comment = correctComment(comment);
        else
            comment = "";
        return comment;

    }

    private String correctComment(String comment) {
        return comment.trim().replaceAll("\n", "");
    }

    public String getCommentsCount() {
        int count = 0;
        String[] comments = getComments();
        for(String comm : comments)
            if (comm != null && comm.length() > 0)
                count++;

        return String.valueOf(count) + " " + plural(count, "комментарий", "комментария", "комментариев");
    }

    @Override
    public String toString() {
        return "Nomenclature{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", user='" + user + '\'' +
                ", sub='" + sub + '\'' +
                ", organization='" + organization + '\'' +
                ", count=" + count +
                ", comment='" + comment + '\'' +
                ", timestamp=" + timestamp +
                ", suppliers=" + suppliers +
                '}';
    }
}
