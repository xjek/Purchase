package ru.groupstp.procurementcommission.app;

public class Common {
    public static String plural(int count, String form1, String form2, String form5) {
        if (count == 0)
            return form5;
        if (count <= 1) {
            return form1;
        } else if (count < 5) {
            return form2;
        }
        return form5;
    }
}
