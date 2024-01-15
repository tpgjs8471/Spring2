package com.myweb.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.myweb.www.domain.BoardDTO;
import com.myweb.www.domain.BoardVO;
import com.myweb.www.domain.PagingVO;

public interface BoardDAO {

	List<BoardVO> getlist(PagingVO pgvo);

	BoardVO getdetail(long bno);

	int update(BoardVO bvo);

	int delete(long bno);

	int updateReadCount(long bno);

	int getTotalCount(PagingVO pgvo);

	long selectOneBno();

	int insert(BoardVO bvo);

	int updateCommentCount();

	void fileCount();
	
//	void readCount(@Param("bno")long bno);

}
