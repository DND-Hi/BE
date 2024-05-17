package com.dnd.domain.bookmark.dao;

import com.dnd.domain.bookmark.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}
