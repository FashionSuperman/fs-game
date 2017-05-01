package com.fashionsuperman.fs.game.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fashionsuperman.fs.game.dao.mapper.SequenceMapper;

@Service
public class SequenceService {
	@Autowired
	private SequenceMapper sequenceMapper;
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public Long nextval(){
		return sequenceMapper.nextval();
	}
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public Long currval(){
		return sequenceMapper.currval();
	}
}
