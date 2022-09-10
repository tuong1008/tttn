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

    @OneToMany(mappedBy = "taiKhoan", fetch = FetchType.LAZY)
    private Collection<NguoiDung> khachHangs;

    @OneToMany(mappedBy = "taiKhoan", fetch = FetchType.LAZY)
    private Collection<NguoiDung> nhanViens;

    public TaiKhoan() {

    }

    public TaiKhoan(String tenDN, String matKhau, Quyen quyen) {
        this.tenDN = tenDN;
        this.matKhau = matKhau;
        this.quyen = quyen;
    }
    
    

    public TaiKhoan(String tenDN, String matKhau, Quyen quyen, Collection<NguoiDung> khachHangs,
                    Collection<NguoiDung> nhanViens) {
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

    public Collection<NguoiDung> getKhachHangs() {
        return khachHangs;
    }

    public void setKhachHangs(Collection<NguoiDung> khachHangs) {
        this.khachHangs = khachHangs;
    }

    public Collection<NguoiDung> getNhanViens() {
        return nhanViens;
    }

    public void setNhanViens(Collection<NguoiDung> nhanViens) {
        this.nhanViens = nhanViens;
    }

}
