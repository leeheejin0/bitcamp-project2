package com.eomcs.pms.web;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.eomcs.mybatis.MybatisDaoFactory;
import com.eomcs.mybatis.SqlSessionFactoryProxy;
import com.eomcs.mybatis.TransactionManager;
import com.eomcs.pms.dao.BoardDao;
import com.eomcs.pms.dao.MemberDao;
import com.eomcs.pms.dao.ProjectDao;
import com.eomcs.pms.dao.TaskDao;
import com.eomcs.pms.service.BoardService;
import com.eomcs.pms.service.MemberService;
import com.eomcs.pms.service.ProjectService;
import com.eomcs.pms.service.TaskService;
import com.eomcs.pms.service.impl.DefaultBoardService;
import com.eomcs.pms.service.impl.DefaultMemberService;
import com.eomcs.pms.service.impl.DefaultProjectService;
import com.eomcs.pms.service.impl.DefaultTaskService;

//@WebServlet(
//    value="/init",   // 클라인언트에서 요청할 때 사용할 명령이다.
//    loadOnStartup = 1 // 톰캣 서버를 실행할 때 이 객체를 생성하라고 지정한다.
//    )
// loadOnStartup 이 지정되지 않은 경우, 
// 클라이언트가 실행을 요청할 때 서블릿 객체를 생성한다.
// 물론 한 번 객체를 생성하면 그 생성된 객체를 계속 사용한다.
// 즉 두 개의 객체를 생성하진 않는다.
@SuppressWarnings("serial")
public class AppInitHandler extends HttpServlet {

  @Override
  public void init() throws ServletException {
    // 서블릿 객체를 생성할 때 톰캣 서버가 메서드를 호출하는 과정
    // => 톰캣 서버 => 서블릿 인스턴스 생성 => 생성자 호출 => init(ServletConfig) 호출 => init() 호출

    try {
      // 1) Mybatis 관련 객체 준비
      InputStream mybatisConfigStream = Resources.getResourceAsStream(
          this.getInitParameter("mybatis-config"));
      SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(mybatisConfigStream);
      SqlSessionFactoryProxy sqlSessionFactoryProxy = new SqlSessionFactoryProxy(sqlSessionFactory);

      // 2) DAO 관련 객체 준비
      MybatisDaoFactory daoFactory = new MybatisDaoFactory(sqlSessionFactoryProxy);
      BoardDao boardDao = daoFactory.createDao(BoardDao.class);
      MemberDao memberDao = daoFactory.createDao(MemberDao.class);
      ProjectDao projectDao = daoFactory.createDao(ProjectDao.class);
      TaskDao taskDao = daoFactory.createDao(TaskDao.class);

      // 3) 서비스 관련 객체 준비
      TransactionManager txManager = new TransactionManager(sqlSessionFactoryProxy);

      BoardService boardService = new DefaultBoardService(boardDao);
      MemberService memberService = new DefaultMemberService(memberDao);
      ProjectService projectService = new DefaultProjectService(txManager, projectDao, taskDao);
      TaskService taskService = new DefaultTaskService(taskDao);

      // 4) 서비스 객체를 ServletContext 보관소에 저장한다.
      ServletContext servletContext = this.getServletContext();

      servletContext.setAttribute("boardService", boardService);
      servletContext.setAttribute("memberService", memberService);
      servletContext.setAttribute("projectService", projectService);
      servletContext.setAttribute("taskService", taskService);

      System.out.println("의존 객체를 모두 준비하였습니다.");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void destroy() {
  }

  @Override
  public ServletConfig getServletConfig() {
    return null;
  }

  @Override
  public String getServletInfo() {
    return null;
  }

  @Override
  public void service(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
  }

}
