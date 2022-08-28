package com.ptithcm.tttn.common;

import com.ptithcm.tttn.DAOImpl.NhanVienDAOImpl;
import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Utils {

    public static String createUserName(String fullName) {
        fullName = Utils.covertToString(fullName);
        String res = "";
        String[] temp = fullName.split(" ");

        res = temp[temp.length - 1];
        for (int i = 0; i < temp.length - 1; i++) {
            res += temp[i].charAt(0);
        }
        int number = new NhanVienDAOImpl().getMaxNumberByName(res) + 1;
        res += number;
        return res.toUpperCase();
    }

    public static int createID(SessionFactory factory, String hql) {
        Session session = factory.getCurrentSession();
        Query query = session.createQuery(hql);
        List<Integer> list = query.list();
        return list.get(0);
    }

    public static String covertToString(String value) {
        try {
            String temp = Normalizer.normalize(value, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
