package dataHandling;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class IspitId implements Serializable {
    private static final long VersionUid= 1L;

    @Column(name = "INDEKS")
    private String indeks;

    @Column(name = "IDPREDMETA")
    private int idpredmeta;

    public IspitId(String indeks, int idpredmeta) {
        this.indeks = indeks;
        this.idpredmeta = idpredmeta;
    }

    public IspitId() {
    }

    public String getIndeks() {
        return indeks;
    }

    public void setIndeks(String indeks) {
        this.indeks = indeks;
    }

    public int getIdpredmeta() {
        return idpredmeta;
    }

    public void setIdpredmeta(int idpredmeta) {
        this.idpredmeta = idpredmeta;
    }

    @Override
    public String toString() {
        return indeks +
                "\t" + idpredmeta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IspitId ispitId = (IspitId) o;
        return indeks == ispitId.indeks && idpredmeta == ispitId.idpredmeta;
    }

    @Override
    public int hashCode() {
        return Objects.hash(indeks, idpredmeta);
    }
}
