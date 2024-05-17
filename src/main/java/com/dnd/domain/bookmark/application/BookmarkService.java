package com.dnd.domain.bookmark.application;

import com.dnd.domain.bookmark.dao.BookmarkRepository;
import com.dnd.domain.bookmark.domain.Bookmark;
import com.dnd.domain.bookmark.dto.BookmarkResponse;
import com.dnd.domain.bookmark.dto.CreateBookmarkRequest;
import com.dnd.domain.event.dao.EventRepository;
import com.dnd.domain.event.domain.Event;
import com.dnd.domain.member.dao.MemberRepository;
import com.dnd.domain.member.domain.Member;
import com.dnd.global.error.exception.CustomException;
import com.dnd.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final EventRepository eventRepository;

    @Transactional
    public Long register(CreateBookmarkRequest request, Long memberId) {
        Member member = getMember(memberId);

        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new CustomException(ErrorCode.EVENT_NOT_FOUND));

        Bookmark bookMark = Bookmark.createBookMark(memberId, event.getId());
        bookmarkRepository.save(bookMark);

        event.increaseBookmarkCount();
        return bookMark.getId();
    }

    public List<BookmarkResponse> findByMember(Long memberId) {
        Member member = getMember(memberId);
        return bookmarkRepository.findByMemberId(memberId);
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    @Transactional
    public void delete(Long memberId, Long bookmarkId) {
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOKMARK_NOT_FOUND));

        Event event = eventRepository.findById(bookmark.getEventId())
                .orElseThrow(() -> new CustomException(ErrorCode.EVENT_NOT_FOUND));

        event.decreaseBookmarkCount();

        validateMember(memberId, bookmark);
        bookmarkRepository.delete(bookmark);
    }

    private void validateMember(Long memberId, Bookmark bookmark) {
        if (!Objects.equals(bookmark.getMemberId(), memberId)) {
            throw new CustomException(ErrorCode.BOOKMARK_USER_MISMATCH);
        }
    }
}
