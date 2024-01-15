package com.myweb.www.handler;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.myweb.www.domain.FileVO;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
@Component // : 사용자 클래스를 Bean 으로 등록
public class FileHandler {
	
	private final String UP_DIR = "D:\\_myProject\\_java\\_fileUpload"; // 파일 업로드 경로
	
	public List<FileVO> uploadFiles(MultipartFile[] files){
		List<FileVO> flist = new ArrayList<>();
		// FileVO 생성, 파일을 경로에 맞춰서 저장, 썸네일 저장
		// 날짜를 폴더로 생성하여 현재시간,날짜에 맞게 업로드 파일을 관리
		LocalDate date = LocalDate.now(); // 현재 시간
		String today = date.toString(); // 현재시간을 String로 저장
		today = today.replace("-",File.separator); // File.separator 는 운영체제마다 모양이 다르다,  \(wimdow) , /(mac)
		
		// D:\\_myProject\\_java\\_fileUpload\\2024\\01\\10
		File folders = new File(UP_DIR , today);
		
		// 폴더 생성 , exists : 폴더의 유무 확인
		if(!folders.exists()) { // 폴더가 없다면
			// mkdir : 폴더 1 개를 생성
			// mkdirs : 하위 폴더 다 생성
			folders.mkdirs();
		}
		
		// files 객체에 대한 설정
		for(MultipartFile file : files) {
			FileVO fvo = new FileVO();
			// UP_DIR 제외 하위 오늘날짜 경로만 set
			fvo.setSaveDir(today);
			// file.size 은 return 타입이 long 이다
			fvo.setFileSize(file.getSize());
			
			String originalFileName = file.getOriginalFilename(); 
			log.info(">>>>> originalFileName >>>>> {}"+originalFileName);
			// 경로가 포함되어있을 수도 있으므로 분리과정을 거쳐야함
			String fileName = originalFileName.substring(
					originalFileName.lastIndexOf(File.separator)+1);
			log.info(">>>>>>> fileName >>>>>>> {}"+fileName);
			fvo.setFileName(fileName);
			
			// uuid 생성
			UUID uuid = UUID.randomUUID(); // 중복방지
			String uuidstr = uuid.toString();
			fvo.setUuid(uuidstr);
			// 여기까지가 기본 fvo Setting 완료
			
			// 디스크에 저장할 파일 객체를 생성
			String fullFileName = uuidstr+"_"+fileName; // uuid_fileName
			File storeFile = new File(folders, fullFileName);
			// 실제 파일이 저장되려면 첫 경로부터 다 설정이 되어있어야함
			// D:\\_myProject\\_java\\_fileUpload\\2024\\01\\10
			
			try {
				file.transferTo(storeFile); // 저장
				// 썸네일 생성 => 이미지 파일만 썸네일 생성 가능
				// 이미지 확인
				if(isImageFile(storeFile)) {
					fvo.setFileType(1); // 이미지 파일은 타입이 1
					// 쌈네일 생성
					File thumbNail = new File(folders, uuidstr+"_th_"+fileName);
					Thumbnails.of(storeFile).size(75, 75).toFile(thumbNail);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.info("파일 생성 오류");
			}
			// list에 fvo를 추가
			flist.add(fvo);
		}
		
		
		return flist;
	}
	
	
	// 이미지 체크 메서드 생성
	// Tika : 다양한 형식의 파일에서 텍스트 및 메타데이터를 추출하고 파일의 MIME 유형을 식별하기 위한 도구
	private boolean isImageFile(File storFile) throws IOException{
		String mimeType = new Tika().detect(storFile); // type/ image / jpg
		return mimeType.startsWith("image")? true : false; // startsWith("") : ("") 로 시작하는지 확인
	}
	
	
	
	
	
}
