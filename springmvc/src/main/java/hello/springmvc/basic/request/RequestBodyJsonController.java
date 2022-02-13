package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
/**
 * {"username":"hello", "age":20}
 * content-type: application/json
 */
@Slf4j
@Controller
public class RequestBodyJsonController {
    private ObjectMapper objectMapper = new ObjectMapper();//제이슨을 받는 객체
    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request,
                                  HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();//리퀘스트값 읽기
        String messageBody = StreamUtils.copyToString(inputStream,
                StandardCharsets.UTF_8);//인코딩
        log.info("messageBody={}", messageBody);
        HelloData data = objectMapper.readValue(messageBody, HelloData.class);//제이슨 값 읽기
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        response.getWriter().write("ok");//응답
    }

    /**
     * @RequestBody
     * HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     *
     * @ResponseBody
     * - 모든 메서드에 @ResponseBody 적용
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     */
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws
            IOException {//바디로 바로 받기
        HelloData data = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "ok";
    }

    /**
     * @RequestBody 생략 불가능(@ModelAttribute 가 적용되어 버림. 요청 파라미터 처리 방식으로 전환됨)
     * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (contenttype:
    application/json) 리퀘스트 바디를 어노테이션으로 사용하면 제이슨이면 컨버터가 그냥 우리가 지정한 파라미터 타입의 문자나 객체로 변환해서 넣어줌
     *
     */
    @ResponseBody
    @PostMapping("/request-body-json-v3")//objectMapper안쓰는 버전
    public String requestBodyJsonV3(@RequestBody HelloData data) {//메세지 바디가 아니라 그냥 바로 해당 파라미터를 변수멤버로 가진 객체로 받아버림
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {//httpEntity로 http 통째로 받기
        HelloData data = httpEntity.getBody();//바디 꺼내기
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return "ok";
    }
    
    /**
     * @RequestBody 생략 불가능(@ModelAttribute 가 적용되어 버림)
     * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (contenttype:
    application/json)
     *
     * @ResponseBody 적용
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter 적용
    (Accept: application/json)
     */
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) {//객체로 받기!
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return data;//객체로 반환! http메세지 컨버터가 리스폰스 바디가 있으면 나갈때도 적용이 됨. 객체가 컨버터에의해 json으로 바뀌어 바디에 콱 들어감!
    }
    //@리퀘스트바디 요청 json요청->http메세지컨버터->객체 반환
    //@리스폰스바디 응답 객체->http메세지컨버터->json반환 응답
}