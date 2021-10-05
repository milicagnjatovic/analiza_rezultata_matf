package dataHandling;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "MG.PREDMET")
public class Predmet {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String oznaka;

    @Column
    private String naziv;

    @Column(name = "SEMESTAR", nullable = true)
    private Integer semestar;

    @Column
    private String katedra;

    @Column(name = "ESPB")
    private int espb;

    @OneToMany(mappedBy = "predmet")
    private List<Ispit> ispiti;

    public Predmet(){}

    public Predmet(String oznaka, String naziv) {
        this.oznaka = oznaka;
        this.naziv = naziv;
        }
    public Predmet(int id){
        this.id = id;
    }
    public Predmet(String oznaka, String naziv, String katedra, int semestar, int espb) {
        this.oznaka = oznaka;
        this.naziv = naziv;
        this.katedra = katedra;
        this.semestar = semestar;
        this.espb = espb;
    }

    public Predmet(String svi, String svi_predmeti, int id) {
        this.oznaka = svi;
        this.naziv = svi_predmeti;
        this.id = id;
    }

    public String getOznaka() {
        return oznaka.trim();
    }

    public String getNaziv() {
        return naziv.trim();
    }

    public Integer getSemestar() {
        return semestar;
    }

    public String getKatedra() {
        return katedra;
    }

    public int getId() {
        return id;
    }

    public int getEspb() {
        return espb;
    }

    public int getGodina() {
        return Math.floorDiv(semestar+1, 2);
    }

    public void setEspb(int espb) {
        this.espb = espb;
    }

//    private boolean called = false;
    public List<Ispit> getIspiti() {
//        if (called==true)
//            return ispiti;
//        called = true;
        Session session = dataHandling.HibernateUtil.getSessionFactory().openSession();
        String hql = "from dataHandling.Ispit as i where i.id.idpredmeta = :idpredmeta";
        Query<Ispit> upit  = session.createQuery(hql);
        upit.setParameter("idpredmeta", this.id);
        ispiti = upit.list();
        session.close();
        return ispiti;
    }

    public void setIspiti(List<Ispit> ispiti) {
        this.ispiti = ispiti;
    }

    public void setOznaka(String oznaka) {
        this.oznaka = oznaka;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setSemestar(Integer semestar) {
        this.semestar = semestar;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setKatedra(String katedra) {
        this.katedra = katedra;
    }

    @Override
    public String toString(){
        return oznaka + " " + naziv;
    }

    public String getInfo() {
        return oznaka +
                " " + naziv +
                " " + semestar +
                " " + katedra;
    }

    public double getProsek(){
        List<Integer> els = getIspiti().stream().filter(i -> i.getOcena()>5).map(i -> i.getOcena()).collect(Collectors.toList());
        if(els.isEmpty())
            return 5.0;
        return (double)els.stream().reduce(0, (a,b) -> a+b) / els.size();
    }

    public int getUpisalo(){
        int upisalo = getIspiti().size();
        return upisalo;
    }

    public int getPolozilo(){
        int polozilo = (int)getIspiti().stream().filter(i->i.getOcena()>5).count();
        return polozilo;
    }

    public boolean sacuvajPredmet() {
        Session session = dataHandling.HibernateUtil.getSessionFactory().openSession();
        Transaction tr=null;
        try {
            tr = session.beginTransaction();
            session.save(this);
            tr.commit();
        } catch (Exception e){
            e.printStackTrace();
            if (tr!=null)
                tr.rollback();
            session.close();
            return false;
        }
        session.close();
        return true;
    }
}
