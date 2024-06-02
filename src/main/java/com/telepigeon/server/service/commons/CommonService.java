package com.telepigeon.server.service.commons;

import com.telepigeon.server.dto.common.AgeRangesDto;
import com.telepigeon.server.dto.common.GenderDto;
import com.telepigeon.server.dto.common.KeywordsDto;
import com.telepigeon.server.dto.common.RelationsDto;
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
