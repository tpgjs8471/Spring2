package com.myweb.www.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardVO {
	
	private long bno;
	private String title;
	private String writer;
	private String content;
	private String regDate;
	private String modDate;
	private int readCount;
	private int cmtQty;
	private int hasFile;
	
}
