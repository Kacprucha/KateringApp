package com.kateringapp.backend.exceptions.cateringFirmData;

import com.kateringapp.backend.exceptions.NotFoundException;

public class cateringFirmDataNotFoundException extends NotFoundException {
    public cateringFirmDataNotFoundException(Long id) {
      super("CateringFirmData with id " + id + " was not found");
    }
}
