package com.ptithcm.tttn.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "QUYEN")
public class Quyen implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "MAQUYEN")
    private int maQuyen;

    @Column(name = "TENQUYEN")
    private String tenQuyen;

    @OneToMany(mappedBy = "quyen", fetch = FetchType.EAGER)
    private Collection<TaiKhoan> taiKhoans;

    public Quyen() {

    }

    public Quyen(int maQuyen, String tenQuyen, Collection<TaiKhoan> taiKhoans) {
        super();
        this.maQuyen = maQuyen;
        this.tenQuyen = tenQuyen;
        this.taiKhoans = taiKhoans;
    }

    public int getMaQuyen() {
        return maQuyen;
    }

    public void setMaQuyen(int maQuyen) {
        this.maQuyen = maQuyen;
    }

    public String getTenQuyen() {
        return tenQuyen;
    }

    public void setTenQuyen(String tenQuyen) {
        this.tenQuyen = tenQuyen;
    }

    public Collection<TaiKhoan> getTaiKhoans() {
        return taiKhoans;
    }

    public void setTaiKhoans(Collection<TaiKhoan> taiKhoans) {
        this.taiKhoans = taiKhoans;
    }

}
