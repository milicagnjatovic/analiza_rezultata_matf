package dataHandling;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnosIspita {
    public static void unesiIspiteIzFajla(String fajl, Predmet predmet) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fajl, Charset.forName("UTF-16")));

        Pattern pattern = Pattern.compile(".*\"[ ]*(?<indeks>[0-9]+/[0-9]+)[ ]*\"[\t ]*" +
                "\"(?<ime>.*?)\".*?" +
                "\"(?<prezime>.*?)\".*" +
                "\"(?<poeni>.*?)\"$",
                Pattern.UNICODE_CASE);

        String l = null;
        while ((l = reader.readLine())!=null){
            Matcher m = pattern.matcher(l);
            if(m.find()){
                String indeks = m.group("indeks");
                String ime = m.group("ime");
                String prezime = m.group("prezime");
                Double poeni = Double.parseDouble(m.group("poeni").trim().replace(",", "."));

                Session session = dataHandling.HibernateUtil.getSessionFactory().openSession();

                Transaction tr = null;

                Student s = session.get(Student.class, indeks);
                if (s == null){
                    try {
                        tr = session.beginTransaction();
                        session.save(new Student(indeks, ime, prezime, null));
                        tr.commit();
                    }  catch (Exception e){
                        if (tr != null)
                            tr.rollback();
                        System.out.println("Neuspelo cuvanje stuenta " + indeks);
                        System.out.println(e.getMessage());
                        return;
                    }
                }

                Ispit ispit = new Ispit(indeks, poeni, predmet);
                try {
                    tr = session.beginTransaction();
                    session.save(ispit);
                    tr.commit();
                }  catch (Exception e){
                    if (tr != null)
                        tr.rollback();
                    System.out.println("Neuspelo cuvanje ispita " + ispit);
                    System.out.println(e.getMessage());
                    return;
                }
            }
        }
    }
}
