/*
 * 北京果敢时代科技有限公司
 * 北京市朝阳区望京SOHO T3 B座1607
 * 邮编：100022
 * 网址：www.davdian.com
 */

package com.coolplay.user.user.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.coolplay.user.common.baseservice.impl.BaseService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.coolplay.user.user.model.MessageModel;
import tk.mybatis.mapper.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import com.coolplay.user.user.dao.*;
import com.coolplay.user.user.service.*;

/**
 * @author  davdian
 * @version 1.0
 * @since 1.0
 */

@Service("messageService")
public class MessageServiceImpl extends BaseService<MessageModel> implements IMessageService{
	@Autowired
	private MessageMapper messageMapper;
	
	@Override
	public MessageModel findById(Integer id) {
		if(id == null) {
			return null;
		}
		return messageMapper.findById(id);
	}


	public List<MessageModel> find(Map<String, Object> param) {
		return messageMapper.find(param);
	}

	@Override
	public PageInfo<MessageModel> selectByFilterAndPage(MessageModel messageModel, int pageNum,
		int pageSize) {
		PageHelper.startPage(pageNum, pageSize, true, false, null);
		List<MessageModel> list = this.selectByFilter(messageModel);
		return new PageInfo<>(list);
	}

	@Override
	public List<MessageModel> selectByFilter(MessageModel messageModel) {
		Example example = new Example(MessageModel.class);
		Example.Criteria criteria = example.createCriteria();

		if(messageModel.getUserId() != null) {
			criteria.andEqualTo("userId", messageModel.getUserId());
		}

		if(StringUtils.isNotEmpty(messageModel.getSortWithOutOrderBy())) {
			example.setOrderByClause(messageModel.getSortWithOutOrderBy());
		}
		return getMapper().selectByExample(example);
	}
}
