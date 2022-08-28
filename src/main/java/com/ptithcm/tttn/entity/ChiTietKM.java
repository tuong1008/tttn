package com.ptithcm.tttn.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CHITIETKM")
@AssociationOverrides({
        @AssociationOverride(name = "pk.khuyenMai",
                joinColumns = @JoinColumn(name = "MAKM")),
        @AssociationOverride(name = "pk.sanPham",
                joinColumns = @JoinColumn(name = "MASP"))})
public class ChiTietKM implements Serializable {

    @Embedded
    @Id
    ChiTietKMPK pk;

    @Column(name = "GIAMGIA")
    private int giamGia;

    public ChiTietKM() {

    }

    public ChiTietKM(ChiTietKMPK pk, int giamGia) {
        super();
        this.pk = pk;
        this.giamGia = giamGia;
    }

    public ChiTietKMPK getPk() {
        return pk;
    }

    public void setPk(ChiTietKMPK pk) {
        this.pk = pk;
    }

    public int getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(int giamGia) {
        this.giamGia = giamGia;
    }

}
