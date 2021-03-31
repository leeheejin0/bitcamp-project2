package com.eomcs.pms.dao;

import java.util.List;
import com.eomcs.pms.domain.Board;

// 한 번에 4번째 단계까지 가지말고 일단 3번째와 4번째 단계 사이에 있는 정도로 구현을 해보자.
// - 각 DAO 클래스는 Connection 객체를 공유하기 위해 인스턴스 필드로 선언한다.
// - 각 DAO 클래스는 DAO 인스턴스가 생성될 때 Connection 객체를 만든다.
public interface BoardDao {

  int insert(Board board) throws Exception;

  List<Board> findAll() throws Exception;

  Board findByNo(int no) throws Exception;

  int update(Board board) throws Exception;

  int updateViewCount(int no) throws Exception;

  int delete(int no) throws Exception;

  List<Board> findByKeyword(String keyword) throws Exception;
}












