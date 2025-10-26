package com.mms.catalogservice.mother;

import com.mms.catalogservice.dto.ProductDTO;
import com.mms.catalogservice.entity.OnlineStatus;

public class ProductDTOObjectMother {

    public static final ProductDTO ANY = activeRadioProduct().build();
    public static final ProductDTO ANY_RADIO_PRODUCT = activeRadioProduct().build();
    public static final ProductDTO ANY_DELETED_PRODUCT = inactiveProduct();


    public static ProductDTO.ProductDTOBuilder activeRadioProduct() {
        String categoryIds = "1165;784;308;304;8200;1167;1169";

        return ProductDTO.builder()
                .id(21239770010L)
                .name("DIGITRADIO 360 CD IR SCHWARZ")
                .onlineStatus(OnlineStatus.ACTIVE)
                .fullDescription("Klassische Eleganz und moderne Ausstattung bedeuten beim DIGITRADIO 360 CD IR keinen Widerspruch...")
                .shortDescription("DAB+/UKW/Internet Stereo-Designradio mit CD-Player...");
//                .associatedCategories(CategoryObjectMother.fromIdString(categoryIds));
    }

    public static ProductDTO inactiveProduct() {
        return activeRadioProduct()
                .onlineStatus(OnlineStatus.DELETED)
                .build();
    }
}