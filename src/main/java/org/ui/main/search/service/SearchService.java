package org.ui.main.search.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.hibernate.query.Query;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.ui.main.advert.model.Advantages;
import org.ui.main.advert.model.Advert;
import org.ui.main.advert.model.Features;
import org.ui.main.search.dto.CoordinateResponse;
import org.ui.main.search.dto.FilterSearchResponse;
import org.ui.main.search.dto.PageResponse;
import org.ui.main.search.dto.PriceRangeStatisticResponse;
import org.ui.main.search.repository.SearchRepository;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchService {

	@PersistenceContext
	private EntityManager entityManager;
	private final SearchRepository searchRepository;


	public SearchService(SearchRepository searchRepository) {
		this.searchRepository = searchRepository;
	}


	public PageResponse<Advert> filterSearchForm(Map<String, List<String>> urlParameters) {
		int limit = 12;
		String sortStrategy = "newest";
		long priceFrom = 0;
		long priceTo = Integer.MAX_VALUE;
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Advert> cq = cb.createQuery(Advert.class);
		Root<Advert> advert = cq.from(Advert.class);
		List<Predicate> predicates = new ArrayList<>();
		applyParameters(urlParameters, predicates, cb, advert, priceFrom, priceTo, sortStrategy);
		applySort(sortStrategy, cb, advert, cq);

		predicates.add(cb.greaterThanOrEqualTo(advert.get("propertyRealty").get("totalPrice"), priceFrom));
		predicates.add(cb.lessThanOrEqualTo(advert.get("propertyRealty").get("totalPrice"), priceTo));

		cq.where(predicates.toArray(new Predicate[0]));

		int offset = 0;
		if (urlParameters.containsKey("offset")) {
			offset = Integer.parseInt(String.valueOf(urlParameters.get("offset")));
		}

		TypedQuery<Advert> query = entityManager.createQuery(cq);
		query.setFirstResult(offset);
		query.setMaxResults(limit);

		List<Advert> adverts = query.getResultList();
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		Root<Advert> countRoot = countQuery.from(Advert.class);
		countQuery.select(cb.count(countRoot));
		List<Predicate> countPredicates = new ArrayList<>(predicates);
		countQuery.where(countPredicates.toArray(new Predicate[0]));
		Long total = entityManager.createQuery(countQuery).getSingleResult();

		List<PriceRangeStatisticResponse> statisticFromDatabase = searchRepository.getStatistic(50);

		Map<Integer, Integer> statistic = getStatistic(statisticFromDatabase);
		Long maxPrice = !statisticFromDatabase.isEmpty() ? statisticFromDatabase.get(statisticFromDatabase.size() - 1).getPriceRange() : 0L;
		List<CoordinateResponse> advertsOnMap = getAdvertsOnMap(adverts);
		List<FilterSearchResponse> filterSearchResponses = convertToResponse(adverts);

		Pageable pageRequest = PageRequest.of(offset / limit, limit);
		Page<FilterSearchResponse> page = new PageImpl<>(filterSearchResponses, pageRequest, total);

		return new PageResponse<>(page, sortStrategy, maxPrice, statistic, advertsOnMap);
	}

	private Map<Integer, Integer> getStatistic(List<PriceRangeStatisticResponse> statisticFromDatabase) {
		Map<Integer, Integer> statistic = new HashMap<>();
		for (PriceRangeStatisticResponse priceRangeStatisticResponse : statisticFromDatabase) {
			statistic.put(priceRangeStatisticResponse.getPriceRange(), priceRangeStatisticResponse.getCount());
		}
		return statistic;
	}

	private void applySort(String sortStrategy, CriteriaBuilder cb, Root<Advert> advert, CriteriaQuery<Advert> cq) {
		List<Order> orders = new ArrayList<>();
		switch (sortStrategy) {
			case "priceAsc":
				orders.add(cb.asc(advert.get("propertyRealty").get("totalPrice")));
				break;
			case "priceDesc":
				orders.add(cb.desc(advert.get("propertyRealty").get("totalPrice")));
				break;
			default:
				orders.add(cb.desc(advert.get("publishedAt")));
				break;
		}
		cq.orderBy(orders);
	}

	private void applyParameters(Map<String, List<String>> urlParameters, List<Predicate> predicates, CriteriaBuilder cb, Root<Advert> advert, Long priceFrom, Long priceTo, String sortStrategy) {
		for (Map.Entry<String, List<String>> parameter : urlParameters.entrySet()) {
			String decodedValue = URLDecoder.decode(String.valueOf(parameter.getValue()), StandardCharsets.UTF_8)
					.replace("[", "")
					.replace("]", "");
			switch (parameter.getKey()) {
				case "city":
					predicates.add(cb.equal(advert.get("address").get("city"), decodedValue));
					break;
				case "address":
					predicates.add(cb.like(advert.get("address").get("addressName"), "%" + decodedValue + "%"));
					break;
				case "rooms":
					List<Integer> roomList = Arrays.stream(decodedValue.split(","))
							.map(Integer::parseInt)
							.toList();
					Optional<Integer> roomOption = roomList.stream().filter(i -> i >= 4).findFirst();
					List<Integer> lowerRooms = roomList.stream().filter(i -> i < 4).collect(Collectors.toList());

					if (roomOption.isPresent() || !lowerRooms.isEmpty()) {
						if (roomOption.isPresent()) {
							predicates.add(cb.greaterThanOrEqualTo(advert.get("propertyRealty").get("room"), 4));
						}

						if (!lowerRooms.isEmpty()) {
							predicates.add(advert.get("propertyRealty").get("room").in(lowerRooms));
						}
					}
					break;
				case "feature":
					List<String> features = Arrays.stream(decodedValue.split(","))
							.collect(Collectors.toList());
					predicates.add(advert.get("propertyRealty").get("room").in(features));
					break;
				case "districts":
					String[] splitDistrict = decodedValue.split(",");
					predicates.add(advert.get("address").get("district").in(splitDistrict));
					break;
				case "priceFrom":
					priceFrom = Long.parseLong(decodedValue);
					break;
				case "priceTo":
					priceTo = Long.parseLong(decodedValue);
					break;
				case "typeOwner":
					List<String> typeOwners = Arrays.asList(decodedValue.split(","));
					predicates.add(advert.get("seller").get("typeOwner").in(typeOwners));
					break;
				case "withKids":
					predicates.add(cb.equal((advert.get("propertyRealty").get("withKids")), decodedValue));
					break;
				case "withPets":
					predicates.add(cb.equal((advert.get("propertyRealty").get("withPets")), decodedValue));
					break;
				case "typeRealty":
					predicates.add(cb.equal((advert.get("typeRealty")), decodedValue));
					break;
				case "agency":
					predicates.add(cb.equal((advert.get("seller").get("agencyName")), decodedValue));
					break;
				case "sort":
					sortStrategy = decodedValue;
					break;
			}
		}
		predicates.add(cb.equal((advert.get("status")), "IN_USE"));
	}

	private List<CoordinateResponse> getAdvertsOnMap(List<Advert> adverts) {
		return adverts
				.stream()
				.map(advertInList ->
						new CoordinateResponse(advertInList.getId(), advertInList.getAddress().getLongitude(), advertInList.getAddress().getLatitude()))
				.toList();
	}

	private List<FilterSearchResponse> convertToResponse(List<Advert> adverts) {
		return adverts.stream().map(advert -> new FilterSearchResponse(
				advert.getId(),
				advert.getAddress().getLongitude(),
				advert.getAddress().getLatitude(),
				advert.getPropertyRealty().getTotalPrice(),
				advert.getPropertyRealty().getRoom(),
				advert.getPropertyRealty().getSquare(),
				advert.getPropertyRealty().getFloor(),
				advert.getAddress().getAddressName(),
				advert.getPropertyRealty().getFeatures().stream().map(Features::getFeatureName).collect(Collectors.toSet()),
				advert.getDescription(),
				advert.getPropertyRealty().getAdvantageList().stream().map(Advantages::getAdvantageName).collect(Collectors.toSet()),
				advert.getPublishedAt(),
				advert.getAddress().getDistrict(),
				advert.getAddress().getBuildIdMapTiler(),
				advert.getImages().stream().findFirst().orElse(""),
				advert.getStatus(),
				advert.getSeller().getAgency().getAgencyCatalog()
		)).toList();
	}
}
