package fun.gatsby.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A UserExt.
 */
@Entity
@Table(name = "user_ext")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserExt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "pthone")
    private String pthone;

    @OneToOne

    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "user_ext_news",
               joinColumns = @JoinColumn(name = "user_ext_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "news_id", referencedColumnName = "id"))
    private Set<News> news = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPthone() {
        return pthone;
    }

    public UserExt pthone(String pthone) {
        this.pthone = pthone;
        return this;
    }

    public void setPthone(String pthone) {
        this.pthone = pthone;
    }

    public User getUser() {
        return user;
    }

    public UserExt user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<News> getNews() {
        return news;
    }

    public UserExt news(Set<News> news) {
        this.news = news;
        return this;
    }

    public UserExt addNews(News news) {
        this.news.add(news);
        news.getUserExts().add(this);
        return this;
    }

    public UserExt removeNews(News news) {
        this.news.remove(news);
        news.getUserExts().remove(this);
        return this;
    }

    public void setNews(Set<News> news) {
        this.news = news;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserExt)) {
            return false;
        }
        return id != null && id.equals(((UserExt) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserExt{" +
            "id=" + getId() +
            ", pthone='" + getPthone() + "'" +
            "}";
    }
}
