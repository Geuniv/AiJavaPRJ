package kopo.poly.service.impl;

import kopo.poly.dto.StudentDTO;
import kopo.poly.persistance.mapper.IStudentMapper;
import kopo.poly.service.IStudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
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

    @Override
    public List<StudentDTO> insertStudentList(List<StudentDTO> pList) throws Exception {

        log.info(this.getClass().getName() + ".insertStudentList Start !");

        pList.forEach(dto -> {
            try {
                this.insertStudent(dto);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Iterator<StudentDTO> it = pList.iterator();
        List<StudentDTO> rList = new ArrayList<>();

        while (it.hasNext()) {

            Optional<StudentDTO> res = Optional.ofNullable(
                    studentMapper.getStudent(it.next()) /* SELECT 쿼리 */
            );

            /* INSERT 쿼리 */
            if (!res.isPresent()) { // DB 조회 결과로 회원아이디가 존재하지 않는다면...
                studentMapper.insertStudent(it.next()); // 학생 등록 SQL 실행하기, ( INSERT 쿼리 )
            } else {
                 log.info("조회 가능");
            }
            rList = Optional.ofNullable(studentMapper.getStudentList()).orElseGet(ArrayList::new);
        }
        /* SELECT 쿼리 */
        /* Student 테이블에 등록된 학생 아이디가 존재하는지 체크하기 위해 DB 조회하기 ( Optional ) */

        log.info(this.getClass().getName() + ".insertStudentList End !");

        return rList;
    }

    @Override
    public List<StudentDTO> updateStudent(StudentDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".updateStudent Start !");

        /* SELECT 쿼리 */
        /* Student 테이블에 등록된 학생 아이디가 존재하는지 체크하기 위해 DB 조회하기 ( Optional ) */
        Optional<StudentDTO> res = Optional.ofNullable(
                studentMapper.getStudent(pDTO) /* SELECT 쿼리 */
        );

        /* INSERT 쿼리 */
        if (res.isPresent()) { // DB 조회 결과로 회원아이디가 존재하지 않는다면...
            studentMapper.updateStudent(pDTO);
            log.info(pDTO.getUserId() + "님이 수정되었습니다.");// 학생 등록 SQL 실행하기, ( INSERT 쿼리 )
        } else {
            log.info("회원이 존재하지 않아 수정되지 못했습니다.");

        }

        /* SELECT 쿼리 */
        /* 학생 테이블 전체 조회하기 ( Optional ) */
        List<StudentDTO> rList = Optional.ofNullable(
                studentMapper.getStudentList() /* SELECT 쿼리 */
        ).orElseGet(ArrayList::new); // null을 empty로 변환

        log.info(this.getClass().getName() + ".insertStudent End !");

        return rList;
    }

    /* DELETE 기능 ( 과제 ) */
    /**
     * 학생 삭제한 뒤, 결과 조회하기
     *
     * @param pDTO 삭제하기 위해 학생 아이디 정보를 가지고 있는 DTO
     * @return DB 조회한 학생 정보
     * */
    @Override
    public List<StudentDTO> deleteStudent(StudentDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".deleteStudent Start !");

        /* SELECT 쿼리 */
        /* Student 테이블에 등록된 학생 아이디가 존재하는지 체크하기 위해 DB 조회하기 */
        Optional<StudentDTO> res = Optional.ofNullable(
                studentMapper.getStudent(pDTO)
        );

        /* DELETE 쿼리 */
        if (res.isPresent()) { // DB 조회 결과로 회원아이디가 존재한다면..
            studentMapper.deleteStudent(pDTO); // 학생 삭제 SQL 실행하기
            log.info(pDTO.getUserId() + "님이 삭제되었습니다.");
        } else {
            log.info("회원이 존재하지 않아 삭제되지 못했습니다.");

        }

        /* SELECT 쿼리 */
        List<StudentDTO> rList = Optional.ofNullable(
                studentMapper.getStudentList()
        ).orElseGet(ArrayList::new);

        log.info(this.getClass().getName() + ".deleteStudent End !");
        return rList;
    }
}
