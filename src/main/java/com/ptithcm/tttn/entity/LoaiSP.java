package com.ptithcm.tttn.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "LOAISP")
public class LoaiSP implements Serializable {

    @Id
    @Column(name = "MALOAI")
    private String maLoai;

    @Column(name = "TENLOAI", columnDefinition = "nvarchar(255)")
    private String tenLoai;

    @OneToMany(mappedBy = "loaiSP", fetch = FetchType.LAZY)
    @JsonBackReference
    private Collection<SanPham> sanPhams;

    public LoaiSP() {

    }

    public LoaiSP(String maLoai, String tenLoai, Collection<SanPham> sanPhams) {
        super();
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.sanPhams = sanPhams;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public Collection<SanPham> getSanPhams() {
        return sanPhams;
    }

    public void setSanPhams(Collection<SanPham> sanPhams) {
        this.sanPhams = sanPhams;
    }

    @Override
    public String toString() {
        return "LoaiSP{" +
                "maLoai='" + maLoai + '\'' +
                ", tenLoai='" + tenLoai + '\'' +
                '}';
    }
}
