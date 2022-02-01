package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


public class MappingController {
    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/hello-basic")
    public String helloBasic() {
        log.info("helloBasic");
        return "ok";
    }//기본. URL 매핑!

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
        //헤더에 mode는 디버그 이 값을 넣고 호출하면 저 헤더 조건에 맞는 경우 호출이 됨.
    }

    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mapping-get-v2");
        return "ok";
    }
    //매핑을 축약한 버전.

    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data) {
        log.info("mappingPath userId={}", data);
        return "ok";
    }
    //경로 변수 사용! PathVariable를 이용했다.
    //@PathVariable 는 매핑 어노테이션 url 정의 부분과 메소드 내의 파라미터부분에 정의해서 사용할 수 있다.
    //메핑 어노테이션에는 {}의 템플릿 변수를, 파라미터 부분엔 @PathVariable("템플릿변수와동일")이런 식으로
    //추가하면 된다. 말 그대로 url 경로에 변수를 넣어주는 역할을 함

    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long
            orderId) {
        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return "ok";
    }
    // 다중 사용도 가능

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
