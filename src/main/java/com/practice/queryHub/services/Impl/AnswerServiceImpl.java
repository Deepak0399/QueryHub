package com.practice.queryHub.services.Impl;

import com.practice.queryHub.dtos.AnswerDTO;
import com.practice.queryHub.exception.AnswerNotFoundException;
import com.practice.queryHub.model.Answer;
import com.practice.queryHub.model.Question;
import com.practice.queryHub.model.User;
import com.practice.queryHub.repositories.AnswerRepository;
import com.practice.queryHub.repositories.QuestionRepository;
import com.practice.queryHub.repositories.UserRepository;
import com.practice.queryHub.services.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    @Override
    public Answer createAnswer(AnswerDTO answerDTO) {
        Answer answer = new Answer();
        answer.setId(UUID.randomUUID());
        answer.setContent(answerDTO.getContent());

        Optional<User> user = userRepository.findById(answerDTO.getUserId());
        user.ifPresent(answer::setUser);

        Optional<Question> question = questionRepository.findById(answerDTO.getQuestionId());
        question.ifPresent(answer::setQuestion);

        return answerRepository.save(answer);
    }

    @Override
    public Optional<Answer> getAnswerById(UUID answerId) {
        Optional<Answer> answer = answerRepository.findById(answerId);
        if (answer.isPresent()) {
            return answer;
        }
        else {
            throw new AnswerNotFoundException("Answer Not Found");
        }
    }

    @Override
    public List<Answer> getAnswersByQuestionId(UUID questionId, int page, int size) {
        return answerRepository.findByQuestionId(questionId, PageRequest.of(page, size)).getContent();
    }

    @Override
    public void deleteAnswer(UUID answerId) {
        if (answerRepository.findById(answerId).isPresent()) {
            answerRepository.deleteById(answerId);
        }
        else {
            throw new AnswerNotFoundException("Answer Not Available To Delete");
        }
    }
}
