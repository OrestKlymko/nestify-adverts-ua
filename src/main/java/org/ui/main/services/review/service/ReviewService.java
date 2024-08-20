package org.ui.main.services.review.service;


import org.springframework.stereotype.Service;
import org.ui.main.output.mail.dto.MailStructure;
import org.ui.main.output.mail.service.MailService;
import org.ui.main.services.review.dto.ReviewOnAdvertResponse;
import org.ui.main.services.review.repository.ReviewRepository;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MailService mailService;

    public ReviewService(ReviewRepository reviewRepository, MailService mailService) {
        this.reviewRepository = reviewRepository;
        this.mailService = mailService;
    }


    public void createReviewOnAdvert(Long advertId, Long reviewId) {
        reviewRepository.insertReviewOnAdvert(advertId, reviewId);
        List<ReviewOnAdvertResponse> reviewsOnAdvert = reviewRepository.getReviewsOnAdvert(advertId);
        if (!reviewsOnAdvert.isEmpty()) {
            sendAlertMessage(reviewsOnAdvert);
        }
    }

    private void sendAlertMessage(List<ReviewOnAdvertResponse> reviewsOnAdvert) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Підозріле оголошення за посиланням: https://frontend-ssr-nestify.vercel.app/sk/advert-info/%s\n", reviewsOnAdvert.get(0).getAdvertId()));
        builder.append("Ось статистика: \n");
        reviewsOnAdvert.forEach(advert -> {
            builder.append(advert.getReviewName()).append(" - ").append(advert.getCountReview()).append(".шт;\n");
        });
        MailStructure mailStructure = new MailStructure("Підозріле оголошення", builder.toString());
        mailService.sendMail("orest.klymko2020@gmail.com", mailStructure);
        mailService.sendMail("kossdemm@gmail.com", mailStructure);
    }
}
