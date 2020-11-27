package fun.gatsby.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Tops.
 */
@Entity
@Table(name = "tops")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tops implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "link")
    private String link;

    @Column(name = "source")
    private String source;

    @Column(name = "time")
    private LocalDate time;

    @Column(name = "seq")
    private Integer seq;

    @Column(name = "note")
    private String note;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Tops title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public Tops link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSource() {
        return source;
    }

    public Tops source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LocalDate getTime() {
        return time;
    }

    public Tops time(LocalDate time) {
        this.time = time;
        return this;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public Integer getSeq() {
        return seq;
    }

    public Tops seq(Integer seq) {
        this.seq = seq;
        return this;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getNote() {
        return note;
    }

    public Tops note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tops)) {
            return false;
        }
        return id != null && id.equals(((Tops) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tops{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", link='" + getLink() + "'" +
            ", source='" + getSource() + "'" +
            ", time='" + getTime() + "'" +
            ", seq=" + getSeq() +
            ", note='" + getNote() + "'" +
            "}";
    }
}
