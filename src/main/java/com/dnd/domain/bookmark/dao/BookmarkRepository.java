package com.dnd.domain.bookmark.dao;

import com.dnd.domain.bookmark.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long>, BookmarkRepositoryCustom{

    List<Bookmark> findBookmarkByMemberId(Long memberId);
}
