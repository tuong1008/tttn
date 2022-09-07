package com.ptithcm.tttn.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "NGUOIDUNG")
public class NguoiDung implements Serializable {

    @Id
    @Column(name = "USERID") 
    private String userId;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "HOTEN", columnDefinition = "nvarchar(255)")
    private String hoTen;

    @Column(name = "GIOITINH", columnDefinition = "nvarchar(255)")
    private String gioiTinh;

    @Column(name = "DIACHI", columnDefinition = "nvarchar(255)")
    private String diaChi;

    @Column(name = "SDT", unique = true)
    @Pattern(regexp = "^0[0-9]{9,10}$",message="Số điện thoại không đúng định dạng") 
    private String sdt;

    @Column(name = "TRANGTHAI")
    private int trangThai;

    @ManyToOne
    @JoinColumn(name = "TENDN", unique = true)
    private TaiKhoan taiKhoan;

    @OneToMany(mappedBy = "khachHang", fetch = FetchType.LAZY)
    private Collection<DonHang> donHangs;
    
    @OneToMany(mappedBy = "nhanVienD", fetch = FetchType.LAZY)
    private Collection<DonHang> donHangDs;

    @OneToMany(mappedBy = "nhanVienG", fetch = FetchType.LAZY)
    private Collection<DonHang> donHangGs;

    @OneToMany(mappedBy = "nhanVien", fetch = FetchType.LAZY)
    private Collection<HoaDon> hoaDons;

    @OneToMany(mappedBy = "nhanVien", fetch = FetchType.LAZY)
    private Collection<PhieuTra> phieuTras;

    @OneToMany(mappedBy = "nhanVien", fetch = FetchType.LAZY)
    private Collection<PhieuDat> phieuDats;

    @OneToMany(mappedBy = "nhanVien", fetch = FetchType.LAZY)
    private Collection<PhieuNhap> phieuNhaps;
    //
    @OneToMany(mappedBy = "nhanVien", fetch = FetchType.LAZY)
    private Collection<KhuyenMai> khuyenMais;

    public NguoiDung() {

    }

    public NguoiDung(String userId, String email, String hoTen, String gioiTinh, String diaChi, String sdt, int trangThai,
                     TaiKhoan taiKhoan, Collection<DonHang> donHangs) {
        super();
        this.userId = userId;
        this.email = email;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.trangThai = trangThai;
        this.taiKhoan = taiKhoan;
        this.donHangs = donHangs;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public Collection<DonHang> getDonHangs() {
        return donHangs;
    }

    public void setDonHangs(Collection<DonHang> donHangs) {
        this.donHangs = donHangs;
    }

    public Collection<DonHang> getDonHangDs() {
        return donHangDs;
    }

    public void setDonHangDs(Collection<DonHang> donHangDs) {
        this.donHangDs = donHangDs;
    }

    public Collection<DonHang> getDonHangGs() {
        return donHangGs;
    }

    public void setDonHangGs(Collection<DonHang> donHangGs) {
        this.donHangGs = donHangGs;
    }

    public Collection<HoaDon> getHoaDons() {
        return hoaDons;
    }

    public void setHoaDons(Collection<HoaDon> hoaDons) {
        this.hoaDons = hoaDons;
    }

    public Collection<PhieuTra> getPhieuTras() {
        return phieuTras;
    }

    public void setPhieuTras(Collection<PhieuTra> phieuTras) {
        this.phieuTras = phieuTras;
    }

    public Collection<PhieuDat> getPhieuDats() {
        return phieuDats;
    }

    public void setPhieuDats(Collection<PhieuDat> phieuDats) {
        this.phieuDats = phieuDats;
    }

    public Collection<PhieuNhap> getPhieuNhaps() {
        return phieuNhaps;
    }

    public void setPhieuNhaps(Collection<PhieuNhap> phieuNhaps) {
        this.phieuNhaps = phieuNhaps;
    }

    public Collection<KhuyenMai> getKhuyenMais() {
        return khuyenMais;
    }

    public void setKhuyenMais(Collection<KhuyenMai> khuyenMais) {
        this.khuyenMais = khuyenMais;
    }

    
}
