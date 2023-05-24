package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;

/* lombok을 이용해서 Get, Set 함수를 자바가 컴파일해서 바이트코드로 변환할때 알아서 함수를 추가해줌 */
@Getter
@Setter
public class OcrDTO {

    private String filepath; /* 저장된 이미지 파일의 파일 저장 경로 */

    private String filename; /* 저장된 이미지 파일 이름 */

    private String result; /* 저장된 이미지로부터 읽은 글씨 */
}
