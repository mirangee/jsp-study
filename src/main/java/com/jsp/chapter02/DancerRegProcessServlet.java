package com.jsp.chapter02;

import com.jsp.entity.Dancer;
import com.jsp.repository.DancerRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;

@WebServlet("/chapter02/dancer/reg-process")
public class DancerRegProcessServlet extends HttpServlet {
    // 사용자의 입력값 가져오기
    // 가져온 입력값을 토대로 댄서 객체 생성 -> repository의 save()가 대신 해줄 것임.
    // 정보를 데이터베이스에 저장 -> repository의 save()가 대신 해줄거임.
    // 결과를 화면으로 응답.


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // GET 요청이 전달되면 자동으로 호출되는 메서드.
        // GET 요청에만 호출됩니다. -> POST 요청에는 반응하지 않는다.
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // POST 요청이 전달되면 자동으로 호출되는 메서드.
        // POST 요청에만 호출됩니다. -> GET 요청에는 반응하지 않는다.


        //POST 방식에서 전달된 한글 데이터의 깨짐 방지 메서드
        request.setCharacterEncoding("UTF-8");

        // 제공된 form에 작성된 사용자의 입력값 가져오기
        String name = request.getParameter("name");
        String crewName = request.getParameter("crewName");
        String danceLevel = request.getParameter("danceLevel");
        //checkbox같이 여러 값을 전달받는 경우에는 getParameterValues를 사용한다.
        //-> String 배열로 리턴된다.
        String[] genresArray = request.getParameterValues("genres");

        /*
        System.out.println("name = " + name);
        System.out.println("crewName = " + crewName);
        System.out.println("danceLevel = " + danceLevel);
        System.out.println("genresArray = " + Arrays.toString(genresArray));
        */

        // 가져온 입력값을 토대로 댄서 객체 생성 -> DancerRepository 객체를 참조해 save() 메서드 호출
        DancerRepository.save(name, crewName, danceLevel, genresArray);

        // 화면에 결과 보여주기
        // 결과 출력
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter w = response.getWriter();

        w.write("<!DOCTYPE html>\n");
        w.write("<html>\n");
        w.write("<head>\n");
        w.write("</head>\n");
        w.write("<body>\n");

        w.write("<ul>");

        for (Dancer dancer : DancerRepository.findAll()) {
            w.write(String.format("<li># 이름 : %s, 크루명: %s, 수준: %s, 공연당페이: %d원, 장르: %s</li>\n"
                    , dancer.getName(), dancer.getCrewName(),
                    dancer.getDanceLevel().getLevelDescription(),
                    dancer.getDanceLevel().getPayPerEvent(),
                    dancer.getGenres()));
        }

        w.write("</ul>");

        w.write("<a href=\"/chapter02/dancer/register\">새로운 등록하기</a>");

        w.write("</body>\n");
        w.write("</html>");

        w.flush();
        w.close();
    }

}
