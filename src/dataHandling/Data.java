package dataHandling;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Data {
    public static Predmet trenutniPredmet = new Predmet("SVI", "Svi predmeti", 0);
    public static final Predmet sviPredmeti = new Predmet("SVI", "Svi predmeti", 0);

    // unos ispita
    public static String fajl;

    //prikaz indeksa
    public static Student student = null;

    public static double poeniUpperBound = 120;

    public static List<Predmet> getPredmete(){
        Session session = dataHandling.HibernateUtil.getSessionFactory().openSession();
        String hql = "from dataHandling.Predmet";
        Query<Predmet> upit  = session.createQuery(hql);
        List<Predmet> ret = upit.list();
        session.close();
        return ret;
    }

    public static List<String> getOznakePredmeta(){
        return getPredmete().stream().map(p -> p.getOznaka()).collect(Collectors.toList());
    }

    private static List<Ispit> ispiti = null;
    public static List<Ispit> getIspite(){
        if(ispiti!=null)
            return ispiti;

        Session session = dataHandling.HibernateUtil.getSessionFactory().openSession();
        String hql = "from dataHandling.Ispit";
        Query<Ispit> upit  = session.createQuery(hql);
        ispiti = upit.list();
        session.close();
        return ispiti;
    }

    public static int upisaloSve(){
        int upisalo = Data.getIspite().size();
        return upisalo;
    }

    public static int poloziloSve(){
        int polozilo = (int)Data.getIspite().stream().filter(i->i.getOcena()>5).count();
        return polozilo;
    }

    public static double prosekSvih(){
        List<Integer> els = Data.getIspite().stream().filter(i -> i.getOcena()>5).map(i -> i.getOcena()).collect(Collectors.toList());
        if (els.isEmpty())
            return 5.0;
        return (double)els.stream().reduce(0, (a,b) -> a+b) / els.size();
    }

    public static void setStudent(String indeks){
        Session session = dataHandling.HibernateUtil.getSessionFactory().openSession();
        Data.student = session.get(Student.class, indeks.trim());
        session.close();
    }

    private static String lastPieString = "";

    public static String getLastPieString() {
        return lastPieString;
    }

    public static ObservableList<PieChart.Data> ocenePieData(Predmet predmet) {
        List<Ispit> ispiti = null;
        if (predmet == null)
            ispiti = getIspite();
        else
            ispiti = predmet.getIspiti();
        int palo = 0;
        int sest = 0;
        int sedam = 0;
        int osam = 0;
        int devet = 0;
        int deset = 0;
        int ukupno = 0;
        for (Ispit i : ispiti){
            ukupno += 1;
            switch (i.getOcena()){
                case 10: deset+=1; break;
                case 9: devet+=1;break;
                case 8: osam+=1;break;
                case 7: sedam+=1;break;
                case 6: sest+=1;break;
                default: palo += 1;
            }
        }

        lastPieString = "ocena polozilo %\n" +
                String.format("%5d %4d %5.2f",10, deset, deset*100.0/ukupno) + "%\n" +
                String.format("%5d %4d %5.2f",9, devet, devet*100.0/ukupno) + "%\n" +
                String.format("%5d %4d %5.2f",8, osam, osam*100.0/ukupno) + "%\n" +
                String.format("%5d %4d %5.2f",7, sedam, sedam*100.0/ukupno) + "%\n" +
                String.format("%5d %4d %5.2f",6, sest, sest*100.0/ukupno) + "%\n" +
                String.format("%5d %4d %5.2f",5, palo, palo*100.0/ukupno) + "%\n";

        return FXCollections.observableArrayList(
                new PieChart.Data("5", palo),
                new PieChart.Data("6", sest),
                new PieChart.Data("7", sedam),
                new PieChart.Data("8", osam),
                new PieChart.Data("9", devet),
                new PieChart.Data("10", deset)
        );
    }

    public static ObservableList<PieChart.Data> indeksiPieData(Predmet predmet) {
        List<Ispit> ispiti = null;
        if (predmet == null)
            ispiti = getIspite();
        else
            ispiti = predmet.getIspiti();

        Map<String, Integer> indeksi = new HashMap<>();
        for (Ispit i: ispiti) {
            String indeks = i.getId().getIndeks().split("/")[1].strip();
            if(indeksi.containsKey(indeks))
                indeksi.put(indeks, indeksi.get(indeks)+1);
            else
                indeksi.put(indeks, 1);
        }

        ArrayList<PieChart.Data> data = new ArrayList<>();
        for (String key:indeksi.keySet()) {
            PieChart.Data d =new PieChart.Data(key, indeksi.get(key));
            data.add(d);
        }

        return FXCollections.observableArrayList(data);
    }

    public static List<XYChart.Series<String, Double>> prosekOcenaSvakogPredmeta = null;
    public static List<XYChart.Series<String, Double>> prolaznostSvakogPredmeta = null;

    public static Double maxProlaznost = 0.0;
    public static String maxProlaznostP;
    public static Double minProlaznost = 101.0;
    public static String minProlaznostP;

    public static Double maxProsek = 0.0;
    public static String maxProsekP;
    public static Double minProsek = 11.0;
    public static String minProsekP;

    public static ObservableList<PredmetiTableType> predmetiTableItems = FXCollections.observableArrayList();

    public static void setInfoOPredmetima (){
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "SELECT p.oznaka, count(*) as ukupno, " +
                "count (case when ocena>5 then 1 else null end) as polozilo, " +
                "sum(case when ocena>5 then ocena else 0 end) as sumaocena, " +
                "p.semestar, " +
                "sum(case when ocena=10 then 1 else 0 end) as deset, " +
                "sum(case when ocena=9 then 1 else 0 end) as devet, " +
                "sum(case when ocena=8 then 1 else 0 end) as osam, " +
                "sum(case when ocena=7 then 1 else 0 end) as sedam, " +
                "sum(case when ocena=6 then 1 else 0 end) as sest " +
                "from Ispit as i join Predmet as p " +
                "on p.id = i.id.idpredmeta " +
                "group by i.id.idpredmeta, p.oznaka, p.semestar " +
                "order by p.oznaka asc";
        Query<Object[]> q = session.createQuery(hql, Object[].class);
        List<Object[]> results = q.list();
        prolaznostSvakogPredmeta = new ArrayList<>();
        prosekOcenaSvakogPredmeta = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            XYChart.Series s1 = new XYChart.Series();
            s1.setName(i+1 + ". godina");
            prosekOcenaSvakogPredmeta.add(s1);
            XYChart.Series s2 = new XYChart.Series();
            s2.setName(i+1 + ". godina");
            prolaznostSvakogPredmeta.add(s2);
        }
        for(Object[] o : results){
            String oznaka = o[0].toString().trim();
            int ukupno = Integer.parseInt(o[1].toString().trim());
            int polozilo = Integer.parseInt(o[2].toString().trim());
            int ocene = Integer.parseInt(o[3].toString().trim());
            int semestar = Integer.parseInt(o[4].toString().trim());

            int o10 = Integer.parseInt(o[5].toString().trim());
            int o9 = Integer.parseInt(o[6].toString().trim());
            int o8 = Integer.parseInt(o[7].toString().trim());
            int o7 = Integer.parseInt(o[8].toString().trim());
            int o6 = Integer.parseInt(o[9].toString().trim());

            Double prosek = ocene*1.0/polozilo;

            XYChart.Data d1 = new XYChart.Data<String, Double>(oznaka, prosek);
            d1.setNode(new HoverTresholdNode(String.format("%.2f", prosek)));
            prosekOcenaSvakogPredmeta.get(Math.floorDiv(semestar+1, 2)-1).getData().add(d1);
            if (prosek>maxProsek){
                maxProsek=prosek;
                maxProsekP = oznaka;
            }
            if (prosek<minProsek){
                minProsek=prosek;
                minProsekP = oznaka;
            }

            Double prolaznost = polozilo*100.0/ukupno;
            XYChart.Data d2 = new XYChart.Data<String, Double>(oznaka, prolaznost);
            d2.setNode(new HoverTresholdNode(String.format("%.2f", prolaznost)+"%"));
            prolaznostSvakogPredmeta.get(Math.floorDiv(semestar+1, 2)-1).getData().add(d2);
            if (prolaznost>maxProlaznost){
                maxProlaznost=prolaznost;
                maxProlaznostP = oznaka;
            }
            if (prolaznost<minProlaznost){
                minProlaznost=prolaznost;
                minProlaznostP = oznaka;
            }
            predmetiTableItems.add(new PredmetiTableType(oznaka, prosek, prolaznost, o10,o9,o8,o7,o6, semestar));
        }
//        predmetiTableItems.forEach(e-> System.out.println(e.toString()));
    }

    public static List<XYChart.Series<String,Integer>> IndeksOceneData(Predmet predmet){
        String p = "";
        if (predmet!=null)
            p = "WHERE idpredmeta = :idpredmeta ";

        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "SELECT ocena, SUBSTR(indeks,LENGTH(TRIM(indeks))- 3) as GODINA, COUNT(*) as NUM " +
                "FROM Ispit " +
                 p +
                "GROUP BY ocena, SUBSTR(indeks,LENGTH(TRIM(indeks))- 3) " +
                "ORDER BY GODINA ";
        Query<Object[]> q = session.createQuery(hql, Object[].class);
        if (predmet != null)
            q.setParameter("idpredmeta", predmet.getId());
        List<Object[]> results = q.list();

        List<XYChart.Series<String, Integer>> lst = new ArrayList<>();

        XYChart.Series<String, Integer> last = null; //new XYChart.Series<>();
        String currGodina = "";
        for (Object[] o: results) {
            String ocena = o[0].toString();
            if (ocena.equals("5"))
                ocena = "palo";
            String godina = o[1].toString().trim();
            int broj = Integer.parseInt(o[2].toString());
            if (godina.equals(currGodina)){
                last.getData().add(new XYChart.Data<>(ocena, broj));
            }
            else {
                if (last!=null)
                    lst.add(last);
                last=new XYChart.Series<>();
                last.setName(godina);
                last.getData().add(new XYChart.Data<>(ocena, broj));
                currGodina = godina;
            }
        }
        if(last == null)
            return lst;
        lst.add(last);
        session.close();
        return lst;
    }


    public static XYChart.Series PoeniData(Predmet p) {
        poeniUpperBound = 0;
        List<Ispit> ispiti = null;
        if(p==null)
            ispiti = Data.getIspite();
        else
            ispiti = p.getIspiti();
        Map<Integer, Integer> map = new HashMap<>();
        for (Ispit i : ispiti){
            int poeni = i.getPoeni().intValue();
            if (poeni<51)
                continue;
            if (map.containsKey(poeni))
                map.put(poeni, map.get(poeni)+1);
            else
                map.put(poeni, 1);
        }
        XYChart.Series series = new XYChart.Series();
        map.forEach((key, value) ->{
            if(value > poeniUpperBound)
                    poeniUpperBound = value;
            XYChart.Data<Integer, Integer> d = new XYChart.Data<>(key, value);
            d.setNode(new HoverTresholdNode(value+""));
            series.getData().add(d);
        });
        poeniUpperBound += 10;
        return series;
    }

    public static ObservableList<StudentiTableType> studentiTableItems() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "SELECT I.id.indeks, TRIM(S.ime), TRIM(S.prezime), \n" +
                "    SUM(CASE WHEN I.ocena>5 THEN I.ocena ELSE 0 END) as OCENE,\n" +
                "    SUM(CASE WHEN I.ocena>5 THEN 1 ELSE 0 END) as POLOZENO\n" +
                "FROM Ispit as I JOIN Student as S \n" +
                "    ON I.id.indeks = S.indeks \n" +
                "GROUP BY I.id.indeks, S.ime, S.prezime";
        Query<Object[]> quiery = session.createQuery(hql, Object[].class);
        List<Object[]> results = quiery.list();
        ObservableList<StudentiTableType> ret = FXCollections.observableArrayList();
        for (Object[] o : results) {
            String indeks = o[0].toString().trim();
            String ime_prezime = o[1].toString() + " " + o[2].toString();
            int ocene = Integer.parseInt(o[3].toString());
            int polozeno = Integer.parseInt(o[4].toString());
            double prosek = 5.0;
            if (polozeno>0)
                prosek = ocene*1.0/polozeno;
            ret.add(new StudentiTableType(ime_prezime, indeks, polozeno, prosek));
        }
        return ret;
    }

    private static class HoverTresholdNode extends StackPane{
        public HoverTresholdNode(String val){
            setPrefSize(10,10);
            Label lbl = new Label(val);
            setMargin(lbl, new Insets(0,0, 15, 0));
            lbl.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
            lbl.setStyle("-fx-background-color: white; -fx-font-family: 'Ubuntu Condensed'; -fx-font-size: 15; -fx-padding: 3; -fx-background-radius: 5; -fx-font-weight: bold; -fx-border-radius: 5;");
            setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    getChildren().setAll(lbl);
                    setCursor(Cursor.NONE);
//                    setMinSize(50,50);
                    toFront();
                }
            });

            setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    getChildren().clear();
                    setCursor(Cursor.CROSSHAIR);
//                    toBack();
                }
            });
        }
    }
}
