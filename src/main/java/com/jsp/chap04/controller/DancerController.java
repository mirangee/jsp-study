package com.jsp.chap04.controller;

import com.jsp.chap04.service.DeleteService;
import com.jsp.chap04.service.IDancerService;
import com.jsp.chap04.service.ListService;
import com.jsp.chap04.service.RegistService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 서블릿 == 컨트롤러
// 클라이언트의 요청을 파악하고 모델에게 로직을 위임하며
// 응답할 view를 결정합니다.

@WebServlet("*.do") // 확장자 패턴 -> 앞이 무엇이든지 .do로 끝나는 요청이면 이 서블릿이 다 받겠다.
public class DancerController extends HttpServlet {
    private IDancerService sv; // 인터페이스 타입의 변수를 선언해 상황에 맞게 서비스 객체를 갈아 끼울 예정
    private RequestDispatcher rd; // request 운반꾼

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        System.out.println("requestURI = " + requestURI);

        try {
            switch (requestURI) {
                case "/register.do":
                    System.out.println("등록 폼으로 이동시켜 달라는 요청이군~");
    //                response.sendRedirect("/WEB-INF/chap03/dancer/register.jsp"); 이렇게 이동 못한다.
                    rd = request.getRequestDispatcher("/WEB-INF/chap03/dancer/register.jsp"); // request 운반꾼 , 괄호 안에 목적지를 작성한다.
                    rd.forward(request, response); // forward(): 내부에서 웹페이지 이동을 명령할 때 쓰는 메서드, request를 짊어지고 해당 페이지로 떠난다.
                    break;

                case "/regist.do":
                    System.out.println("댄서 등록 요청이 들어왔음");
                    sv = new RegistService();
                    sv.execute(request, response);

                    //execute가 실행되면서 서비스가 댄서 목록을 request에 담아놓은 상태입니다.
                    // dispatcher에게 목적지를 알려주면서 request와 response 객체를 가지고 이동하라는 명령을 내린다.
                    // 실제 페이지가 목적지로 이동되면서 request와 response객체도 함께 전달된다.
                    // -> jsp에서 request 객체를 꺼내서 목록을 화면에 뿌려서 응답하면 끝!
                    rd = request.getRequestDispatcher("/WEB-INF/chap03/dancer/list.jsp");
                    rd.forward(request, response);
                    break;


                case "/delete.do":
                    System.out.println("댄서 삭제 요청이 들어왔군");
                    sv = new DeleteService();
                    sv.execute(request, response);
                    System.out.println("repository에서 댄서가 삭제 완료 되었고 이제 뷰로 보여줄 차례");
                    rd = request.getRequestDispatcher("/WEB-INF/chap03/dancer/list.jsp");
                    rd.forward(request, response);
                    break;

                case "/list.do":
                    System.out.println("리스트를 보여달라는 요청이 들어왔군");
                    sv = new ListService();
                    sv.execute(request, response);
                    rd = request.getRequestDispatcher("/WEB-INF/chap03/dancer/list.jsp");
                    rd.forward(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
