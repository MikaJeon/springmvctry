package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@Controller
//@RestController 컨트롤러와 리스폰스바디 어노테이션이 합쳐진 버전!
//html이나 뷰 템플릿 써도 http바디에 데이터가 담김.
//여기서는 정적리소스(html)이나 뷰 템플릿 없이 직접 http응답 메세지를 꽂는 방식
public class ResponseBodyController {
    @GetMapping("/response-body-string-v1")//가장 간단한 방식. 전달 받은 내용 중 리스폰스를 파라미터로 받아 내용 적기
    public void responseBodyV1(HttpServletResponse response) throws IOException
    {
        response.getWriter().write("ok");
    }
    /**
     * HttpEntity, ResponseEntity(Http Status 추가)
     * @return
     */
    @GetMapping("/response-body-string-v2")//두번째 간단한 방식. 리스폰스엔티티 안에 값 넣어서 리턴해줌
    public ResponseEntity<String> responseBodyV2() {
        return new ResponseEntity<>("ok", HttpStatus.OK);//이 엔티티는 http메세지의 헤더와 바디정보를 가짐.
    }
    @ResponseBody//이거를 붙여주고 그냥 스트링으로 ok찍기. 메세지 정보를 직접 반환해주는 어노테이션
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        return "ok";
    }
    @GetMapping("/response-body-json-v1")//제이슨 타입으로 응답! 객체를 만들어서 내용 담고~~리턴...
    public ResponseEntity<HelloData> responseBodyJsonV1() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return new ResponseEntity<>(helloData, HttpStatus.OK);
    }
    @ResponseStatus(HttpStatus.OK)//상태값을 바꿀 수 있는 어노테이션. 객체를 바로 반환하는 경우 상태 지정을 못해주기 때문.
    //그래도 상황에 따라 동적으로 상태가 바뀌어야 하면 이거 쓰지말고 엔티티 쓰자
    @ResponseBody//이거 쓰면 상태 지정을 못해주고 바로 바디에 내용을 꽂아버리니까 위의 에너테이션 적용
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() {//리스폰스 엔티티 안쓰고 바로 리턴해버림. 
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return helloData;
    }
}