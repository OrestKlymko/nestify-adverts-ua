package org.ui.main.services.advert.dto;

public record AdminResponse(
        Long advertId,
        Long idApplication,
        String finalUrlAdvert,
        String status
//        SEND, COMPLETED, RENTED, DISCARD
){
}
