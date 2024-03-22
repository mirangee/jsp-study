package com.jsp.chap04.service;

import com.jsp.entity.Dancer;
import com.jsp.repository.DancerRepository;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DeleteService implements IDancerService{
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = request.getParameter("name");
        System.out.println("name = " + name);
        DancerRepository.delete(name);

        // 삭제가 완료된 후에 삭제가 적용된 댄서 목록을 list.jsp에 보여주자.
        List<Dancer> dancerList = DancerRepository.findAll();

        // request에 삭제 적용된 dancerList를 담는다.
        // list.jsp를 그대로 활용할 것이므로 dl이라는 이름을 똑같이 지정한다.
        request.setAttribute("dl", dancerList);


    }
}
