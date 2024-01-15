package com.myweb.www;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.myweb.www.domain.BoardVO;
import com.myweb.www.repository.BoardDAO;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {com.myweb.www.config.RootConfig.class})
// 해당하는 클래스를 가져와서 사용할 수 있게~
public class BoardTest {
	
	@Inject
	private BoardDAO bdao;
	
//	실행 시 class 안에서 run as 를 해야함 !!
//	=> 해당하는 Test case 만 실행해야 하기 때문에
//	=> 여러 method가 존재시 실행할려는 method만 실행하면 됨
	@Test
	public void insertBoard() {
		for(int i=0; i<300 ; i++) {
			BoardVO bvo = new BoardVO();
			bvo.setTitle("Test Title"+i);
			bvo.setWriter("TESTER");
			bvo.setContent("Test Content");
			bdao.insert(bvo);
		}
	}
}