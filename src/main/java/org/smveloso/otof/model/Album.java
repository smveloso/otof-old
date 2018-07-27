package org.smveloso.otof.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author sergio
 */
@Entity
@Table(name="album")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
    @NamedQuery(name="Album.byName", query="from Album a where a.name = :name")
})
public abstract class Album implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    protected String name;
        
    /** For future use.
     *  Is this album available only on the cloud side ?
     */
    @Column(nullable = false)
    protected boolean serverSide;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isServerSide() {
        return serverSide;
    }

    public void setServerSide(boolean serverSide) {
        this.serverSide = serverSide;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
     
    @OneToMany(mappedBy = "album")
    public Set<Location> locations;
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Album)) {
            return false;
        }
        Album other = (Album) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.smveloso.otof.model.Album[ id=" + id + " ]";
    }
    
}
