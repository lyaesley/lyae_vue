package com.lyae.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SoccerDao {
	
	@Autowired SqlSessionTemplate sql;
	
	public List<Map<String, String>> test() {
		return sql.selectList("mapper.test.soccer");
	}
}
