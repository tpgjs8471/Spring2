package com.myweb.www.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingVO {
	
	private int pageNo; // 현재 페이지 번호
	private int qty; // 한페이지당 표시될 리스트 개수
	
	// 검색을 위해 type 과 keyword 추가
	private String type;
	private String keyword;
	
	public PagingVO() {
		this.pageNo = 1;
		this.qty = 10;
		// => 1 pageNo 에 10개 qty
	}
	
	public PagingVO(int pageNo, int qty) {
		this.pageNo = pageNo;
		this.qty = qty;
	}
	
	// 시작번지 구하기
	public int getPageStart() {
		return (this.pageNo - 1)*qty;
	}
	
	// type의 값을 배열로 생성
	// 복합타입의 키워드일 경우 각자 검색해야하기 때문에 배열로 생성
	// this.type.split("") 한 글자씩 분리해서 인식
	public String[] getTypeToArray() {
		return this.type == null ? new String[] {} : this.type.split("");
	}
}
