package com.dnd.domain.bookmark.dao;

import com.dnd.domain.bookmark.dto.BookmarkResponse;

import java.util.List;

public interface BookmarkRepositoryCustom {

    List<BookmarkResponse> findByMemberId(Long memberId);
}
