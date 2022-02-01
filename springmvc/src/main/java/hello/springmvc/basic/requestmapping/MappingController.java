package main.java.hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


public class MappingController {
    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/hello-basic")
    public String helloBasic() {
        log.info("helloBasic");
        return "ok";
    }

    @GetMapping(value = "/maaping-header", headers = "mode=debug")
    public String mappingHeader(){
        log.info("mappingHeader");
        return  "ok";
        //헤더에 mode는 디버그 이 값을 넣고 호출하면 저 헤더 조건에 맞는 경우 호출이 됨.
    }

    @PostMapping










}
