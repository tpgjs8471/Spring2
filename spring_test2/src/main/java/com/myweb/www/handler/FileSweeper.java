package com.myweb.www.handler;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.myweb.www.domain.FileVO;
import com.myweb.www.repository.FileDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@EnableScheduling
@Component
public class FileSweeper {
	
	private final String BASE_PATH = "D:\\_myProject\\_java\\_fileUpload\\";
	
	private final FileDAO fdao; // DB 조회하기 위함
	
	// 초 분 시 일 월 요일 년도(년도는 생략가능)
	@Scheduled(cron="0 41 10 * * *")
	public void fileSweeper() {
		log.info(">>>>> FileSweeper Running Start~!! {}",LocalDateTime.now());
		
		// DB에 등록된 파일 목록 가져오기
		List<FileVO> dbList = fdao.selectListAllFile();
		
		//저장소를 검색할 때 필요한 파일의 경로 리스트(살제 존재해야 할) 리스트
		List<String> currFiles = new ArrayList<String>();
		
		for(FileVO fvo : dbList) {
			String filePath = fvo.getSaveDir()+File.separator+fvo.getUuid();
			String fileName = fvo.getFileName();
			currFiles.add(BASE_PATH+filePath+"_"+fileName);
			
			// 이미지라면 섬네일도 경로 추가
			if(fvo.getFileType() > 0) {	
				currFiles.add(BASE_PATH+filePath+"_th_"+fileName);
			}
		}
		
		log.info(">>>>>> currFiles >>>>"+currFiles);
		// 오늘 날짜를 반영한 폴더 구조 경로 만들기
		LocalDate now = LocalDate.now();
		String today = now.toString();
		today = today.replace("-", File.separator);
		
		// 경로를 기반으로 저장되어있는 파일을 검색 
		//( 오늘 날짜 폴더 안에있는 전체 파일 )
		File dir = Paths.get(BASE_PATH+today).toFile();
		File[] allFileObjects = dir.listFiles();
		
		// 실제 저장되어있는 파일과 DB안의 파일을 비교하여 없는 파일을 삭제
		// DB는 살고 폴더안의 파일이 삭제
		for(File file : allFileObjects) {
			String storedFileName = file.toPath().toString();
			if(!currFiles.contains(storedFileName)) {
				file.delete(); // 파일 삭제
				log.info(">>>>>>>>> delete file >>> {}", storedFileName);
			}
		}
		
		log.info(">>>>> FileSweeper Running Finish~!! {}",LocalDateTime.now());
	}
	
}
