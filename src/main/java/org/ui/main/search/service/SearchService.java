package org.ui.main.search.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
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

	public PageResponse<FilterSearchResponse> filterSearchForm(Map<String, List<String>> urlParameters) {
		int limit = 12;
		String sortStrategy = "newest";
		long priceFrom = 0;
		long priceTo = Integer.MAX_VALUE;
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Advert> cq = cb.createQuery(Advert.class);
		Root<Advert> advert = cq.from(Advert.class);
		List<Predicate> predicates = new ArrayList<>();

		applyParameters(urlParameters, predicates, cb, advert, priceFrom, priceTo, sortStrategy, cq);

		// Запит для отримання статистики без урахування цін
		CriteriaQuery<Advert> statisticCq = cb.createQuery(Advert.class);
		Root<Advert> statisticAdvert = statisticCq.from(Advert.class);
		List<Predicate> statisticPredicates = new ArrayList<>();
		applyParameters(urlParameters, statisticPredicates, cb, statisticAdvert, null, null, sortStrategy, statisticCq);
		statisticCq.where(statisticPredicates.toArray(new Predicate[0]));

		TypedQuery<Advert> statisticQuery = entityManager.createQuery(statisticCq);
		List<Advert> statisticAdverts = statisticQuery.getResultList();
		Map<Integer, Integer> statistic = getStatistic(statisticAdverts);

		// Запит для підрахунку загальної кількості оголошень з урахуванням ціни
		predicates.add(cb.greaterThanOrEqualTo(advert.get("propertyRealty").get("totalPrice"), priceFrom));
		predicates.add(cb.lessThanOrEqualTo(advert.get("propertyRealty").get("totalPrice"), priceTo));
		cq.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Advert> query = entityManager.createQuery(cq);
		int total = query.getResultList().size();
		query.setFirstResult(0);
		query.setMaxResults(Integer.MAX_VALUE);
		List<CoordinateResponse> advertsOnMap = getAdvertsOnMap(query.getResultList());

		int offset = 0;
		if (urlParameters.containsKey("offset")) {
			offset = Integer.parseInt(String.valueOf(urlParameters.get("offset")));
		}
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		List<Advert> adverts = query.getResultList();

		Integer maxPrice = getMaxPrice(adverts);
		List<FilterSearchResponse> filterSearchResponses = convertToResponse(adverts);

		Pageable pageRequest = PageRequest.of(offset / limit, limit);
		Page<FilterSearchResponse> page = new PageImpl<>(filterSearchResponses, pageRequest, total);

		return new PageResponse<>(page, sortStrategy, maxPrice, statistic, advertsOnMap);
	}

	private Integer getMaxPrice(List<Advert> adverts) {
		return adverts.stream()
				.map(advert -> advert.getPropertyRealty().getTotalPrice())
				.max(Integer::compareTo)
				.orElse(0);
	}

	private Map<Integer, Integer> getStatistic(List<Advert> adverts) {
		return adverts.stream()
				.collect(Collectors.groupingBy(
						advert -> (advert.getPropertyRealty().getTotalPrice() / 50) * 50,
						TreeMap::new,
						Collectors.summingInt(advert -> 1)
				));
	}

	private void applySort(String sortStrategy, CriteriaBuilder cb, Root<Advert> advert, CriteriaQuery<Advert> cq) {
		List<Order> orders = new ArrayList<>();
		switch (sortStrategy) {
			case "lowPrice":
				orders.add(cb.asc(advert.get("propertyRealty").get("totalPrice")));
				break;
			case "highPrice":
				orders.add(cb.desc(advert.get("propertyRealty").get("totalPrice")));
				break;
			default:
				orders.add(cb.desc(advert.get("publishedAt")));
				break;
		}
		cq.orderBy(orders);
	}

	private void applyParameters(Map<String, List<String>> urlParameters, List<Predicate> predicates, CriteriaBuilder cb, Root<Advert> advert, Long priceFrom, Long priceTo, String sortStrategy, CriteriaQuery<Advert> cq) {
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
							.collect(Collectors.toList());
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
					Subquery<Long> subquery = cq.subquery(Long.class);
					Root<Advert> subRoot = subquery.from(Advert.class);
					Join<Object, Object> subFeaturesJoin = subRoot.join("propertyRealty").join("features");
					subquery.select(subRoot.get("id"))
							.where(subRoot.get("id").in(advert.get("id")),
									subFeaturesJoin.get("featureName").in(features))
							.groupBy(subRoot.get("id"))
							.having(cb.equal(cb.countDistinct(subFeaturesJoin.get("featureName")), features.size()));
					predicates.add(advert.get("id").in(subquery));
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
		applySort(sortStrategy, cb, advert, cq);
	}

	private List<CoordinateResponse> getAdvertsOnMap(List<Advert> adverts) {
		return adverts.stream()
				.map(advertInList ->
						new CoordinateResponse(advertInList.getId(), advertInList.getAddress().getLongitude(), advertInList.getAddress().getLatitude()))
				.collect(Collectors.toList());
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
		)).collect(Collectors.toList());
	}
}
