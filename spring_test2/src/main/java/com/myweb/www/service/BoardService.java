package com.myweb.www.service;

import java.util.List;

import com.myweb.www.domain.BoardDTO;
import com.myweb.www.domain.BoardVO;
import com.myweb.www.domain.PagingVO;

public interface BoardService {

	int insert(BoardDTO bdto);

	List<BoardVO> getlist(PagingVO pgvo);

	BoardDTO getdetail(long bno);

	int update(BoardDTO bdto);

	int delete(long bno);

	int getTotalCount(PagingVO pgvo);

	int removeFile(String uuid);

}