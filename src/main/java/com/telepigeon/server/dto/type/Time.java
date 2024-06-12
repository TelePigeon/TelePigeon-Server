package com.telepigeon.server.dto.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Time {
    ZERO("00시"),
    ONE("01시"),
    TWO("02시"),
    THREE("03시"),
    FOUR("04시"),
    FIVE("05시"),
    SIX("06시"),
    SEVEN("07시"),
    EIGHT("08시"),
    NINE("09시"),
    TEN("10시"),
    ELEVEN("11시"),
    TWELVE("12시"),
    THIRTEEN("13시"),
    FOURTEEN("14시"),
    FIFTEEN("15시"),
    SIXTEEN("16시"),
    SEVENTEEN("17시"),
    EIGHTEEN("18시"),
    NINETEEN("19시"),
    TWENTY("20시"),
    TWENTY_ONE("21시"),
    TWENTY_TWO("22시"),
    TWENTY_THREE("23시"),
    ;
    private final String content;
}
