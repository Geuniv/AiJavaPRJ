package kopo.poly.service.impl;

import kopo.poly.dto.StudentDTO;
import kopo.poly.persistance.mapper.IStudentMapper;
import kopo.poly.service.IStudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentService implements IStudentService {

    /* 2단계에서 구현 Mapper 인터페이스를 불러오기 */
    /* 오라클 DB와 연결된 Mapper */
    private final IStudentMapper studentMapper;

    @Override
    public List<StudentDTO> insertStudent(StudentDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".insertStudent Start !");

        /* SELECT 쿼리 */
        /* Student 테이블에 등록된 학생 아이디가 존재하는지 체크하기 위해 DB 조회하기 ( Optional ) */
        Optional<StudentDTO> res = Optional.ofNullable(
                studentMapper.getStudent(pDTO) /* SELECT 쿼리 */
        );

        /* INSERT 쿼리 */
        if (!res.isPresent()) { // DB 조회 결과로 회원아이디가 존재하지 않는다면...
            studentMapper.insertStudent(pDTO); // 학생 등록 SQL 실행하기, ( INSERT 쿼리 )
        }

        /* SELECT 쿼리 */
        /* 학생 테이블 전체 조회하기 ( Optional ) */
        List<StudentDTO> rList = Optional.ofNullable(
                studentMapper.getStudentList() /* SELECT 쿼리 */
        ).orElseGet(ArrayList::new); // null을 empty로 변환

        log.info(this.getClass().getName() + ".insertStudent End !");

        return rList;
    }

    /* DELETE 기능 */
    @Override
    public List<StudentDTO> deleteStudent(StudentDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".deleteStudent Start !");

        /* SELECT 쿼리 */
        Optional<StudentDTO> res = Optional.ofNullable(
                studentMapper.getStudent(pDTO)
        );

        /* DELETE 쿼리 */
        if (res.isPresent()) {
            studentMapper.deleteStudent(pDTO);
        }

        /* SELECT 쿼리 */
        List<StudentDTO> rList = Optional.ofNullable(
                studentMapper.getStudentList()
        ).orElseGet(ArrayList::new);

        log.info(this.getClass().getName() + ".deleteStudent End !");
        return rList;
    }
}
