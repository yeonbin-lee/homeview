package com.example.demo1.controller;

import com.example.demo1.dto.PostingDTO;
import com.example.demo1.dto.PostingUpdateDTO;
import com.example.demo1.entity.Member;
import com.example.demo1.entity.Posting;
import com.example.demo1.repository.PostingRepository;
import com.example.demo1.service.MemberService;
import com.example.demo1.service.PostingService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/api/posting")
public class PostingController { // 스테이터스로만 보내는걸로. 문자든 숫자든
    private PostingService postingService;

    public PostingController(PostingService postingService) {
        this.postingService = postingService;
    }

    // 여기서 하니까 오류나서 PostingService로 옮김...
    /*
    public PostingController(HttpSession session) throws Exception {
        member = getMember(session);
    }
    */

    /*private Member getMember(HttpSession session) {

        //세션 객체 안에 있는 email정보 저장
        String email = (String) session.getAttribute("email");
        //log.info("회원정보 [session GET] email:" + email);

        // email id로 찾은 member 객체 리턴
        Member info = memberService.getInfo(email);
        return info;
    }*/

    // 포스팅 리스트
    //@GetMapping // /api/posting?page=0&size=3&sort=id,desc&sort=username,desc  -> 요청은 이런 식으로. 여기서는 page만 따로 ? 뒤에 붙여주면 될듯. page는 0부터 시작
    public ResponseEntity<Posting> index(Model model, @PageableDefault(size = 5, sort = "postId", direction = Sort.Direction.DESC) Pageable pageable) {

        Model postings = model.addAttribute("postings", postingService.page(pageable));// model attribute가 아니라 json 형식으로 응답 내보내기

        return new ResponseEntity(postings, HttpStatus.OK);
    }

    //@GetMapping
    public List<Posting> index1() {// list로 찾으면 11개의 프록시가 만들어짐.. db에 저장된 게시글의 갯수가 11개면 맞을 것 같은데..

        List<Posting> list = postingService.list();
        for (Posting posting : list) {
            System.out.println(posting);
        }
        return list;
    }

    @GetMapping
    public ResponseEntity index2(final Pageable pageable) { // 현재 500 에러 남...

       /* public ResponseEntity retrievePosts(final Pageable pageable) {
            Page<Post> posts = postRepository.findAll(pageable);
            return new ResponseEntity<>(posts,HttpStatus.OK);

            http://devstory.ibksplatform.com/2020/03/spring-boot-jpa-pageable.html

            */

        Page<Posting> posting = postingService.page(pageable);
        return new ResponseEntity<>(posting, HttpStatus.OK);

    }

    //@GetMapping // view로 받는 방법..
    public String index3(Model model, @PageableDefault(size = 5, sort = "postId", direction = Sort.Direction.DESC) Pageable pageable) {

        model.addAttribute("postings", postingService.page(pageable)); // model attribute가 아니라 json 형식으로 응답 내보내기
        return "postings";
    }



    /*@GetMapping // /api/posting?page=0&size=3&sort=id,desc&sort=username,desc  -> 요청은 이런 식으로. 여기서는 page만 따로 ? 뒤에 붙여주면 될듯. page는 0부터 시작
    public List<Posting> index() {

        List<Posting> list = postingService.list();
        for (Posting posting : list) {
            System.out.println(posting);
        }
        return list;

        //model.addAttribute("postings", postingService.list(pageable)); // model attribute가 아니라 json 형식으로 응답 내보내기
        //return "posting";
    }*/



    // 새로운 작성 폼 열기
    @GetMapping("/add")
    public ResponseEntity saveForm() {
        return new ResponseEntity(HttpStatus.OK);
    }

    // 포스팅 저장 - email 지정
    //@PostMapping("/add")
    /*public ResponseEntity saveforTest(@Valid @RequestBody PostingDTO postingDTO, BindingResult bindingResult) {

        log.info("[Controller] post_id:" + postingDTO.getPostId()  + " title: "+ postingDTO.getTitle() +
                " content" + postingDTO.getContent() + " 시간" + postingDTO.getPostTime());

        Member member = new Member(100L,"아아", "아아", "aaa@gmail.com", "1234@@aaaB", "ROLE_MEMBER");

        Posting newPosting = new Posting(postingDTO.getPostId() , member, postingDTO.getTitle(), postingDTO.getContent(),
                postingDTO.getPostTime(), postingDTO.getPostHits(), null);

        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            for(FieldError error : list) {
                return new ResponseEntity<>(error.getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        postingService.save(newPosting,"seon7129@naver.com");

        return new ResponseEntity(HttpStatus.CREATED);
    }*/

    // 포스팅 저장
    @PostMapping("/add")
    public ResponseEntity save(@Valid @RequestBody PostingDTO postingDTO, HttpSession session, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            for(FieldError error : list) {
                return new ResponseEntity<>(error.getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        postingService.save(postingDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    // 작성된 포스팅 열기
    @GetMapping("/{postId}")
    public ResponseEntity findById(@PathVariable Long postId, Model model) {
        model.addAttribute("posting", postingService.content(postId));
        return new ResponseEntity(HttpStatus.OK);
    }

    // 수정 폼 열기
    @GetMapping("/{postId}/edit")
    public ResponseEntity editForm(@PathVariable Long postId, Model model) {
        model.addAttribute("posting", postingService.content(postId));
        return new ResponseEntity(HttpStatus.OK);

    }

    // 포스팅 수정 완료
    @PostMapping("/{postId}/edit")
    public ResponseEntity edit(@PathVariable Long postId, @Valid @RequestBody PostingUpdateDTO posting, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            for(FieldError error : list) {
                return new ResponseEntity<>(error.getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
        }

        postingService.update(postId, posting);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 포스팅 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity deleteById(@PathVariable Long postId) {
        postingService.delete(postId);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

}