package fun.gatsby.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A News.
 */
@Entity
@Table(name = "news")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "source")
    private String source;

    @Column(name = "link")
    private String link;

    @Column(name = "kind")
    private String kind;

    @Column(name = "time")
    private Instant time;

    @Column(name = "content")
    private String content;

    @ManyToMany(mappedBy = "news")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<UserExt> userExts = new HashSet<>();

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

    public News title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public News source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLink() {
        return link;
    }

    public News link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getKind() {
        return kind;
    }

    public News kind(String kind) {
        this.kind = kind;
        return this;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Instant getTime() {
        return time;
    }

    public News time(Instant time) {
        this.time = time;
        return this;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public News content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<UserExt> getUserExts() {
        return userExts;
    }

    public News userExts(Set<UserExt> userExts) {
        this.userExts = userExts;
        return this;
    }

    public News addUserExt(UserExt userExt) {
        this.userExts.add(userExt);
        userExt.getNews().add(this);
        return this;
    }

    public News removeUserExt(UserExt userExt) {
        this.userExts.remove(userExt);
        userExt.getNews().remove(this);
        return this;
    }

    public void setUserExts(Set<UserExt> userExts) {
        this.userExts = userExts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof News)) {
            return false;
        }
        return id != null && id.equals(((News) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "News{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", source='" + getSource() + "'" +
            ", link='" + getLink() + "'" +
            ", kind='" + getKind() + "'" +
            ", time='" + getTime() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }
}
