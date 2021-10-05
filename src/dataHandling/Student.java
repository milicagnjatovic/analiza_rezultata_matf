package dataHandling;

import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "MG.STUDENT")
public class Student {
    @Id
    private String indeks;

    @Column
    private String ime;

    @Column
    private String prezime;

    @OneToMany(mappedBy = "student")
    private List<Ispit> ispiti;

    @Column
    private String pol;

    public Student(String indeks, String ime, String prezime, String pol) {
        this.indeks = indeks;
        this.ime = ime;
        this.prezime = prezime;
        this.pol = pol;
    }

    @Override
    public String toString() {
        return indeks +
                "\t" + ime +
                " " + prezime +
                " " + pol;
    }

    public Student(){}

    public String getIndeks() {
        return indeks.strip();
    }

    public String getIme() {
        return ime.strip();
    }

    public String getPrezime() {
        return prezime.strip();
    }

    public String getPol() {
        return pol;
    }

    public void setIndeks(String indeks) {
        this.indeks = indeks;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public List<Ispit> getIspiti() {
        Session session = dataHandling.HibernateUtil.getSessionFactory().openSession();
        String hql = "from dataHandling.Ispit as i where i.id.indeks = :indeks";
        Query<Ispit> upiti = session.createQuery(hql);
        upiti.setParameter("indeks", this.indeks);
        return upiti.list();
    }

    public void setIspiti(List<Ispit> ispiti) {
        this.ispiti = ispiti;
    }

    public Double getProsek(){
        List<Integer> ispiti = getIspiti().stream().filter(i->i.getOcena()>5).map(i->i.getOcena()).collect(Collectors.toList());
        int sum = ispiti.stream().reduce(0, (a,b) -> a+b);
        if (sum==0)
            return 5.00;
        return sum*1.0 / ispiti.size();
    };

    public String getOdnosPolozenih(){
        return getIspiti().stream().filter(i->i.getOcena()>5).count() + " / " + getIspiti().size();
    }
}
