package kopo.poly.service;

import kopo.poly.dto.OcrDTO;

/* 인터페이스 파일은 파일 이름 앞에 'I'를 붙혀줌 */
public interface IOcrService {

    /* 인터페이스는 인터페이스를 구현하는 자바 객체들의 공통 상수 설정할 때도 활용됨 */
    /* 학습 모델 파일이 존재하는 폴더 */
    String modelFile = "C:/model/tessdata";

    /* 이미지 파일로부터 문자 읽어 오기 */
    OcrDTO getReadforImageText(OcrDTO pOTO) throws Exception;
}
