package com.ptithcm.tttn.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "TAIKHOAN")
public class TaiKhoan implements Serializable {

    @Id
    @Column(name = "TENDN")
    private String tenDN;

    @Column(name = "MATKHAU")
    private String matKhau;

    @ManyToOne
    @JoinColumn(name = "MAQUYEN")
    private Quyen quyen;

    @OneToMany(mappedBy = "taiKhoan", fetch = FetchType.EAGER)
    private Collection<KhachHang> khachHangs;

    @OneToMany(mappedBy = "taiKhoan", fetch = FetchType.EAGER)
    private Collection<NhanVien> nhanViens;

    public TaiKhoan() {

    }

    public TaiKhoan(String tenDN, String matKhau, Quyen quyen, Collection<KhachHang> khachHangs,
                    Collection<NhanVien> nhanViens) {
        super();
        this.tenDN = tenDN;
        this.matKhau = matKhau;
        this.quyen = quyen;
        this.khachHangs = khachHangs;
        this.nhanViens = nhanViens;
    }

    public String getTenDN() {
        return tenDN;
    }

    public void setTenDN(String tenDN) {
        this.tenDN = tenDN;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public Quyen getQuyen() {
        return quyen;
    }

    public void setQuyen(Quyen quyen) {
        this.quyen = quyen;
    }

    public Collection<KhachHang> getKhachHangs() {
        return khachHangs;
    }

    public void setKhachHangs(Collection<KhachHang> khachHangs) {
        this.khachHangs = khachHangs;
    }

    public Collection<NhanVien> getNhanViens() {
        return nhanViens;
    }

    public void setNhanViens(Collection<NhanVien> nhanViens) {
        this.nhanViens = nhanViens;
    }

}
