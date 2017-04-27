package com.ifpe.tads.descorp.model.compra;

import com.ifpe.tads.descorp.model.fornecedor.Fornecedor;
import com.ifpe.tads.descorp.model.usuario.Administrador;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Tiago Lima <tiagolimadev@outlook.com>
 */
@Entity
@Table(name = "TB_COMPRA")
public class Compra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private Double valorTotal;

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_DATA_COMPRA", nullable = false)
    private Date dataCompra;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_ADMINISTRADOR", referencedColumnName = "ID")
    private Administrador administrador;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_FORNECEDOR", referencedColumnName = "ID")
    private Fornecedor fornecedor;

    @OneToMany(mappedBy = "compra", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCompra> itensCompra;

    private void calcularValorTotal() {
        double total = 0;
        for (ItemCompra item : this.itensCompra) {
            total += item.getSubTotal();
        }
        this.valorTotal = total;
    }

    public void finalizarCompras() {
        //TO DO
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public List<ItemCompra> getItensCompra() {
        return itensCompra;
    }

    public void setItensCompra(List<ItemCompra> itensCompra) {
        this.itensCompra = itensCompra;
    }

    public Double getValorTotal() {
        this.getAdministrador();
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Compra)) {
            return false;
        }
        Compra other = (Compra) object;

        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }

        return false;
    }
    
    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return "com.ifpe.tads.descorp.model.compra.Compra[ id=" + id + ":" + df.format(dataCompra) + " ]";
    }
    
}
