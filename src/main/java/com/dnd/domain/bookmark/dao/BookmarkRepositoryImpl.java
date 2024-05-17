package com.dnd.domain.bookmark.dao;

import com.dnd.domain.bookmark.dto.BookmarkResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dnd.domain.bookmark.domain.QBookmark.bookmark;
import static com.dnd.domain.event.domain.QEvent.event;

@Repository
@RequiredArgsConstructor
public class BookmarkRepositoryImpl implements BookmarkRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BookmarkResponse> findByMemberId(Long memberId) {
        //TODO imageURL 추가해야 함
        return queryFactory.select(
                        Projections.constructor(BookmarkResponse.class,
                                bookmark.id,
                                event.id,
                                event.title,
                                event.description,
                                event.host,
                                event.cost,
                                event.startAt,
                                event.finishAt,
                                event.reservationUrl)
                )
                .from(bookmark)
                .join(event)
                .on(bookmark.eventId.eq(event.id))
                .where(bookmark.memberId.eq(memberId))
                .fetch();
    }
}
