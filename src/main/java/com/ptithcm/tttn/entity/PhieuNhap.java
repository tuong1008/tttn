package com.ptithcm.tttn.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;


@Entity @Table(name = "PHIEUNHAP")
public class PhieuNhap implements Serializable{
	
	@Id @Column(name = "MAPN")
	private String maPD;
	
	@Column(name = "NGAYTAO") @Temporal(TemporalType.DATE) @DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date ngayTao;
	
	@Column(name = "TONGTIEN")
	private long tongTien;
	
	@ManyToOne @JoinColumn(name = "MANV")
	private NhanVien nhanVien;
	
	@OneToMany(mappedBy = "pk.phieuNhap", fetch = FetchType.EAGER)
	private Collection<CTPhieuNhap> ctPhieuNhaps;
	
	@ManyToOne @JoinColumn(name = "MAPD")
	private PhieuDat phieuDat;
	
	public PhieuNhap() {
		
	}

	public PhieuNhap(String maPD, Date ngayTao, long tongTien, NhanVien nhanVien, Collection<CTPhieuNhap> ctPhieuNhaps,
			PhieuDat phieuDat) {
		super();
		this.maPD = maPD;
		this.ngayTao = ngayTao;
		this.tongTien = tongTien;
		this.nhanVien = nhanVien;
		this.ctPhieuNhaps = ctPhieuNhaps;
		this.phieuDat = phieuDat;
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
