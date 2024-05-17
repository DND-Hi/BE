package com.dnd.domain.bookmark.api;

import com.dnd.domain.bookmark.application.BookmarkService;
import com.dnd.domain.bookmark.dto.BookmarkResponse;
import com.dnd.domain.bookmark.dto.CreateBookmarkRequest;
import com.dnd.domain.common.annotation.LoginUsers;
import com.dnd.global.config.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/bookmark")
@RestController
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Operation(summary = "북마크 생성")
    @PostMapping
    public Long create(
            @RequestBody @Valid CreateBookmarkRequest request,
            @Parameter(hidden = true)
            @LoginUsers CustomUserDetails userDetails) {

        return bookmarkService.register(request, userDetails.getMemberId());
    }

    @Operation(summary = "유저의 북마크 조회")
    @GetMapping
    public List<BookmarkResponse> create(
            @Parameter(hidden = true)
            @LoginUsers CustomUserDetails userDetails) {
         return bookmarkService.findByMember(userDetails.getMemberId());
    }

    @Operation(summary = "유저의 북마크 삭제")
    @DeleteMapping("/{bookmarkId}")
    public void delete(
            @Parameter(hidden = true)
            @LoginUsers CustomUserDetails userDetails,
            @PathVariable Long bookmarkId) {

        bookmarkService.delete(userDetails.getMemberId(), bookmarkId);
    }
}
