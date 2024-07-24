CREATE INDEX idx_seller_agency_id ON seller(agency_id);
CREATE INDEX idx_property_features_property_id ON property_features(property_id);
CREATE INDEX idx_property_features_feature_id ON property_features(feature_id);
CREATE INDEX idx_property_advantages_property_id ON property_advantages(property_id);
CREATE INDEX idx_property_advantages_advantage_id ON property_advantages(advantage_id);
CREATE INDEX idx_images_advert_id ON images(advert_id);
