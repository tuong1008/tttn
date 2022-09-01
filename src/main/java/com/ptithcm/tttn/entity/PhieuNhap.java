package com.ptithcm.tttn.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;


@Entity
@Table(name = "PHIEUNHAP")
public class PhieuNhap implements Serializable {

    @Id
    @Column(name = "MAPN")
    private String maPN;

    @Column(name = "NGAYTAO")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date ngayTao;

    @Column(name = "TONGTIEN")
    private long tongTien;

    @ManyToOne
    @JoinColumn(name = "MANV")
    private NhanVien nhanVien;

    @OneToMany(mappedBy = "pk.phieuNhap", fetch = FetchType.LAZY)
    private Collection<CTPhieuNhap> ctPhieuNhaps;

    @OneToOne(mappedBy = "phieuNhap", fetch = FetchType.LAZY)
    private PhieuDat phieuDat;

    public PhieuNhap() {

    }

    public PhieuNhap(String maPN, Date ngayTao, long tongTien, NhanVien nhanVien, Collection<CTPhieuNhap> ctPhieuNhaps,
                     PhieuDat phieuDat) {
        super();
        this.maPN = maPN;
        this.ngayTao = ngayTao;
        this.tongTien = tongTien;
        this.nhanVien = nhanVien;
        this.ctPhieuNhaps = ctPhieuNhaps;
        this.phieuDat = phieuDat;
    }

    public String getMaPN() {
        return maPN;
    }

    public void setMaPN(String maPN) {
        this.maPN = maPN;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public long getTongTien() {
        return tongTien;
    }

    public void setTongTien(long tongTien) {
        this.tongTien = tongTien;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public Collection<CTPhieuNhap> getCtPhieuNhaps() {
        return ctPhieuNhaps;
    }

    public void setCtPhieuNhaps(Collection<CTPhieuNhap> ctPhieuNhaps) {
        this.ctPhieuNhaps = ctPhieuNhaps;
    }

    public PhieuDat getPhieuDat() {
        return phieuDat;
    }

    public void setPhieuDat(PhieuDat phieuDat) {
        this.phieuDat = phieuDat;
    }

}
