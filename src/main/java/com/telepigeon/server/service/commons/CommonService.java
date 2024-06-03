package com.telepigeon.server.service.commons;

import com.telepigeon.server.dto.enums.response.AgeRangesDto;
import com.telepigeon.server.dto.enums.response.GenderDto;
import com.telepigeon.server.dto.enums.response.KeywordsDto;
import com.telepigeon.server.dto.enums.response.RelationsDto;
import com.telepigeon.server.dto.type.AgeRange;
import com.telepigeon.server.dto.type.Gender;
import com.telepigeon.server.dto.type.Keyword;
import com.telepigeon.server.dto.type.Relation;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CommonService {
    public KeywordsDto getKeywords(){
        return KeywordsDto.of(
                Arrays.stream(Keyword.values())
                        .map(Keyword::getContent).toList()
        );
    }

    public GenderDto getGenders(){
        return GenderDto.of(
                Arrays.stream(Gender.values())
                        .map(Gender::getContent).toList()
        );
    }

    public AgeRangesDto getAgeRanges(){
        return AgeRangesDto.of(
                Arrays.stream(AgeRange.values())
                        .map(AgeRange::getContent).toList()
        );
    }

    public RelationsDto getRelations(){
        return RelationsDto.of(
                Arrays.stream(Relation.values())
                        .map(Relation::getContent).toList()
        );
    }

}
