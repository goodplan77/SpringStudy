package com.kh.spring.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

@Controller // bean 스캐너에 의해 bean 객체로 등록
//annotation programming

@RequestMapping("/member") // 하위메서드의 공통 주소
// 해당 링크로 요청이 들어오면 해당 객체가 요청 처리

@SessionAttributes({"loginUser"}) // Model에 추가된 값의 key값과 일치하는 값이 나오도록 설정
public class MemberController {
	
	/*
	 * 기존 객체 생성 방식
	 * private MemberService mService = new MemberServieImpl();
	 * 
	 * Spring의 DI(Dependency Injection) : 객체를 개발자가 생성하는게 아니라 , 스프링이 생성한 객체를
	 * 주입 받아서 사용하는 방식
	 * 
	 * @Autowired
	 */
	
	// @Autowired : 순환 참조 발생 위험성있음
	private MemberService mService; // new MemberServiceImpl();
	private BCryptPasswordEncoder encoder;
	
	// 생성자 주입
	// @Autowired 생략 가능
	public MemberController(MemberService mService , BCryptPasswordEncoder encoder) {
		this.mService = mService;
		this.encoder = encoder;
	}
	
	/*
	 * @RequestMapping ?
	 * - 클라이언트 요청 url에 맞는 클래스 / 메소드를 연결해주는 어노테이션
	 * 	해당 어노테이션이 붙은 클래스 / 메서드는 HandlerMapping 으로 등록된다.
	 * * HandlerMapping? 사용자가 지정한 url 정보들을 보관하는 저장소.
	 */
	
	// Spring에서 클라이언트의 요청값들을 뽑아내는 방법
	// 1. HttpServletRequest 로 뽑아내기
	
	  // /member/login + post 요청이 들어 왔을때 해당 메소드 수행
	  
//		@RequestMapping(value = "/login", method = RequestMethod.POST) // ENUM 클래스
//		public String login(HttpServletRequest req) {
//			System.out.println("userId : " + req.getParameter("userId"));
//			return "main";
//		}
	
	// 2. @RequestParam 어노테이션 사용
	// 넘어온 값이 없다면 기본값 설정이 가능.
	
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public String login(
//
//			@RequestParam(value = "userId", defaultValue = "mmm") String userId,
//
//			@RequestParam(value = "userPwd") String userPwd) {
//		System.out.println(userId + "||||||" + userPwd);
//		return "main";
//	}
	 
	
	// 3. @RequestParam 제거 가능
	// 단 , 요청 값이 필수가 아닌 경우 문제가 있을수 있음 (비어 있는 값)
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public String login(String userId, String userPwd) {
//		System.out.println(userId + "||||||" + userPwd);
//		return "main";
//	}
	
	// 4. 커맨드 객체 방식
	/*
	 * 1) 스프링컨테이너에서 매개변수로 지정한 vo클래스의 기본생성자를 호출하여 객체를 생성.
	 * 2) request로 전달받은 파라미터의 key값과 일치하는 필드의 setter 메서드를 찾아서 호출. 
	 */
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public ModelAndView login(@ModelAttribute Member m , 
//			HttpSession session ,
//			Model model ,
//			ModelAndView mv) {
//		System.out.println(m.getUserId() + "||||||" + m.getUserPwd());
		
		/*
		 * 로그인 요청처리 완료 후 "응답데이터를" 담아서 응답페이지로 url 재요청을 할 때
		 * 
		 * 1)	Model 객체를 이용
		 * 포워딩할 응답뷰페이지로 전달하고자 하는 데이터를 맵형식으로 담아줌.
		 * -> request , session 스코프 2개 가지고 있음
		 * -> 기본 scope : request.
		 * 		session scope로 저장하고자 한다면 클래스 위쪽에 @SessionAttribute을 추가해야한다.
		 * 
		 * 2)	ModelAndView 객체 이용
		 * 		ModelAndView에서 model은 데이터를 key-value형태로 담을 수 있는 Model 객체와 동일.
		 * 		View는 응답뷰에 대한 정보를 담을 수 있다. 반환형을 ModelAndView로 변경해줘야 한다.
		 * */
		
//		model.addAttribute("errorMsg" , "오류발생"); // request.setAttribute("errorMsg" , "오류발생");
//		
//		mv.addObject("errorMsg" , "로그인 실패");
//		mv.setViewName("common/errorPage");
//		
//		return mv;
//	}
	
	@PostMapping("/login")
	public String login(
			Member m ,
			Model model, // 응답 데이터 담아줄 객체 (로그인한 회원정보 , 로그인 성공/실패 메시지)
			RedirectAttributes ra) {
		
		// 암호화전 로그인 요청처리 작업
		// 업무 로직
		
//		Member loginUser = mService.login(m);
//		
//		String viewName = "";
//		if(loginUser == null) {
//			model.addAttribute("errorMsg" , "로그인 실패!"); // SessionAttributes와 일치하는 값이 없으므로 request이관
//			viewName = "common/errorPage";
//		} else {
//			model.addAttribute("loginUser" , loginUser); // session scope 로 이관
//			viewName = "redirect:/";
//		}
//		return viewName;
		
		// 암호화 후 로그인 업무로직
		Member loginUser = mService.login(m); // 아이디 기준으로 사용자 정보 조회.
		// loginUser의  비밀번호에는 암호화된 비밀번호가 담겨 있음.
		// m에는 암호화 되지 않은 평문형태의 비밀번호가 담겨 있음
		
		// matches(평문형태의 비밀번호 , 암호화된 비밀번호) : 내부적으로 두 값이 일치하는지 검사를 한 후 true/false 반환
		// encoder.matches(m.getUserPwd(), loginUser.getUserPwd())
		
		String viewName = "";
		if(!(loginUser != null && encoder.matches(m.getUserPwd(), loginUser.getUserPwd()))) {
			model.addAttribute("errorMsg" , "로그인 실패!"); // SessionAttributes와 일치하는 값이 없으므로 request이관
			viewName = "common/errorPage";
		} else {
			model.addAttribute("loginUser" , loginUser); // session scope 로 이관
			viewName = "redirect:/";
		}
		return viewName;
	}
}
