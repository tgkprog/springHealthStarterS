package com.sel2in.smsWebSend.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Doctor")
public class ActiviyDailyMain implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	@Column(name = "authorId", unique = false)
	private Integer authorId;

	@Column(name = "doc_spec")
	private String doctorSpec;

	@Column(name = "accepting_apts")
	private Boolean acceptingAppts;

	/** max parallel appointments 0 means no apts */
	@Column(name = "lanes")
	private int lanes;// 0, 1, 2 or 3 (max for now).

	public ActiviyDailyMain() {
	}
}