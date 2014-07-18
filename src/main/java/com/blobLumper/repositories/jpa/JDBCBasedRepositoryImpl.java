package com.blobLumper.repositories.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
/**
 * It is a wrapper implementation around JdbcOperations. 
 * So, in future if we have to change JdbcOperations implementation we
 * can do it here centrally and easily.
 * 
 * Also, this class declares what sort of JDBC operations do we need.
 * @author zoahib
 *
 */
public class JDBCBasedRepositoryImpl implements JDBCBasedRepository {

	@Autowired private JdbcOperations operations;
	
	
	@Override
	public List executeSQLForList(final String sql, final Object... parameters) {
		return operations.queryForList(sql, parameters);
		
	}
	
	@Override
	public int executeSQLForInt(final String sql, final Object... parameters){
		Integer result = operations.queryForObject(sql, Integer.class);
		if(result == null){
			return 0;
		}
		
		return result.intValue();
	}
	
}
