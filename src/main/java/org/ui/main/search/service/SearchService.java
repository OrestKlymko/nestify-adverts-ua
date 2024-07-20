package org.ui.main.search.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.ui.main.search.dto.FilterSearchResponse;
import org.ui.main.search.dto.PageResponse;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchService {
//	public PageResponse<FilterSearchResponse> filterSearchForm(Map<String, List<String>> urlParameters) {
//		String sortStrategy = "newest";
//		int limit = 12;
//		long priceFrom = 0;
//		long priceTo = Long.MAX_VALUE;
//
//
//		for (Map.Entry<String, List<String>> parameter : urlParameters.entrySet()) {
//			String decodedValue = URLDecoder.decode(String.valueOf(parameter.getValue()), StandardCharsets.UTF_8)
//					.replace("[", "")
//					.replace("]", "");
//			switch (parameter.getKey()) {
//				case "city":
//					addCriteriaIfNotExists(query, Criteria.where("address.city").regex(decodedValue, "i"));
//					break;
//				case "address":
//					addCriteriaIfNotExists(query, Criteria.where("address.addressName").regex(decodedValue, "i"));
//					break;
//				case "rooms":
//					List<Integer> list = Arrays.stream(decodedValue.split(","))
//							.map(Integer::parseInt)
//							.collect(Collectors.toList());
//					Optional<Integer> option = list.stream().filter(i -> i >= 4).findFirst();
//					List<Integer> lowerRooms = list.stream().filter(i -> i < 4).collect(Collectors.toList());
//
//					if (option.isPresent() || !lowerRooms.isEmpty()) {
//						List<Criteria> criteriaList = new ArrayList<>();
//
//						if (option.isPresent()) {
//							criteriaList.add(Criteria.where("propertyRealty.room").gte(4));
//						}
//
//						if (!lowerRooms.isEmpty()) {
//							criteriaList.add(Criteria.where("propertyRealty.room").in(lowerRooms));
//						}
//
//						orCriteriaList.add(new Criteria().orOperator(criteriaList.toArray(new Criteria[0])));
//					}
//					break;
//				case "feature":
//					List<String> features = Arrays.stream(decodedValue.split(","))
//							.collect(Collectors.toList());
//					addCriteriaIfNotExists(query, Criteria.where("propertyRealty.feature").all(features));
//					break;
//				case "districts":
//					String[] splitDistrict = decodedValue.split(",");
//					List<Criteria> districtCriterias = Arrays.stream(splitDistrict)
//							.map(district -> Criteria.where("address.district").regex(district, "i"))
//							.collect(Collectors.toList());
//					orCriteriaList.add(new Criteria().orOperator(districtCriterias.toArray(new Criteria[0])));
//					break;
//				case "priceFrom":
//					priceFrom = Long.parseLong(decodedValue);
//					break;
//				case "priceTo":
//					priceTo = Long.parseLong(decodedValue);
//					break;
//				case "typeOwner":
//					addCriteriaIfNotExists(query, Criteria.where("seller.typeOwner").is(decodedValue));
//					break;
//				case "withKids":
//					addCriteriaIfNotExists(query, Criteria.where("propertyRealty.withKids").is(decodedValue));
//					break;
//				case "withPets":
//					addCriteriaIfNotExists(query, Criteria.where("propertyRealty.withPets").is(decodedValue));
//					break;
//				case "typeRealty":
//					addCriteriaIfNotExists(query, Criteria.where("typeRealty").is(decodedValue));
//					break;
//				case "approve":
//					addCriteriaIfNotExists(query, Criteria.where("approve").is(Boolean.valueOf(decodedValue)));
//					break;
//				case "agency":
//					addCriteriaIfNotExists(query, Criteria.where("seller.agencyName").is(decodedValue));
//					break;
//				case "sort":
//					sortStrategy = decodedValue;
//					break;
//			}
//		}
//		if (!orCriteriaList.isEmpty()) {
//			query.addCriteria(new Criteria().orOperator(orCriteriaList.toArray(new Criteria[0])));
//		}
//
//		List<AdvertModel> advertWithoutPrice = mongoTemplate.find(query, AdvertModel.class);
//		long maxPrice = 0;
//
//		if (!advertWithoutPrice.isEmpty()) {
//			maxPrice = advertWithoutPrice.stream()
//					.map(advert -> advert.getPropertyRealty().getTotalPrice())
//					.max(Long::compareTo)
//					.orElse(0L);
//		}
//
//		Map<Integer, Integer> statistic = new HashMap<>();
//		int interval = 50;
//
//		for (int currentPrice = 0; currentPrice <= maxPrice; currentPrice += interval) {
//			int finalCurrentPrice = currentPrice;
//			long count = advertWithoutPrice.stream()
//					.filter(advert -> advert.getPropertyRealty().getTotalPrice() >= finalCurrentPrice && advert.getPropertyRealty().getTotalPrice() < finalCurrentPrice + interval)
//					.count();
//			if (count > 0) {
//				statistic.put(currentPrice, (int) count);
//			}
//		}
//
//		if (maxPrice % interval != 0) {
//			int lastIntervalStart = (int) (maxPrice - maxPrice % interval);
//			long count = advertWithoutPrice.stream()
//					.filter(advert -> advert.getPropertyRealty().getTotalPrice() >= lastIntervalStart)
//					.count();
//			if (count > 0) {
//				statistic.put(lastIntervalStart, (int) count);
//			}
//		}
//
//		query.addCriteria(Criteria.where("propertyRealty.totalPrice").gte(priceFrom).lte(priceTo));
//
//		// Sort the query first
//		Sort sort = getSort(sortStrategy);
//		query.with(sort);
//
//		// Get the total count before pagination
//		long total = mongoTemplate.count(query, AdvertModel.class);
//
//		List<HousePointCoordinates> advertModelList = mongoTemplate.find(query, AdvertModel.class)
//				.stream()
//				.map(ModelMapper::mapToHousePointCoordinates)
//				.toList();
//
//
//		// Apply pagination
//		int offset = 0;
//		if (urlParameters.containsKey("offset")) {
//			offset = Integer.parseInt(String.valueOf(urlParameters.get("offset")));
//		}
//		PageRequest pageRequest = PageRequest.of(offset, limit);
//
//		// Fetch paginated and sorted data
//		List<AdvertModel> advertModels = mongoTemplate.find(query.with(pageRequest), AdvertModel.class);
//
//		List<AdvertSearchFilterDto> adverts = advertModels
//				.stream()
//				.map(ModelMapper::mapperToAdvertSearchFilterDto)
//				.collect(Collectors.toList());
//
//		return new CustomPageImpl<>(adverts, pageRequest, total, sortStrategy, maxPrice, statistic, advertModelList);
//	}
//
//	private static void addCriteriaIfNotExists(Query query, Criteria criteria) {
//		if (!query.getQueryObject().containsKey(criteria.getKey())) {
//			query.addCriteria(criteria);
//		}
//	}
//
//	private Sort getSort(String sortStrategy) {
//		return switch (sortStrategy) {
//			case "lowPrice" -> Sort.by(Sort.Direction.ASC, "propertyRealty.totalPrice");
//			case "highPrice" -> Sort.by(Sort.Direction.DESC, "propertyRealty.totalPrice");
//			default -> Sort.by(Sort.Direction.DESC, "createdAt");
//		};
//	}
//
//	public List<AdvertSearchFilterDto> getAddressHouseById(String uuid) throws NotFoundException {
//		return addressHouseRepository.findById(uuid)
//				.map(addressHouse ->
//						advertRepository.findAllById(addressHouse.getAdverts())
//								.stream()
//								.map(ModelMapper::mapperToAdvertSearchFilterDto)
//								.collect(Collectors.toList())
//				).orElseThrow(() -> new NotFoundException(String.format("AdvertHouse with id %s not found", uuid)));
//	}
//	}
}
