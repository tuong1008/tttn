package com.ptithcm.tttn.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "PHIEUTRA")
public class PhieuTra implements Serializable {

    @Id
    @Column(name = "MAPT")
    private String maPT;

    @Column(name = "NGAYLAP")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date ngayLap;

    @ManyToOne
    @JoinColumn(name = "MAHD")
    private HoaDon hoaDon;

    @ManyToOne
    @JoinColumn(name = "MANV")
    private NhanVien nhanVien;

    @OneToMany(mappedBy = "phieuTra", fetch = FetchType.EAGER)
    private Collection<CTDonHang> ctDonHangs;

    public PhieuTra() {

    }

    public PhieuTra(String maPT, Date ngayLap, HoaDon hoaDon, NhanVien nhanVien, Collection<CTDonHang> ctDonHangs) {
        super();
        this.maPT = maPT;
        this.ngayLap = ngayLap;
        this.hoaDon = hoaDon;
        this.nhanVien = nhanVien;
        this.ctDonHangs = ctDonHangs;
    }

    public String getMaPT() {
        return maPT;
    }

    public void setMaPT(String maPT) {
        this.maPT = maPT;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public Collection<CTDonHang> getCtDonHangs() {
        return ctDonHangs;
    }

    public void setCtDonHangs(Collection<CTDonHang> ctDonHangs) {
        this.ctDonHangs = ctDonHangs;
    }

}
