package com.ptithcm.tttn.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "NHACUNGCAP")
public class NhaCungCap implements Serializable {

    @Id
    @Column(name = "MANCC")
    private String maNCC;

    @Column(name = "TENNCC", columnDefinition = "nvarchar(255)")
    private String tenNCC;

    @Column(name = "SDT")
    private String sdt;

    @Column(name = "DIACHI", columnDefinition = "nvarchar(255)")
    private String diaChi;

    @Column(name = "EMAIL")
    private String email;

    @OneToMany(mappedBy = "nhaCungCap", fetch = FetchType.LAZY)
    @JsonBackReference
    private Collection<PhieuDat> phieuDats;

    @OneToMany(mappedBy = "nhaCungCap", fetch = FetchType.LAZY)
    @JsonBackReference
    private Collection<SanPham> sanPhams;

    public NhaCungCap() {

    }

//	public NhaCungCap(String maNCC, String tenNCC, String sdt, String diaChi, String email,
//			Collection<PhieuDat> phieuDats, Collection<SanPham> sanPhams) {
//		super();
//		this.maNCC = maNCC;
//		this.tenNCC = tenNCC;
//		this.sdt = sdt;
//		this.diaChi = diaChi;
//		this.email = email;
//		this.phieuDats = phieuDats;
//		this.sanPhams = sanPhams;
//	}

    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<PhieuDat> getPhieuDats() {
        return phieuDats;
    }

    public void setPhieuDats(Collection<PhieuDat> phieuDats) {
        this.phieuDats = phieuDats;
    }

    public Collection<SanPham> getSanPhams() {
        return sanPhams;
    }

    public void setSanPhams(Collection<SanPham> sanPhams) {
        this.sanPhams = sanPhams;
    }

}
