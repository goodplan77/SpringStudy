package com.kh.spring.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring.member.model.vo.Member;

@Repository // 저장 관련
public class MemberDaoImpl implements MemberDao{

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public Member login(Member m) {
		return sqlSession.selectOne("member.login",m);
	}

	@Override
	public int insertMember(Member m) {
		return sqlSession.insert("member.insertMember",m);
	}

	@Override
	public int updateMember(Member m) {
		return sqlSession.update("member.updateMember",m);
	}

	@Override
	public int idCheck(String userId) {
		return sqlSession.selectOne("member.idCheck",userId);
	}

}
