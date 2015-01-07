package eu.itreegroup.spark.application.shared.db.bean;

import java.io.Serializable;
import lt.jmsys.spark.bind.annotations.Generated;

@Generated
public class Find_char_translations implements Serializable {
	private final static long serialVersionUID = 1;

	private String ch_from;

	private String ch_to;

	public Find_char_translations() {
	}

	public Find_char_translations(String ch_from, String ch_to) {
		this.ch_from = ch_from;
		this.ch_to = ch_to;
	}

	public String getCh_from() {
		return this.ch_from;
	}

	public void setCh_from(String ch_from) {
		this.ch_from = ch_from;
	}

	public String getCh_to() {
		return this.ch_to;
	}

	public void setCh_to(String ch_to) {
		this.ch_to = ch_to;
	}

	@Override
	public String toString() {
		return Find_char_translations.class.getName() + "@[" + "ch_from = "
				+ ch_from + ", ch_to = " + ch_to + "]";
	}

}
