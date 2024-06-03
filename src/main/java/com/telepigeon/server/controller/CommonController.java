package com.telepigeon.server.controller;

import com.telepigeon.server.dto.enums.AgeRangesDto;
import com.telepigeon.server.dto.enums.GenderDto;
import com.telepigeon.server.dto.enums.KeywordsDto;
import com.telepigeon.server.dto.enums.RelationsDto;
import com.telepigeon.server.service.commons.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommonController {
    private final CommonService commonService;

    @GetMapping("/keywords")
    public ResponseEntity<KeywordsDto> getKeywords(){
        return ResponseEntity.ok(commonService.getKeywords());
    }

    @GetMapping("/genders")
    public ResponseEntity<GenderDto> getGenders(){
        return ResponseEntity.ok(commonService.getGenders());
    }

    @GetMapping("/age-ranges")
    public ResponseEntity<AgeRangesDto> getAgeRanges(){
        return ResponseEntity.ok(commonService.getAgeRanges());
    }

    @GetMapping("/relations")
    public ResponseEntity<RelationsDto> getRelations(){
        return ResponseEntity.ok(commonService.getRelations());
    }
}
