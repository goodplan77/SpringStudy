package com.kh.spring.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
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
			RedirectAttributes ra,
			HttpSession session) {
		
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
			ra.addFlashAttribute("alertMsg" , "로그인 성공");
			model.addAttribute("loginUser" , loginUser); // session scope 로 이관
			
			String nextUrl = (String) session.getAttribute("nextUrl");
			
			viewName = "redirect:" + (nextUrl != null ? nextUrl :"/");
			session.removeAttribute(nextUrl);
		}
		return viewName;
	}
	
	@GetMapping("/insert") // /member + /insert = /member/insert(GET)
	public String enrollForm() {
		return "member/memberEnrollForm";
	}
	
	@PostMapping("/insert") // /member/insert(POST)
	public String insertMember(
				Member m , // 사용자가 입력한 값들이 담겨 있는 Member 객체
				RedirectAttributes ra, // session -> request. 일회성 메세지
				Model model // request
			) {
		// 회원가입 요청 업무 로직
		// 암호화작업
		/*
		 * 1) spring-security 관련 의존성 추가 (web , core , config)
		 * 2) 인코딩클래스 bean 객체로 등록
		 * 3) web.xml 에 생성한 xml파일을 로딩할수 있도록 추가
		 */
		
		String encPwd = encoder.encode(m.getUserPwd()); // 객체로 부터 비밀번호 가져옴
		m.setUserPwd(encPwd); // 가져온 비밀번호(평문)를 암호화된 비밀번호로 변경
		int result = mService.insertMember(m);
		
		// 응답메세지 반환
		String url = ""; // result 값에 따라 view로 보낼 url 설정
		if(result > 0) {
			// 1. 처음 데이터를 담을때는 sessionScope에 데이터를 저장
			// 2. redirect가 완료된 후 sessionScope에 저장된 데이터를 requestScope로 이관
			ra.addFlashAttribute("alertMsg" , "회원가입 성공");
			url = "redirect:/"; // 재요청
		}else {
			model.addAttribute("errorMsg" , "회원가입 실패");
			url = "common/errorPage"; // 에러 페이지
		}
		return url;
	}
	
	@GetMapping("/myPage")
	public String myPage () {
		
		return "member/myPage";
	}
	
	@PostMapping("/update")
	public String updateMember(
				Member m, // 멤버 필드 값과 동일시 해야함 (자동으로 getter에서 get을 제거하고 자동으로 추가)
				Model model, // 알림 메세지 사용
				RedirectAttributes ra,
				HttpSession session
			) {
		// 업무 로직
		int result = mService.updateMember(m);
		
		String url = "";
		if(result > 0) {
			// 업데이트 성공
			// DB에 저장된 수정된 회원정보를 다시 불러와서 저장 -> login 메서드 재호출
			Member loginUser = mService.login(m);
			model.addAttribute("loginUser", loginUser);
			ra.addFlashAttribute("alertMsg" , "내정보수정 성공");
			url = "redirect:/member/myPage";
		} else {
			model.addAttribute("errorMsg" , "내정보수정 실패");
			url = "common/errorPage";
			
		}
		
		return url;
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session , SessionStatus status) {
		// session.removeAttribute("loginUser"); // 로그인 유저 정보 삭제
		// session.invalidate(); // 세션내 모든 데이터 무효화
		
		status.setComplete();
		// @SessionAttributes 태그를 통해 model에서 session 으로 이관된 데이터는
		// SessionStatus를 통해 비워줘야한다.
		
		// 기존 session 과는 다른 영역이라 생각하자
		// setComplete : SessionStatus 내부 정보를 무효화
		
		return "redirect:/";
	}
	
	@GetMapping("idCheck")
	@ResponseBody // 비동기 요청시 필요한 어노테이션 : 값자체만 반환?
	public String idCheck(String userId) {
		int result = mService.idCheck(userId);
		// 아이디가 존재한다면 1 , 없다면 0
		
		/*
		 * 컨트롤러에서 반환되는 값은 항상 forward 하고자 하는 응답 페이지 , 혹은 redirect 를 위한 경로로 해석을 한다.
		 *
		 * 그게 아니라 , "값 자체"를 반환 시키기 위해서는 @ResponseBody 어노테이션이 필요함.
		 */
		return result+""; // /WEB-INF/views/1.jsp;
	}
	
//	@PostMapping("/selectOne")
//	@ResponseBody
//	public /*String*/ HashMap<String , Object> selectOne(Member m) {
//		
//		Member m2 = mService.login(m);
//		
//		HashMap<String , Object> map = new HashMap<>();
//		if(m2 != null) {
//			// return "{\"userId\":\""+m2.getUserId()+"\"}";
//			map.put("userId" , m2.getUserId());
//			map.put("userName", m2.getUserName());
//		} else {
//			// return "{}";
//		}
//		// HashMap -> JSON 자동형변환(jackson-databind)
//		return map;
//	}
	
	/*
	 * jsonView Bean을 통한 데이터 처리
	 */
	
//	@PostMapping("/selectOne")
//	public String selectOne(Member m , Model model) {
//		Member m2 = mService.login(m);
//
//		HashMap<String, Object> map = new HashMap<>();
//		if (m2 != null) {
//			map.put("userId", m2.getUserId());
//			map.put("userName", m2.getUserName());
//		}	
//		model.addAttribute("userInfo",map);
//		
//		return "jsonView"; // View 객체의 이름
//	}
	
	@PostMapping("/selectOne")
	public ResponseEntity<Map<String , Object>> selectOne(Member m){
		
		// 1. try - catch
//		try {
//			throw new RuntimeException();
//		} catch (Exception e) {
//			System.out.println("1. try - catch 블럭에서 예외처리");
//			e.printStackTrace();
//			if(true) {
//				throw new RuntimeException();
//			}
//		}
		Member loginUser = mService.login(m);
		
		HashMap<String , Object> userInfo = new HashMap<>();
		
		ResponseEntity<Map<String , Object>> res = null;
		// ResponseEntity : HTTP 응답의 상태 코드, 헤더, 그리고 바디를 포함할 수 있는 객체
		if(loginUser == null) {
			res = ResponseEntity.notFound().build();
		} else {
			userInfo.put("userId", loginUser.getUserId());
			userInfo.put("userName", loginUser.getUserName());
			
			res = ResponseEntity
					.ok() //  HTTP 상태 코드 200 (OK)를 설정
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
					// header : HTTP 응답에 커스텀 헤더를 추가하는 데 사용
					//"application/json; charset=utf-8"
					.body(userInfo);
					// body : 응답 바디 설정
		}
		
		return res;
	}
	
	/*
	 * 스프링 예외처리 방법
	 * 1. try ~ catch / throws 메서드별로 예외 처리가 가능하며 항상 1순위로 예외 처리를 함.
	 * 
	 * 2. 하나의 컨트롤러에서 발생하는 예외를 모아서 처리하는 방법 -> @ExceptionHandler 어노테이션 추가 , 2순위로 작동함.
	 * 
	 * 3. 어플리케이션 전역에서 발생하는 예외를 모아서 처리하는 방법 -> @ControllerAdvice 어노테이션 추가 , 3순위로 작동함.
	 */
	
	// 2. @ExceptionHandler
	@ExceptionHandler
	public String exceptionHandler(Exception e , Model model) {
		System.out.println("2. ExceptionHandler 에서 예외처리");
		e.printStackTrace();
		
		model.addAttribute("errorMsg" , "서비스 이용중 문제가 발생했습니다.");
		
		return "common/errorPage";
	}
	
}
