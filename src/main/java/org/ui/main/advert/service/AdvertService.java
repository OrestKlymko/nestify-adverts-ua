package org.ui.main.advert.service;


import org.springframework.stereotype.Service;
import org.ui.main.advert.dto.FinalPageResponse;
import org.ui.main.advert.repository.AdvertRepository;
import org.ui.main.exceptions.NotFondException;

@Service
public class AdvertService {

    private final AdvertRepository advertRepository;

    public AdvertService(AdvertRepository advertRepository) {
        this.advertRepository = advertRepository;
    }


    public FinalPageResponse findAdvertById(Long id) throws NotFondException {
        return advertRepository.getAdvertById(id)
                .orElseThrow(() -> new NotFondException(String.format("Advert with id %s not found", id)));
    }
}
