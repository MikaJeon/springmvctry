package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {
    /**
     * 반환 타입이 없으면서 이렇게 응답에 값을 직접 집어넣으면, view 조회X
     */

    //요청 파라미터 조회하는 방법
    //HttpServletRequest가 제공하는 방식으로 요청 파라미터를 조회
   @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse
            response) throws IOException {
        String username = request.getParameter("username");//get이나 post에 파라미터로 값이 넘어오면 이걸로 꺼내기 가능
        int age = Integer.parseInt(request.getParameter("age"));//int가 파라미터로 오면 타입 변환 필요
        log.info("username={}, age={}", username, age);
        response.getWriter().write("ok");//response는 서버가 클라이언트에게 '응답'한다는 의미를 가진 객체.getWriter씀으로써 쓰기로 응답하겠다 표시
    }//반환 타입이 void면서 reponse로 응답하면 값 쓴게 브라우저에 바로 뜸

    /**
     * @RequestParam 사용
     * - 파라미터 이름으로 바인딩. 요청 파라미터를 편리하게 사용 가능
     * @ResponseBody 추가
     * - 리턴값을 View 조회하는 걸 무시하고, HTTP message body에 직접 해당 내용 입력 레스트컨트롤러랑 같은 역할
     */
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,//괄호 안 이름이 파라미터로 넘어온 변수. 뒤에가 담을 변수
            @RequestParam("age") int memberAge) {
        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    /**
     * @RequestParam 사용
     * HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name="xx") 생략 가능
     */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,//파람 괄호 안 생략 가능. 변수명이 동일할 경우!
            @RequestParam int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * @RequestParam 사용
     * String, int 등의 단순 타입이면 @RequestParam 도 생략 가능
     */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {//요청파라미터 이름과 맞고 스트링 인트 인티저 등 단순타입이면 @리퀘스트파람 이것도 생략 가능 
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * @RequestParam.required
     * /request-param -> username이 없으므로 예외
     *
     * 주의!
     * /request-param?username= -> 빈문자로 통과
     *
     * 주의!
     * /request-param
     * int age -> null을 int에 입력하는 것은 불가능, 따라서 Integer 변경해야 함(또는 다음에 나오는
    defaultValue 사용)
     */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(// required 이 값이 무조건 들어와야 해! 안들어와도 돼 설정
            @RequestParam(required = true) String username,//유저네임은 반드시 파라미터로 넘겨져야 함. 아니면 오류. 기본이 트루임. null과 ""는 다름. null은 값이 없지만 빈문자는 값이 있는 것
            @RequestParam(required = false) Integer age) {//에이지를 파라미터로 안넘겨도 괜찮. int에는 null이 들어가면 안됨. 인티저는 괜찮. 에이지 없이도 파라미터 받으려면 인트 말고 인티저로 설정!
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * @RequestParam
     * - defaultValue 사용
     *
     * 참고: defaultValue는 빈 문자의 경우에도 적용
     * /request-param?username=
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username,//안넘어오면 값 설정해줌.
            @RequestParam(required = false, defaultValue = "-1") int age) {//디폴트값을 넣어줬으니 int써도 됨. null이면 저 값이 들어갈테니!
        log.info("username={}, age={}", username, age);
        return "ok";//디폴트는 빈문자도 처리해줌
    }

    /**
     * @RequestParam Map, MultiValueMap
     * Map(key=value)
     * MultiValueMap(key=[value1, value2, ...] ex) (key=userIds, value=[id1, id2])
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {//파라미터를 맵으로 받아버리기!
        log.info("username={}, age={}", paramMap.get("username"),//파라미터 맵에서 get 메서드로 값 꺼내기
                paramMap.get("age"));
        return "ok";
    }//파라미터를 맵으로 조회하기
    //멀티벨류도 사용 가능. MultiValueMap(key=[val1, val2..] 아니면 (key=userid, value=[id1,id2...] 이런 식으로 받아오면 됨
    


    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {//모델 객체 생성, 요청 파라미터 명 프로퍼티를 찾아 get, set을 자동으로 해줌.
        //프로퍼티란? 객체에 getAge, setAge 메서드가 있으면 Age라는 프로퍼티가 있는 것. Age 프로퍼티 값을 변경하면 setAge가 호출, 조회하면 getAge가 호출
        //    @ResponseBody
        //    @RequestMapping("/model-attribute-v1")
        //    public String modelAttribureV1(@RequestParam String username, @RequestParam int age){
        //        HelloData helloData = new HelloData();
        //        helloData.setUsername(username);
        //        helloData.setAge(age);
        //
        //        return "ok";
        //    } 이 과정을 안하게 해준다
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {//파라미터 값 두개를 보낸걸 그 파라미터를 멤버로 가진 객체로 받아서 처리 가능
        log.info("username={}, age={}", helloData.getUsername(),
                helloData.getAge());
        return "ok";
    }//모델어트리뷰트 어노테이션 생략하고 진행해도 문제 없음.
    //스프링은 스프링 인트 인티져는 리퀘스트파람 어노테이션을 자동으로 연결하고, 나머지는 모델어트리뷰트로 처리함.

    @Slf4j
    @Controller//단순한 텍스트 메시지를 HTTP 메시지 바디에 담아서 전송. form빼고!
    public class RequestBodyStringController {
        @PostMapping("/request-body-string-v1")
        public void requestBodyString(HttpServletRequest request,
                                      HttpServletResponse response) throws IOException {
            ServletInputStream inputStream = request.getInputStream();//인픗스트림 사용해서 읽기
            String messageBody = StreamUtils.copyToString(inputStream,
                    StandardCharsets.UTF_8);//바디 내부로 받을 때는 인코딩 방식 설정해줘야 함
            log.info("messageBody={}", messageBody);
            response.getWriter().write("ok");
        }
    }

    /**
     * InputStream(Reader): HTTP 요청 메시지 바디의 내용을 직접 조회
     * OutputStream(Writer): HTTP 응답 메시지의 바디에 직접 결과 출력
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter)//리퀘스트랑 리스폰스가 통째로 필요한게 아니니까 인풋스트림이랑 롸이터로만 받음 
            throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream,
                StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        responseWriter.write("ok");
    }

    /**
     * HttpEntity: HTTP header, body 정보를 편리하게 조회
     * - 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X) 요청 파라미터랑(url이랑 form)은 관련 없음
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     *
     * 응답에서도 HttpEntity 사용 가능
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) {//문자로 인코딩 이런거 자동으로 해줌. http메세지컨버터를 동작시킴
        String messageBody = httpEntity.getBody();//바디만 받아옴
        log.info("messageBody={}", messageBody);
        return new HttpEntity<>("ok");//바디에 메세지 넣어서 리턴 가능. 마치 http메세지를 바로바로 주고 받는 느낌!
    }
    //HttpEntity를 상속받은 파라미터 RequestEntity + 리턴 ResponseEntity 세트 사용 가능. 이것들은 상태코드를 넣을 수 있음

    /**
     * @RequestBody
     * - 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     *
     * @ResponseBody
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     */
    @ResponseBody//리퀘스트바디의 응답 버전!
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) {//리퀘스트 바디 에노테이션. 바디를 그냥 받아옴!
        log.info("messageBody={}", messageBody);
        return "ok";//리스폰스바디가 잇으니 이 내용이 바디에 그냥 들어감. 이 버전이 가장 많이 사용됨
        //헤더정보 필요시 httpEntity나 리퀘스트헤더 사용하면 됨.
    }

}