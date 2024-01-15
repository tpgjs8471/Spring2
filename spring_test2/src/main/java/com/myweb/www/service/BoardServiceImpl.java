package com.myweb.www.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myweb.www.domain.BoardDTO;
import com.myweb.www.domain.BoardVO;
import com.myweb.www.domain.FileVO;
import com.myweb.www.domain.PagingVO;
import com.myweb.www.repository.BoardDAO;
import com.myweb.www.repository.FileDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	private final BoardDAO bdao;
	private final FileDAO fdao;

	@Transactional
	@Override
	public int insert(BoardDTO bdto) {
		log.info(">>>>>>>>>>>> insert check");
		// bvo boardMapper / flist fileMapper 각자 등록
		int isOk = bdao.insert(bdto.getBvo());
		
		if(bdto.getFlist() == null) {
			return isOk;			
		}
		// bvo insert 후 파일이 존재한다면~
		if(isOk > 0 && bdto.getFlist().size()>0) {
			// bno setting
			long bno = bdao.selectOneBno(); // 가장 마지막에 등록된 bno
			for(FileVO fvo : bdto.getFlist()) {
				fvo.setBno(bno);
				isOk *= fdao.insertFile(fvo);
			}
		}
		return isOk;
	}

	@Override
	public List<BoardVO> getlist(PagingVO pgvo) {
		log.info(">>>>>>>>>>>> getlist check");
		int isOk = bdao.updateCommentCount();
		bdao.fileCount();
		return bdao.getlist(pgvo);
	}

	@Transactional
	@Override
	public BoardDTO getdetail(long bno) {
		log.info(">>>>>>>>>>>> getdetail check");
		int isOk = bdao.updateReadCount(bno);
		BoardVO bvo = bdao.getdetail(bno);
		List<FileVO> flist = fdao.getFileList(bno);
		BoardDTO bdto = new BoardDTO(bvo,flist);
		return bdto;
	}

	@Transactional
	@Override
	public int update(BoardDTO bdto) {
		log.info(">>>>>>>>>>> update check");
		int isOk = bdao.update(bdto.getBvo()); // 이건 보드내용만 수정하는 거고
		
		if(bdto.getFlist() == null) {
			isOk *= 1; // 파일이 없을 경우 isOk 1 을 줘버려서 Pass
		} else if(isOk > 0 && bdto.getFlist().size()>0) {
			long bno = bdto.getBvo().getBno();
			for(FileVO fvo : bdto.getFlist()) {
				fvo.setBno(bno);
				isOk *= fdao.insertFile(fvo);
			}
		}
		
		return isOk;
	}

	@Override
	public int delete(long bno) {
		log.info(">>>>>>>>>>> DELETE check");
		return bdao.delete(bno);
	}

	@Override
	public int getTotalCount(PagingVO pgvo) {
		log.info(">>>>>>>>>>> getTotalCount check");
		return bdao.getTotalCount(pgvo);
	}

	@Override
	public int removeFile(String uuid) {
		log.info(">>>>>>>>>>> removeFile check");
		return fdao.removeFile(uuid);
	}

}
