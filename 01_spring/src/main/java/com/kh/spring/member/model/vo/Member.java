package com.kh.spring.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor //매개변수 없는 생성자 자동 생성 
@AllArgsConstructor // 매개 변수 있는 생성자 자동 생성
@Getter @Setter // getter , setter 생성
@ToString
@Builder
// 필드가 추가 되더라도 알아서 위 어노테이션 설정에 따라 알아서 추가 해줌
public class Member {

	private int userNo;
	private String userId;
	private String userPwd;
	private String userName;
	private String email;
	private String birthday;
	private String gender;
	private String phone;
	private String address;
	private Date enrollDate;
	private Date modifyDate;
	private String status;

}
