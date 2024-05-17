package com.dnd.domain.bookmark.api;

import com.dnd.domain.bookmark.application.BookmarkService;
import com.dnd.domain.bookmark.dto.BookmarkResponse;
import com.dnd.domain.bookmark.dto.CreateBookmarkRequest;
import com.dnd.domain.common.annotation.LoginUsers;
import com.dnd.global.common.response.GlobalResponse;
import com.dnd.global.config.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
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
    public GlobalResponse create(
            @RequestBody @Valid CreateBookmarkRequest request,
            @LoginUsers CustomUserDetails userDetails) {

        Long bookmarkId = bookmarkService.register(request, userDetails.getMemberId());
        return GlobalResponse.success(200, bookmarkId);
    }

    @Operation(summary = "유저의 북마크 조회")
    @GetMapping
    public GlobalResponse create(@LoginUsers CustomUserDetails userDetails) {
        List<BookmarkResponse> result = bookmarkService.findByMember(userDetails.getMemberId());
        return GlobalResponse.success(200, result);
    }

    @Operation(summary = "유저의 북마크 삭제")
    @DeleteMapping
    public GlobalResponse delete(
            @LoginUsers CustomUserDetails userDetails,
            Long bookmarkId) {
        bookmarkService.delete(userDetails.getMemberId(), bookmarkId);
        return GlobalResponse.success(204, null);
    }
}
