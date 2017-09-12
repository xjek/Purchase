package ru.groupstp.procurementcommission.model;

/**
 * Просмотренные позиции
 */
public class Browsed  {
    private String mQid;

    public String getQid() {
        return mQid;
    }

    public void setQid(String qid) {
        mQid = qid;
    }

    @Override
    public String toString() {
        return "Browsed{" +
                "mQid='" + mQid + '\'' +
                '}';
    }

}
