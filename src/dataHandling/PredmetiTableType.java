package dataHandling;

public class PredmetiTableType {
    private String predmet;
    private Double prosek;
    private double prolaznost;
    private int o10;
    private int o9;
    private int o8;
    private int o7;
    private int o6;
    private int semestar;

    public PredmetiTableType(String predmet, Double prosek, double prolaznost, int o10, int o9, int o8, int o7, int o6, int semestar) {
        this.predmet = predmet;
        this.prosek = prosek;
        this.prolaznost = prolaznost;
        this.o10 = o10;
        this.o9 = o9;
        this.o8 = o8;
        this.o7 = o7;
        this.o6 = o6;
        this.semestar = semestar;
    }

    public String getPredmet() {
        return predmet;
    }

    public void setPredmet(String predmet) {
        this.predmet = predmet;
    }

    public Double getProsek() {
        return prosek;
    }

    public void setProsek(Double prosek) {
        this.prosek = prosek;
    }

    public double getProlaznost() {
        return prolaznost;
    }

    public void setProlaznost(double prolaznost) {
        this.prolaznost = prolaznost;
    }

    public int getO10() {
        return o10;
    }

    public void setO10(int o10) {
        this.o10 = o10;
    }

    public int getO9() {
        return o9;
    }

    public void setO9(int o9) {
        this.o9 = o9;
    }

    public int getO8() {
        return o8;
    }

    public void setO8(int o8) {
        this.o8 = o8;
    }

    public int getO7() {
        return o7;
    }

    public void setO7(int o7) {
        this.o7 = o7;
    }

    public int getO6() {
        return o6;
    }

    public void setO6(int o6) {
        this.o6 = o6;
    }

    public int getSemestar() {
        return semestar;
    }

    public void setSemestar(int semestar) {
        this.semestar = semestar;
    }

    @Override
    public String toString() {
        return "PredmetiTableType{" +
                "predmet='" + predmet + '\'' +
                ", prosek=" + prosek +
                ", prolaznost=" + prolaznost +
                ", o10=" + o10 +
                ", o9=" + o9 +
                ", o8=" + o8 +
                ", o7=" + o7 +
                ", o6=" + o6 +
                ", semestar=" + semestar +
                '}';
    }
}
