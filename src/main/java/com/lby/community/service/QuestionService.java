package com.lby.community.service;

import com.lby.community.dto.PageDTO;
import com.lby.community.dto.QuestionDTO;
import com.lby.community.mapper.QuestionMapper;
import com.lby.community.mapper.UserMapper;
import com.lby.community.model.Question;
import com.lby.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PageDTO list(Integer page, Integer size) {

        PageDTO pageDTO = new PageDTO();

        Integer totalPage;

        Integer totalCount = questionMapper.count();

        if (totalCount%size == 0){
            totalPage = totalCount/size;
        }else{
            totalPage = totalCount/size + 1;
        }

        if(page<1){
            page = 1;
        }
        if(page > totalPage){
            page = totalPage;
        }

        pageDTO.setPagination(totalPage,page);

        Integer offset = size*(page-1);
        List<Question> questions = questionMapper.questionList(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);
        return pageDTO;
    }

    public PageDTO list(Integer userId, Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();

        Integer totalPage;

        Integer totalCount = questionMapper.countByUserId(userId);

        if (totalCount%size == 0){
            totalPage = totalCount/size;
        }else{
            totalPage = totalCount/size + 1;
        }

        if(page<1){
            page = 1;
        }
        if(page > totalPage){
            page = totalPage;
        }

        pageDTO.setPagination(totalPage,page);

        Integer offset = size*(page-1);
        List<Question> questions = questionMapper.List(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);
        return pageDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }
}
