package org.ui.main.search.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.hibernate.query.Query;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.ui.main.advert.model.Advert;
import org.ui.main.search.dto.PageResponse;
import org.ui.main.search.dto.PriceRangeStatistic;
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
        String sortStrategy = "newest";
        int limit = 12;
        long priceFrom = 0;
        long priceTo = Integer.MAX_VALUE;

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Advert> cq = cb.createQuery(Advert.class);
        Root<Advert> advert = cq.from(Advert.class);
        List<Predicate> predicates = new ArrayList<>();
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

//        List<HousePointCoordinates> advertModelList = mongoTemplate.find(query, AdvertModel.class)
//                .stream()
//                .map(ModelMapper::mapToHousePointCoordinates)
//                .toList();

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
        long total = query.unwrap(Query.class).getResultList().size();

        List<PriceRangeStatistic> statisticFromDatabase = searchRepository.getStatistic(50);
        Map<Integer, Integer> statistic = getStatistic(statisticFromDatabase);

        Long maxPrice = !statisticFromDatabase.isEmpty() ? statisticFromDatabase.get(statisticFromDatabase.size() - 1).getPriceRange() : 0L;
        Pageable pageRequest = PageRequest.of(offset / limit, limit);
        Page<Advert> page = new PageImpl<>(adverts, pageRequest, total);

        return new PageResponse<>(page, sortStrategy, maxPrice, statistic, null);
    }

    private Map<Integer, Integer> getStatistic(List<PriceRangeStatistic> statisticFromDatabase) {
        Map<Integer, Integer> statistic = new HashMap<>();
        for (PriceRangeStatistic priceRangeStatistic : statisticFromDatabase) {
            statistic.put(priceRangeStatistic.getPriceRange(), priceRangeStatistic.getCount());
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

}
