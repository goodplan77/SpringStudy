package com.kh.spring.common.template;

import com.kh.spring.common.model.vo.PageInfo;

public class Pagination {
	
	public static PageInfo getpageInfo(int listCount , int currentPage , int pageLimit , int boardLimit) {
		int maxPage = (int)(Math.ceil(((double)listCount/boardLimit)));
		/*
		 * listCount 와 boardLimit에 영향을 받음
		 * 공식 구하기
		 * boardLimit값은 10이라는 가정하에 규칙을 세울 예정.
		 * 총 갯수			boardLimit			maxPage
		 * 100				10					10페이지 (10*10)
		 * 101				10					11페이지 (10*10 + 1)
		 * 110				10 					11페이지 (10*11)
		 * 111				10					12페이지 (10*11 + 1)
		 * 111/10 -> 11.1 -> 올림처리 -> 12.0 -> 소수점 빼기 -> 12
		 */
		int startPage = (currentPage-1) / pageLimit * pageLimit+1; 
	    /*
	     * currentPage 와 pageLimit 영향을 받음
	     * 
	     * - 공식구하기 단, pageLimit는 10이라는 가정으로 규칙을 찾기
	     * 
	     * startPage : 1 , 11 , 21 , 31 ... -> n * 10 + 1
	     * 10은 pageLimit 값이므로 n * pageLimit + 1
	     * 
	     * currentPage			startPage
	     *  1						1	-> n * pageLimit + 1 => n = 0
	     *  10						1	-> n * pageLimit + 1 => n = 0
	     *  11						11	-> n * pageLimit + 1 => n = 1
	     *  20						11	-> n * pageLimit + 1 => n = 0
	     *  
	     *  1 ~ 10 ->	n = 0
	     *  11~ 20 ->	n = 1
	     *  21~ 30 ->	n = 2
	     *  			n = (currentPage - 1) / pageLimit 연산추 소수점 제거시
	     *  최종 결과 n * pageLimit + 1 => (currentPage - 1) / pageLimit * pageLimit + 1
	     */
		int endPage = startPage + pageLimit-1; 
	    
	    if(endPage > maxPage) {
	    	endPage = maxPage;
	    }
	    
	    PageInfo pageInfo = new PageInfo(listCount , currentPage , pageLimit , boardLimit , maxPage , startPage , endPage);
	
	    return pageInfo;
	}
}
