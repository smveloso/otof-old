package org.smveloso.otof.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author sergiomv
 */
@Entity
@NamedQueries({
    @NamedQuery(name="Foto.porArquivo", query = "from Foto f where f.arquivo = :arquivo"),
    @NamedQuery(name="Foto.digestDuplicado", query="select f.digest from Foto f group by(f.digest) having count(f.digest) > 1"),
    @NamedQuery(name="Foto.naoArquivadas", query="from Foto f where f.unidades is null")
})
public class Foto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataTirada;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataIdentificada;

    // TODO Remover isso. A localização passará a ser associada a uma coleção.
    @Column(unique = true, length = 512, nullable = false)
    private String arquivo;
    
    @Column(nullable = false)
    private String digest;
    
    @Column(nullable = false)
    private Long tamanhoArquivo;
    
    @Column(nullable = true)
    private Boolean duplicate;

    @Column(nullable = true)
    private String unidades;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataTirada() {
        return dataTirada;
    }

    public void setDataTirada(Date dataTirada) {
        this.dataTirada = dataTirada;
    }

    public Date getDataIdentificada() {
        return dataIdentificada;
    }

    public void setDataIdentificada(Date dataIdentificada) {
        this.dataIdentificada = dataIdentificada;
    }
    
    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public Long getTamanhoArquivo() {
        return tamanhoArquivo;
    }

    public void setTamanhoArquivo(Long tamanhoArquivo) {
        this.tamanhoArquivo = tamanhoArquivo;
    }

    public Boolean isDuplicate() {
        return duplicate;
    }

    public void setDuplicate(Boolean duplicate) {
        this.duplicate = duplicate;
    }

    public String getUnidades() {
        return unidades;
    }

    @Transient
    public void addUnidade(String unidade) {
        if (this.unidades == null) {
            this.unidades = unidade;
        } else {
            this.unidades += ";" + unidade;
        }
    }
    
    @Transient
    public List<String> getUnidadesAsList() {
        List<String> unidadesAsList = null;
        if (this.unidades != null) {
            unidadesAsList = Arrays.asList(this.unidades.split(";"));
        }
        if (unidadesAsList == null) {
            unidadesAsList = new ArrayList<>();
        }
        return unidadesAsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Foto)) {
            return false;
        }
        Foto other = (Foto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.smveloso.otof.model.Foto[ id=" + id + " ]";
    }

}
