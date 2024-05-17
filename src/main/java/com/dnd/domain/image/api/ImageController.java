package com.dnd.domain.image.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnd.domain.image.application.ImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

	private final ImageService imageService;

}
