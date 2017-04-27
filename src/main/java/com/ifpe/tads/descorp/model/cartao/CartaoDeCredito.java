package com.ifpe.tads.descorp.model.cartao;

import com.ifpe.tads.descorp.model.usuario.Cliente;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Tiago Lima <tiagolimadev@outlook.com>
 */
@Entity
@Table(name = "TB_CARTAO_CREDITO")
public class CartaoDeCredito implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "TXT_NUMERO")
    private String numero;
    
    @Column(name = "TXT_NOME")
    private String nome;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "DT_VALIDADE")
    private Date validade;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "TXT_BANDEIRA")
    private Bandeira bandeira;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID")
    private Cliente cliente;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public Bandeira getBandeira() {
        return bandeira;
    }

    public void setBandeira(Bandeira bandeira) {
        this.bandeira = bandeira;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CartaoDeCredito)) {
            return false;
        }
        CartaoDeCredito other = (CartaoDeCredito) object;
        
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        
        return false;
    }
    
    @Override
    public String toString() {
        return "com.ifpe.tads.descorp.model.cartao.CartaoDeCredito[ id=" + id + ":" + bandeira + " ]";
    }
    
}
