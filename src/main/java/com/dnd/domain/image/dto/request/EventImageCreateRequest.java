package com.dnd.domain.image.dto.request;

import com.dnd.domain.image.domain.ImageFileExtension;

public record EventImageCreateRequest (
			Long eventId,
			ImageFileExtension imageFileExtension
) {
}
