package com.ptithcm.tttn.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;


@Entity
@Table(name = "HOADON")
public class HoaDon implements Serializable {

    @Id
    @Column(name = "MAHD")
    private String maHD;

    @Column(name = "NGAYTAO")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date ngayTao;

    @Column(name = "MASOTHUE")
    private String maSoThue;

    @ManyToOne
    @JoinColumn(name = "MANV")
    private NhanVien nhanVien;

    @OneToOne
    @JoinColumn(name = "MADH", unique = true)
    private DonHang donHang;

    @OneToMany(mappedBy = "hoaDon", fetch = FetchType.LAZY)
    private Collection<PhieuTra> phieuTras;

    public HoaDon() {

    }

    public HoaDon(String maHD, Date ngayTao, String maSoThue, NhanVien nhanVien, DonHang donHang,
                  Collection<PhieuTra> phieuTras) {
        super();
        this.maHD = maHD;
        this.ngayTao = ngayTao;
        this.maSoThue = maSoThue;
        this.nhanVien = nhanVien;
        this.donHang = donHang;
        this.phieuTras = phieuTras;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getMaSoThue() {
        return maSoThue;
    }

    public void setMaSoThue(String maSoThue) {
        this.maSoThue = maSoThue;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public DonHang getDonHang() {
        return donHang;
    }

    public void setDonHang(DonHang donHang) {
        this.donHang = donHang;
    }

    public Collection<PhieuTra> getPhieuTras() {
        return phieuTras;
    }

    public void setPhieuTras(Collection<PhieuTra> phieuTras) {
        this.phieuTras = phieuTras;
    }

}
