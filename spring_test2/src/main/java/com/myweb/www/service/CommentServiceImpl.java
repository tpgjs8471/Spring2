package com.myweb.www.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myweb.www.domain.CommentVO;
import com.myweb.www.domain.PagingVO;
import com.myweb.www.handler.PagingHandler;
import com.myweb.www.repository.CommentDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
	private final CommentDAO cdao;

	@Override
	public int post(CommentVO cvo) {
		log.info("post commentdao insert");
		return cdao.insert(cvo);
	}

	@Transactional
	// 중간에 값이 변하더라도 메소드에 락을 걸어(그 시점에만) 값이변하지 않게 함
	// 메서드가 시작될 때 트랜잭션이 시작되고, 메서드가 완료될 때 트랜잭션이 커밋 또는 롤백
	// 주로 서비스에서 사용함
	@Override
	public PagingHandler getList(long bno,PagingVO pgvo) {
		log.info("list commentdao insert");
		// commentList 로 setting
		// ph 객체안에 cmtList 구성 해야함
		// totalCount 구해오기
		int totalCount = cdao.selectOneBnoTotalCount(bno);
		List<CommentVO> list = cdao.getList(bno,pgvo);
		PagingHandler ph = new PagingHandler(pgvo,totalCount,list);
		return ph;
	}

	@Override
	public int modify(CommentVO cvo) {
		log.info("modify serviceImpl insert");
		return cdao.update(cvo);
	}

	@Override
	public int remove(long cno) {
		log.info("remove serviceImpl insert");
		return cdao.delete(cno);
	}
}