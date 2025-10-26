package com.mms.catalogservice.mother;

import com.mms.catalogservice.entity.OnlineStatus;
import com.mms.catalogservice.entity.ProductEntity;

public class ProductEntityObjectMother {

    public static final ProductEntity ANY = activeRadioProduct().build();
    public static final ProductEntity ANY_RADIO_PRODUCT = activeRadioProduct().build();
    public static final ProductEntity ANY_DELETED_PRODUCT = inactiveProduct();

    public static ProductEntity.ProductEntityBuilder activeRadioProduct() {
        String categoryIds = "1165;784;308;304;8200;1167;1169";

        return ProductEntity.builder()
                .id(21239770010L)
                .name("DIGITRADIO 360 CD IR SCHWARZ")
                .onlineStatus(OnlineStatus.ACTIVE)
                .longDescription("Klassische Eleganz und moderne Ausstattung bedeuten beim DIGITRADIO 360 CD IR keinen Widerspruch...")
                .shortDescription("DAB+/UKW/Internet Stereo-Designradio mit CD-Player...");
//                .associatedCategories(CategoryObjectMother.fromIdString(categoryIds));
    }

    public static ProductEntity inactiveProduct() {
        return activeRadioProduct()
                .onlineStatus(OnlineStatus.DELETED)
                .build();
    }
}