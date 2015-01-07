package eu.itreegroup.spark.application.shared.db.bean;

import java.io.Serializable;
import lt.jmsys.spark.bind.annotations.Generated;


@Generated
public class Spr_paging_ot implements Serializable {
  private final static long serialVersionUID = 1;

  private Double cnt;

  private Double skip_rows;

  private Double page_size;

  private String order_clause;


  public Spr_paging_ot() {
  }

  public Spr_paging_ot(Double cnt, Double skip_rows, Double page_size, String order_clause) {
    this.cnt = cnt;
    this.skip_rows = skip_rows;
    this.page_size = page_size;
    this.order_clause = order_clause;
  }

  public Spr_paging_ot(Spr_paging_ot src) {
    if (src == null)
      return;
    
    this.cnt = src.getCnt();
    this.skip_rows = src.getSkip_rows();
    this.page_size = src.getPage_size();
    this.order_clause = src.getOrder_clause();
  }

  public Double getCnt() {
    return this.cnt;
  }

  public void setCnt(Double cnt) {
    this.cnt = cnt;
  }

  public Double getSkip_rows() {
    return this.skip_rows;
  }

  public void setSkip_rows(Double skip_rows) {
    this.skip_rows = skip_rows;
  }

  public Double getPage_size() {
    return this.page_size;
  }

  public void setPage_size(Double page_size) {
    this.page_size = page_size;
  }

  public String getOrder_clause() {
    return this.order_clause;
  }

  public void setOrder_clause(String order_clause) {
    this.order_clause = order_clause;
  }

  @Override
  public String toString() {
    return Spr_paging_ot.class.getName() + "@[" + "cnt = " + cnt + ", skip_rows = " + skip_rows + ", page_size = " + page_size + ", order_clause = " + order_clause + "]";
  }

}
