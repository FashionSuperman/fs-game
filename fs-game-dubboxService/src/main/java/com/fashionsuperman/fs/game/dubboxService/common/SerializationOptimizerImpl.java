package com.fashionsuperman.fs.game.dubboxService.common;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;
import com.fashionsuperman.fs.game.dao.entity.User;
@SuppressWarnings("rawtypes")
public class SerializationOptimizerImpl implements SerializationOptimizer{

	@Override
	public Collection<Class> getSerializableClasses() {
		List<Class> classes = new LinkedList<>();
		classes.add(User.class);
		
		return classes;
	}

}
