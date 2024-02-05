package com.app.tienda.model.request;

public class Domicilio {
  private Long id;
  private String street;
  private Integer mz;
  private Integer lt;
  private String col;
  private String cp;
  private String del;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public Integer getMz() {
    return mz;
  }

  public void setMz(Integer mz) {
    this.mz = mz;
  }

  public Integer getLt() {
    return lt;
  }

  public void setLt(Integer lt) {
    this.lt = lt;
  }

  public String getCol() {
    return col;
  }

  public void setCol(String col) {
    this.col = col;
  }

  public String getCp() {
    return cp;
  }

  public void setCp(String cp) {
    this.cp = cp;
  }

  public String getDel() {
    return del;
  }

  public void setDel(String del) {
    this.del = del;
  }

  @Override
  public String toString() {
    return "Domicilio{" +
            "id=" + id +
            ", street='" + street + '\'' +
            ", mz=" + mz +
            ", lt=" + lt +
            ", col='" + col + '\'' +
            ", cp='" + cp + '\'' +
            ", del='" + del + '\'' +
            '}';
  }
}
