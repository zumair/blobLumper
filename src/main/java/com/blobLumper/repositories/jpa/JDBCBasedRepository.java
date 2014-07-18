package com.blobLumper.repositories.jpa;

import java.util.List;

public interface JDBCBasedRepository {

	public abstract List executeSQLForList(String sql, Object... parameters);

	public abstract int executeSQLForInt(final String sql, final Object... parameters);

}