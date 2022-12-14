package com.ptithcm.tttn.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;


@Entity
@Table(name = "PHIEUDAT")
public class PhieuDat implements Serializable {

    @Id
    @Column(name = "MAPD")
    private String maPD;

    @Column(name = "NGAYTAO")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date ngayTao;

    @ManyToOne
    @JoinColumn(name = "MANCC")
    private NhaCungCap nhaCungCap;

    @ManyToOne
    @JoinColumn(name = "MANV")
    private NguoiDung nhanVien;

    @OneToMany(mappedBy = "pk.phieuDat", fetch = FetchType.LAZY)
    private Collection<CTPhieuDat> ctPhieuDats;

    @OneToOne(mappedBy = "phieuDat")
    private PhieuNhap phieuNhap;

    public PhieuDat() {

    }

    public PhieuDat(String maPD, Date ngayTao, NhaCungCap nhaCungCap, NguoiDung nhanVien,
                    Collection<CTPhieuDat> ctPhieuDats, PhieuNhap phieuNhap) {
        super();
        this.maPD = maPD;
        this.ngayTao = ngayTao;
        this.nhaCungCap = nhaCungCap;
        this.nhanVien = nhanVien;
        this.ctPhieuDats = ctPhieuDats;
        this.phieuNhap = phieuNhap;
    }

    public String getMaPD() {
        return maPD;
    }

    public void setMaPD(String maPD) {
        this.maPD = maPD;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public NguoiDung getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NguoiDung nhanVien) {
        this.nhanVien = nhanVien;
    }

    public Collection<CTPhieuDat> getCtPhieuDats() {
        return ctPhieuDats;
    }

    public void setCtPhieuDats(Collection<CTPhieuDat> ctPhieuDats) {
        this.ctPhieuDats = ctPhieuDats;
    }

    public PhieuNhap getPhieuNhap() {
        return phieuNhap;
    }

    public void setPhieuNhap(PhieuNhap phieuNhap) {
        this.phieuNhap = phieuNhap;
    }
    
}
