package com.myweb.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.myweb.www.domain.CommentVO;
import com.myweb.www.domain.PagingVO;
import com.myweb.www.handler.PagingHandler;

public interface CommentDAO {

	int insert(CommentVO cvo);

	List<CommentVO> getList(@Param("bno")long bno,@Param("pgvo")PagingVO pgvo);

	int selectOneBnoTotalCount(long bno);

	int update(CommentVO cvo);

	int delete(long cno);

}
