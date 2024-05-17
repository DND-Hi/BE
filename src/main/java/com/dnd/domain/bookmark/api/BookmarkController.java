package com.dnd.domain.bookmark.api;

import com.dnd.domain.bookmark.application.BookmarkService;
import com.dnd.domain.bookmark.dto.CreateBookmarkRequest;
import com.dnd.domain.common.annotation.LoginUsers;
import com.dnd.global.common.response.GlobalResponse;
import com.dnd.global.config.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/bookmark")
@RestController
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Operation(summary = "북마크 생성")
    @PostMapping("/create")
    public GlobalResponse create(
            @RequestBody @Valid CreateBookmarkRequest request,
            @LoginUsers CustomUserDetails userDetails) {

        Long bookmarkId = bookmarkService.register(request, userDetails.getMemberId());
        return GlobalResponse.success(200, bookmarkId);
    }
}
