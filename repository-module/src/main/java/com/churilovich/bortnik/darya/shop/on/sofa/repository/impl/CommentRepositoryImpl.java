package com.churilovich.bortnik.darya.shop.on.sofa.repository.impl;

import com.churilovich.bortnik.darya.shop.on.sofa.repository.CommentRepository;
import com.churilovich.bortnik.darya.shop.on.sofa.repository.model.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

import static com.churilovich.bortnik.darya.shop.on.sofa.repository.constants.QueryParameterConstants.ID_QUERY_PARAMETER;

@Repository
public class CommentRepositoryImpl extends GenericRepositoryImpl<Long, Comment> implements CommentRepository {

    @Override
    @SuppressWarnings("unchecked")
    public List<Comment> findAllByArticleId(Long id) {
        String queryInStringFormat = "from " + entityClass.getName() + " where article_id=:id order by dateAdded desc";
        Query query = entityManager.createQuery(queryInStringFormat);
        query.setParameter(ID_QUERY_PARAMETER, id);
        return query.getResultList();
    }
}
