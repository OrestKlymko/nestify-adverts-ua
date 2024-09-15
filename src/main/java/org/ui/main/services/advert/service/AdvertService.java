package org.ui.main.services.advert.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.ui.main.services.advert.dto.AdvertInfoResponse;
import org.ui.main.services.advert.dto.FinalPageResponse;
import org.ui.main.services.advert.dto.SellerResponse;
import org.ui.main.services.advert.repository.AdvertRepository;
import org.ui.main.exceptions.NotFondException;

import java.util.Optional;


@Service
public class AdvertService {

	private static final Logger log = LoggerFactory.getLogger(AdvertService.class);

	@Value("${nestify-auth-service-url}")
	private String authServiceUrl;

	private final AdvertRepository advertRepository;
	private final RestTemplate restTemplate;


	public AdvertService(AdvertRepository advertRepository, RestTemplate restTemplate) {
		this.advertRepository = advertRepository;
		this.restTemplate = restTemplate;
	}


	public FinalPageResponse findAdvertById(Long id) throws NotFondException {
		Optional<AdvertInfoResponse> advertById = advertRepository.getAdvertById(id);
		if (advertById.isPresent() && !advertById.get().getSellerId().equals("parser")) {
			SellerResponse seller = getSeller(advertById.get().getSellerId());
			return new FinalPageResponse(advertById.get(), seller);
		} else if (advertById.isPresent()) {
			return new FinalPageResponse(advertById.get());
		}
		throw new NotFondException(String.format("Advert with id %s not found", id));
	}


	private SellerResponse getSeller(String sellerId) throws NotFondException {
		ResponseEntity<SellerResponse> response = restTemplate
				.getForEntity(authServiceUrl + "/api/seller/" + sellerId, SellerResponse.class);
		if (response.getStatusCode().equals(HttpStatusCode.valueOf(200))) {
			return response.getBody();
		} else {
			log.error("Seller with id {} not found", sellerId);
			throw new NotFondException(String.format("Seller with id %s not found", sellerId));
		}

	}
}
