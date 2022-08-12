package com.ptithcm.tttn.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "CTDONHANG")
@AssociationOverrides({
        @AssociationOverride(name = "pk.sanPham",
                joinColumns = @JoinColumn(name = "MASP")),
        @AssociationOverride(name = "pk.donHang",
                joinColumns = @JoinColumn(name = "MADH"))})
public class CTDonHang implements Serializable {

    @Embedded
    @Id
    CTDonHangPK pk;

    @Column(name = "GIA")
    private long gia;

    @Column(name = "SL")
    private int sl;

    @Column(name = "SLTRA")
    private Integer slTra;

    @ManyToOne
    @JoinColumn(name = "MAPT")
    private PhieuTra phieuTra;

    public CTDonHang() {

    }

    public CTDonHang(CTDonHangPK pk, long gia, int sl, Integer slTra, PhieuTra phieuTra) {
        super();
        this.pk = pk;
        this.gia = gia;
        this.sl = sl;
        this.slTra = slTra;
        this.phieuTra = phieuTra;
    }

    public CTDonHangPK getPk() {
        return pk;
    }

    public void setPk(CTDonHangPK pk) {
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

    public Integer getSlTra() {
        return slTra;
    }

    public void setSlTra(Integer slTra) {
        this.slTra = slTra;
    }

    public PhieuTra getPhieuTra() {
        return phieuTra;
    }

    public void setPhieuTra(PhieuTra phieuTra) {
        this.phieuTra = phieuTra;
    }

}
