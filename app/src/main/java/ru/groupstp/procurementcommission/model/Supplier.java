package ru.groupstp.procurementcommission.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Поставщики
 */
public class Supplier extends RealmObject {
    private String id;
    private String name;
    private boolean trust;

    private int price;
    private String analog;
    private int guar;
    private int have;
    private int prebuy;
    private String maker;
    private int deliveryTime;
    private RealmList<Commission> commissions;

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

    public boolean isTrust() {
        return trust;
    }

    public void setTrust(boolean trust) {
        this.trust = trust;
    }

    public String getPrice() {
        return String.valueOf(price);
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setAnalog(String analog) {
        this.analog = analog;
    }

    public void setGuar(int guar) {
        this.guar = guar;
    }

    public void setHave(int have) {
        this.have = have;
    }

    public void setPrebuy(int prebuy) {
        this.prebuy = prebuy;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public RealmList<Commission> getCommissions() {
        return commissions;
    }

    public void setCommissions(RealmList<Commission> commissions) {
        this.commissions = commissions;
    }

    public void addCommission(Commission commission) {
        this.commissions.add(commission);
    }

   public String[][] getData() {
       return new String[][]{{"Аналог", analog}, {"Гарантия", String.valueOf(guar)}, {"Остаток у поставщика", String.valueOf(have)}, {"Отсрочка", String.valueOf(prebuy)},
               {"Производитель", maker}, {"Срок", String.valueOf(deliveryTime)}};
   }

    @Override
    public String toString() {
        return "Supplier{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", trust=" + trust +
                ", price=" + price +
                ", analog='" + analog + '\'' +
                ", guar=" + guar +
                ", have=" + have +
                ", prebuy=" + prebuy +
                ", maker='" + maker + '\'' +
                ", deliveryTime=" + deliveryTime +
                ", commissions=" + commissions +
                '}';
    }
}
