package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

//어떤 요청이 왔을 때 어떤 컨트롤러가 연결이 되어야 하는지 설정
public class MappingController {
    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/hello-basic")
    public String helloBasic() {
        log.info("helloBasic");
        return "ok";
    }//기본. URL 매핑! 대부분의 속성이 배열로 제공되므로 다중 설정 가능! {"/url-1","/url-2"}이런식으로 ()안에 넣으면 됨. or조건
    //사실 /url과 /url/은 다른 url이지만 스프링은 동일한 요청으로 매핑해줌.
    //메서드 지정 안하면 get등의 메서드와 무관하게 그냥 출력 됨.

    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        log.info("mappingGetV1");
        return "ok";
    }
    //특정 http 메서드만 요청 허용을 한다. get만 허용하는 코드

    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader(){
        log.info("mappingHeader");
        return  "ok";
        //헤더에 mode=디버그 이 값을 넣고 호출하면 저 헤더 조건에 맞는 경우 호출이 됨. 호출 조건을 추가한거!
    }

    @GetMapping(value = "/mapping-param", headers = "mode=debug")
    public String mappingParam(){
        log.info("mappingParam");
        return  "ok";
        //호출 조건을 추가한거! /mapping-param?mode=debug 의 url에만 호출됨
    }

    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mapping-get-v2");
        return "ok";
    }
    //매핑을 축약한 버전. get post put delete patch 다 앞에 똑같이 붙이면 됨. 스프링에서 리퀘스트매핑(get메서드) 이런식으로 설정을 해뒀음

    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data) {
        log.info("mappingPath userId={}", data);
        return "ok";
    }
    //{}로 템플릿 화!! PathVariable를 이용했다.
    //mapping/userid 이런 식으로 오는거.
    //@PathVariable("userId") String data 이렇게 userId 받은 걸 data 변수로 꺼내서 사용 가능.
    //변수명 동일하게 할거면 @PathVariable String userId 일케만 해도 됨
    //내가 아는 ?user=a는 쿼리파라미터 방식임

    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long
            orderId) {
        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return "ok";
    }
    // 다중 사용도 가능. 파라미터로 받은 userId를 바로 userId에 넣는 방식

    @PostMapping(value = "/mapping-consume", consumes = "application/json")
    public String mappingConsumes(){
        log.info("mappingConsumes");
        return "ok";
    }
    //이 경우 그냥 호출 시 404, 바디에 json을 넣으면 content타입이 json으로 바뀜. 샌드하면 잘뜸!
    //consume이 content type을 의미함.
    //특정 파라미터 조건 매핑


    @PostMapping(value = "/mapping-produce", produces = "text/html")
    public String mappingProduce(){
        log.info("mappingProduce");
        return "ok";
    }
    //produces의 경우에는 accept header가 필요함.(무엇만 받을 수 있다 라는 뜻.)
    //produces는 우리가 직접 만든 것을 의미. 즉 우리는 text/html을 만들고, accept 헤더가 text/html이어야 함.



//MeiaType.APPLICATION_JSON_VALUE나 MeiaType.TEXT_HTML_VALUE 를 사용하는게 좋음


}
