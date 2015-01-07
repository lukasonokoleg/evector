package eu.itreegroup.spark.application.shared.db.bean;

import java.io.Serializable;
import lt.jmsys.spark.bind.annotations.Generated;

@Generated
public class User_menu implements Serializable {
	private final static long serialVersionUID = 1;

	private String menu_code;

	private String menu_type;

	private String menu_title;

	private String parent_menu_code;

	private String function_code;

	private Double seq_no;

	public User_menu() {
	}

	public User_menu(String menu_code, String menu_type, String menu_title,
			String parent_menu_code, String function_code, Double seq_no) {
		this.menu_code = menu_code;
		this.menu_type = menu_type;
		this.menu_title = menu_title;
		this.parent_menu_code = parent_menu_code;
		this.function_code = function_code;
		this.seq_no = seq_no;
	}

	public String getMenu_code() {
		return this.menu_code;
	}

	public void setMenu_code(String menu_code) {
		this.menu_code = menu_code;
	}

	public String getMenu_type() {
		return this.menu_type;
	}

	public void setMenu_type(String menu_type) {
		this.menu_type = menu_type;
	}

	public String getMenu_title() {
		return this.menu_title;
	}

	public void setMenu_title(String menu_title) {
		this.menu_title = menu_title;
	}

	public String getParent_menu_code() {
		return this.parent_menu_code;
	}

	public void setParent_menu_code(String parent_menu_code) {
		this.parent_menu_code = parent_menu_code;
	}

	public String getFunction_code() {
		return this.function_code;
	}

	public void setFunction_code(String function_code) {
		this.function_code = function_code;
	}

	public Double getSeq_no() {
		return this.seq_no;
	}

	public void setSeq_no(Double seq_no) {
		this.seq_no = seq_no;
	}

	@Override
	public String toString() {
		return User_menu.class.getName() + "@[" + "menu_code = " + menu_code
				+ ", menu_type = " + menu_type + ", menu_title = " + menu_title
				+ ", parent_menu_code = " + parent_menu_code
				+ ", function_code = " + function_code + ", seq_no = " + seq_no
				+ "]";
	}

}
