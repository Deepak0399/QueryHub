package com.practice.queryHub.services.Impl;

import com.practice.queryHub.dtos.QuestionDTO;
import com.practice.queryHub.exception.QuestionNotFoundException;
import com.practice.queryHub.model.Question;
import com.practice.queryHub.model.Tag;
import com.practice.queryHub.model.User;
import com.practice.queryHub.repositories.QuestionRepository;
import com.practice.queryHub.repositories.TagRepository;
import com.practice.queryHub.repositories.UserRepository;
import com.practice.queryHub.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    @Override
    public Question createQuestion(QuestionDTO questionDTO) {
        Question question = new Question();
        question.setContent(questionDTO.getContent());
        question.setId(UUID.randomUUID());
        question.setTitle(questionDTO.getTitle());

        Optional<User> user = userRepository.findById(questionDTO.getUserId());
        user.ifPresent(question::setUser);
        Set<Tag> tags = questionDTO.getTagIds().stream()
                .map(tagRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        question.setTags(tags);
        return questionRepository.save(question);
    }

    @Override
    public void deleteQuestion(UUID questionId) {
        if (questionRepository.findById(questionId).isPresent()) {
            questionRepository.deleteById(questionId);
        }
        else {
            throw new QuestionNotFoundException("Question Not Found");
        }
    }

    @Override
    public List<Question> getQuestions(int offset, int limit) {
        return questionRepository.findAll(PageRequest.of(offset, limit)).getContent();
    }

    @Override
    public Optional<Question> getQuestionById(UUID questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        if (question.isPresent()) {
            return question;
        }
        else {
            throw new QuestionNotFoundException("Question Not Found");
        }
    }

    public Optional<Question> getQuestionByTag(UUID tagId, int offset, int limit) {
        Optional<Tag> tag = tagRepository.findById(tagId);
        if (tag.isPresent()) {
//            questionRepository.findQuestionsByTags(tagId, PageRequest.of(offset, limit)).getContent();
        }
        return null;
    }
}
