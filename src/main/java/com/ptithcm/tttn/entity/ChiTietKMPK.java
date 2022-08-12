package com.ptithcm.tttn.entity;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class ChiTietKMPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "MAKM")
    private KhuyenMai khuyenMai;

    @ManyToOne
    @JoinColumn(name = "MASP")
    private SanPham sanPham;

    public ChiTietKMPK() {

    }

    public ChiTietKMPK(KhuyenMai khuyenMai, SanPham sanPham) {
        super();
        this.khuyenMai = khuyenMai;
        this.sanPham = sanPham;
    }

    public KhuyenMai getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(KhuyenMai khuyenMai) {
        this.khuyenMai = khuyenMai;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

}
