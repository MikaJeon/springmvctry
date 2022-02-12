package hello.springmvc.basic.requestmapping;

import org.springframework.web.bind.annotation.*;

//회원관리 기능을 http api로 만들 경우(매핑만!)

@RestController
@RequestMapping("/mapping/users") //전체 매핑 url을 여기서 컨트롤!
public class MappingClassController {

    /*
    url 설계!
    회원 목록 조회: GET /mapping/users
    회원 등록: POST /mapping/users
    회원 조회: GET /mapping/users/id1
    회원 수정: PATCH /mapping/users/id1
    회원 삭제: DELETE /mapping/users/id1*/

        /**
         * GET /mapping/users
         */
        @GetMapping
        public String users() {
            return "get users";
        }
        /**
         * POST /mapping/users
         */
        @PostMapping
        public String addUser() {
            return "post user";
        }
        /**
         * GET /mapping/users/{userId}
         */
        @GetMapping("/{userId}")
        public String findUser(@PathVariable String userId) {
            return "get userId=" + userId;
        }
        /**
         * PATCH /mapping/users/{userId}
         */
        @PatchMapping("/{userId}")
        public String updateUser(@PathVariable String userId) {

            return "update userId=" + userId;
        }
        /**
         * DELETE /mapping/users/{userId}
         */
        @DeleteMapping("/{userId}")
        public String deleteUser(@PathVariable String userId) {

            return "delete userId=" + userId;
        }
}
