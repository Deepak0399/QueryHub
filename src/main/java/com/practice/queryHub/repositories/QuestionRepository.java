package com.practice.queryHub.repositories;

import com.practice.queryHub.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {

    @Query("SELECT q FROM Question q JOIN q.tags t WHERE t.id IN :tagIds")
    Page<Question> findQuestionsByTags(Set<UUID> tagId, Pageable pageable);
}
