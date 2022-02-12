package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//@Slf4j 이거 쓰면 15번째줄에서 참조 선언 안해도 됨
@RestController// @controller는 리턴값이 스트링이면 뷰 이름으로 인식함.
// 스트링을 리턴하고 싶으면 이걸 사용해야 함 restapi할때 그 rest. http 바디에 리턴값을 넣어 반환해준다.

//시스아웃같은걸로 정보를 출력하지 않고 별도의 로깅 라이브러리를 사용해 로그를 출력함.
//스프링 부트에서는 로깅 라이브러리가 있다. 프로젝트가 인터페이스 화 되어 들어있음. 기본으로 slf4j(인터페이스) logback(구현체)
public class LogTestController {
    private final Logger log = LoggerFactory.getLogger(getClass());//꼭 slf4j. 그리고 현재 내 클래스 지정(getclass)
    //시스아웃같은걸로 정보를 출력하지 않고 별도의 로깅 라이브러리를 사용해 로그를 출력함.
    // 부가 정보도 같이 볼 수 있고 상황에 맞춰서 출력할지 말지 조절이 가능하기 때문. 시스아웃보다 성능도 좋음
    //로거는 파일로 남길수도 있다! 네트워크로 로거를 전송할 수도 있다! 로거파일의 자동 분할, 백업 이런것도 지원해준다.
    //스프링 부트에서는 로깅 라이브러리가 있다. 프로젝트가 인터페이스 화 되어 들어있음. 기본으로 slf4j(인터페이스) logback(구현체)
    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";
        //{}는 치환됨. 여러개 쓸거면 "={},{}", 변수, 변수 이런 식으로 씀.
        //로그의 레벨을 정해서 쓸 수 있음. 밑으로 갈 수록 레벨 증가.
        //시스아웃 쓰면 운영서버에도 로그가 다 남아서 문제가 된다.
        log.trace("trace log={}", name);
        log.debug("debug log={}", name);//개발 시 , 디버그 할때 보는 로그
        log.info(" info log={}", name);//운영 시스템에서도 봐야 할 중요한 정보
        log.warn(" warn log={}", name);//경고
        log.error("error log={}", name);//에러
        //실행하면 트레이스랑 디버그는 안뜸. 모든 로그를 보고 싶으면 에플리케이션 프로퍼티스 가서 로그 레벨 설정 가능
        //logging.level.hello.springmvc = trace 이런 식으로 해주면 트레이스부터 로그가 뜸.
        // 디버그 하면 트레이스 빼고 디버그 부터 됨. 아무것도 안 적으면 기본이 인포
        //트레이스 레벨로 해놓고 개발 후에 배포할때 이 설정 바꿔서 배포하면 됨!
        log.debug("String concat log=" + name);
        return "ok";
    }
}

/* 로그의 올바른 사용 법

log.info("info log=" + name) 이렇게 사용하면 안된다.
자바 언어의 특징 때문!

자바 언어는 log.trace 메서드 호출 전에 +를 먼저 연산 함.
즉 log.trace(" trace log = Spring ")이 되어버림.
연산이 일어나 버리는게 핵심. 메모리와 cpu를 사용함. 만일 로그 레벨이 info레벨이면 trace로그는 출력 안할건데도 메모리 낭비가 발생

log.trace("trace log={}", name);의 경우 그냥 trace 메서드 실행하면서 name을 파라미터로 넘김.
트레이스는 출력 안하니까 그대로 중지.
 */