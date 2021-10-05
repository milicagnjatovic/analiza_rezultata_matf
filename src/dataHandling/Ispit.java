package dataHandling;

import javax.persistence.*;

@Entity
@Table(name = "MG.ISPIT")
public class Ispit {
    @Id
    private IspitId id;

    @Column
    private Double poeni;

    @Column
    private int ocena;

    @Column
    private String datum;

    public Ispit(String indeks, Double poeni, Predmet predmet) {
        this.id = new IspitId(indeks, predmet.getId());
        this.poeni = poeni;
        izracunajOcenu();
        this.predmet = predmet;
    }

    private void izracunajOcenu(){
        if(this.poeni>=91)
            this.ocena = 10;
        else if (this.poeni>=81)
            this.ocena = 9;
        else if (this.poeni>=71)
            this.ocena = 8;
        else if (this.poeni>=61)
            this.ocena = 7;
        else if (this.poeni>=51)
            this.ocena = 6;
        else
            this.ocena = 5;
    }


    @Override
    public String toString() {
        return id.getIndeks() +
                 " " + predmet.getOznaka() +
                "\t" + poeni +
                "\t" + ocena;
    }

    @ManyToOne
    @JoinColumn(name="IDPREDMETA", referencedColumnName = "ID", updatable = false, insertable = false)
    private Predmet predmet;

    @ManyToOne
    @JoinColumn(name="INDEKS", referencedColumnName = "INDEKS", updatable = false, insertable = false)
    private Student student;

    public Ispit() {
    }

    public Ispit(IspitId id, Double poeni, int ocena, String datum) {
        this.id = id;
        this.poeni = poeni;
        this.ocena = ocena;
        this.datum = datum;
    }

    public IspitId getId() {
        return id;
    }

    public void setId(IspitId id) {
        this.id = id;
    }

    public Double getPoeni() {
        return poeni;
    }

    public void setPoeni(Double poeni) {
        this.poeni = poeni;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public Predmet getPredmet() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
}
