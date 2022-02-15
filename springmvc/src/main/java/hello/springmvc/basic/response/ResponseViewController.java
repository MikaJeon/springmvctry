package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class ResponseViewController {
    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "hello!");
        return mav;//모델 앤 뷰 객체(리스폰스 밑의 헬로우라는 이름으로 있는)를 만들고, 데이타라는 변수안에 헬로우를 담아 추가. 그리고 리턴@
    }
    @RequestMapping("/response-view-v2")//리스폰스바디 쓰면 안된다! 뷰로 연결 안되고 문자로 찍혀버림
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello!!");//스트링으로 반환해서 뷰를 찾게 할거니까 model을 파라미터로 넣어줘야 함.
        //모델이라는 객체에 데이터 라는 변수를 담아야 하니까!
        return "response/hello";
    }
    @RequestMapping("/response/hello")//경로의 이름과 뷰 이름이 같은 경우 리턴값이 생략 가능한 버전.. 추천하지 않음!
    //@컨트롤러를 사용하고 http바디를 건드리는 파라미터가(httpresponse같은) 없으면 가능. 하지만 명시성이 없으니..비추천
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!!");
    }
}