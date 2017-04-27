package com.ifpe.tads.descorp.model.venda;

import com.ifpe.tads.descorp.entrega.Entrega;
import com.ifpe.tads.descorp.model.usuario.Cliente;
import com.ifpe.tads.descorp.model.usuario.Operador;
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
@Table(name = "TB_VENDA")
public class Venda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private Double valorTotal;

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_DATA_VENDA", nullable = false)
    private Date dataVenda;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "ID_OPERADOR", referencedColumnName = "ID")
    private Operador operador;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID")
    private Cliente cliente;

    @OneToMany(mappedBy = "venda", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemVenda> itensVenda;
    
    @OneToMany(mappedBy = "venda", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Entrega> entregas;

    private void calcularValorTotal() {
        double total = 0;
        for (ItemVenda item : this.itensVenda) {
            total += item.getSubTotal();
        }
        this.valorTotal = total;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValorTotal() {
        this.calcularValorTotal();
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemVenda> getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(List<ItemVenda> itensVenda) {
        this.itensVenda = itensVenda;
    }

    public List<Entrega> getEntregas() {
        return entregas;
    }

    public void setEntregas(List<Entrega> entregas) {
        this.entregas = entregas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Venda)) {
            return false;
        }
        Venda other = (Venda) object;

        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }

        return false;
    }
    
    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return "com.ifpe.tads.descorp.model.venda.Venda[ id=" + id + ":" + df.format(dataVenda) + " ]";
    }
    
}
