package com.ptithcm.tttn.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;


@Entity
@Table(name = "DONHANG")
public class DonHang implements Serializable, Comparable<DonHang> {

    @Id
    @Column(name = "MADH")
    private String maDH;

    @Column(name = "TRANGTHAI", columnDefinition = "nvarchar(255)")
    private String trangThai;

    @Column(name = "HOTENNN", columnDefinition = "nvarchar(255)")
    private String hoTenNN;

    @Column(name = "DIACHINN", columnDefinition = "nvarchar(255)")
    private String diaChiNN;

    @Column(name = "SDTNN")
    private String sdtNN;

    @Column(name = "EMAILNN")
    private String emailNN;

    @Column(name = "NGAYTAO")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date ngayTao;

    @Column(name = "NGAYNHAN")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date ngayNhan;

    @Column(name = "TONGTIEN")
    private long tongTien;

    @ManyToOne
    @JoinColumn(name = "MANVD")
    private NguoiDung nhanVienD;

    @Column(columnDefinition = "nvarchar(255)")
    private String nhanVienG;

    @ManyToOne
    @JoinColumn(name = "MAKH")
    private NguoiDung khachHang;

    @OneToMany(mappedBy = "pk.donHang", fetch = FetchType.LAZY)
    private Collection<CTDonHang> ctDonHangs;

    @OneToMany(mappedBy = "donHang", fetch = FetchType.LAZY)
    private Collection<HoaDon> hoaDons;

    public DonHang() {

    }

    public DonHang(String maDH, String trangThai, String hoTenNN, String diaChiNN, String sdtNN, String emailNN,
                   Date ngayTao, Date ngayNhan, long tongTien, NguoiDung nhanVienD, String nhanVienG, NguoiDung khachHang,
                   Collection<CTDonHang> ctDonHangs, Collection<HoaDon> hoaDons) {
        super();
        this.maDH = maDH;
        this.trangThai = trangThai;
        this.hoTenNN = hoTenNN;
        this.diaChiNN = diaChiNN;
        this.sdtNN = sdtNN;
        this.emailNN = emailNN;
        this.ngayTao = ngayTao;
        this.ngayNhan = ngayNhan;
        this.tongTien = tongTien;
        this.nhanVienD = nhanVienD;
        this.nhanVienG = nhanVienG;
        this.khachHang = khachHang;
        this.ctDonHangs = ctDonHangs;
        this.hoaDons = hoaDons;
    }

    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getHoTenNN() {
        return hoTenNN;
    }

    public void setHoTenNN(String hoTenNN) {
        this.hoTenNN = hoTenNN;
    }

    public String getDiaChiNN() {
        return diaChiNN;
    }

    public void setDiaChiNN(String diaChiNN) {
        this.diaChiNN = diaChiNN;
    }

    public String getSdtNN() {
        return sdtNN;
    }

    public void setSdtNN(String sdtNN) {
        this.sdtNN = sdtNN;
    }

    public String getEmailNN() {
        return emailNN;
    }

    public void setEmailNN(String emailNN) {
        this.emailNN = emailNN;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Date getNgayNhan() {
        return ngayNhan;
    }

    public void setNgayNhan(Date ngayNhan) {
        this.ngayNhan = ngayNhan;
    }

    public long getTongTien() {
        return tongTien;
    }

    public void setTongTien(long tongTien) {
        this.tongTien = tongTien;
    }

    public NguoiDung getNhanVienD() {
        return nhanVienD;
    }

    public void setNhanVienD(NguoiDung nhanVienD) {
        this.nhanVienD = nhanVienD;
    }

    public String getNhanVienG() {
        return nhanVienG;
    }

    public void setNhanVienG(String nhanVienG) {
        this.nhanVienG = nhanVienG;
    }

    public NguoiDung getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(NguoiDung khachHang) {
        this.khachHang = khachHang;
    }

    public Collection<CTDonHang> getCtDonHangs() {
        return ctDonHangs;
    }

    public void setCtDonHangs(Collection<CTDonHang> ctDonHangs) {
        this.ctDonHangs = ctDonHangs;
    }

    public Collection<HoaDon> getHoaDons() {
        return hoaDons;
    }

    public void setHoaDons(Collection<HoaDon> hoaDons) {
        this.hoaDons = hoaDons;
    }

    @Override
    public int compareTo(DonHang o) {
        return o.ngayTao.compareTo(ngayTao);
    }

}
