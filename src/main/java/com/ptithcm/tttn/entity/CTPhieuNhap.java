package com.ptithcm.tttn.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CTPHIEUNHAP")
@AssociationOverrides({
        @AssociationOverride(name = "pk.sanPham",
                joinColumns = @JoinColumn(name = "MASP")),
        @AssociationOverride(name = "pk.phieuNhap",
                joinColumns = @JoinColumn(name = "MAPN"))})
public class CTPhieuNhap implements Serializable {

    @Embedded
    @Id
    CTPhieuNhapPK pk;

    @Column(name = "GIA")
    private long gia;

    @Column(name = "SL")
    private int sl;

    public CTPhieuNhap() {

    }

    public CTPhieuNhap(CTPhieuNhapPK pk, long gia, int sl) {
        super();
        this.pk = pk;
        this.gia = gia;
        this.sl = sl;
    }

    public CTPhieuNhapPK getPk() {
        return pk;
    }

    public void setPk(CTPhieuNhapPK pk) {
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
