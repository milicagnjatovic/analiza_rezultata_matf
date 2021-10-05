package dataHandling;

public class StudentiTableType {
    private String ime_prezime;
    private String indeks;
    private int polozeno;
    private double prosek;

    @Override
    public String toString() {
        return indeks + " " + ime_prezime + " " + polozeno +
                " " + prosek;
    }

    public StudentiTableType(String ime_prezime, String indeks, int polozeno, double prosek) {
        this.ime_prezime = ime_prezime;
        this.indeks = indeks;
        this.polozeno = polozeno;
        this.prosek = prosek;
    }

    public StudentiTableType() {
    }

    public String getIme_prezime() {
        return ime_prezime;
    }

    public void setIme_prezime(String ime_prezime) {
        this.ime_prezime = ime_prezime;
    }

    public String getIndeks() {
        return indeks;
    }

    public void setIndeks(String indeks) {
        this.indeks = indeks;
    }

    public int getPolozeno() {
        return polozeno;
    }

    public void setPolozeno(int polozeno) {
        this.polozeno = polozeno;
    }

    public double getProsek() {
        return prosek;
    }

    public void setProsek(double prosek) {
        this.prosek = prosek;
    }
}
