package com.ptithcm.tttn.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CTPHIEUDAT")
@AssociationOverrides({
        @AssociationOverride(name = "pk.sanPham",
                joinColumns = @JoinColumn(name = "MASP")),
        @AssociationOverride(name = "pk.phieuDat",
                joinColumns = @JoinColumn(name = "MAPD"))})
public class CTPhieuDat implements Serializable {

    @Embedded
    @Id
    CTPhieuDatPK pk;

    @Column(name = "GIA")
    private long gia;

    @Column(name = "SL")
    private int sl;

    public CTPhieuDat() {

    }

    public CTPhieuDat(CTPhieuDatPK pk, long gia, int sl) {
        super();
        this.pk = pk;
        this.gia = gia;
        this.sl = sl;
    }

    public CTPhieuDatPK getPk() {
        return pk;
    }

    public void setPk(CTPhieuDatPK pk) {
        this.pk = pk;
    }

    public long getGia() {
        return gia;
    }

    public void setGia(long gia) {
        this.gia = gia;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

}
