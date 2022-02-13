package hello.springmvc.basic.request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Slf4j
@RestController
public class RequestHeaderController {
//http헤더 조회 방법!
    @RequestMapping("/headers")
    public String headers(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpMethod httpMethod,//get put 같은거
                          Locale locale,//언어정보 중 가장 우선순위 높은게 지정 됨
                          @RequestHeader MultiValueMap<String, String> headerMap,//키도스트링 값도스트링
                          @RequestHeader("host") String host,//헤더에 네임을 매핑
                          @CookieValue(value = "myCookie", required = false) String cookie//쿠키값이 없어도 된다는 뜻
    ) {
        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", host);
        log.info("myCookie={}", cookie);
        return "ok";
    }
}//멀티 벨류 맵은 맵과 유사한데 하나의 키에 여러 값을 받을 수 있음. 같은 헤더에 여러 값 들어갈 경우 등. 하나의 키를 꺼내면 배열로 여러 값이 나옴
