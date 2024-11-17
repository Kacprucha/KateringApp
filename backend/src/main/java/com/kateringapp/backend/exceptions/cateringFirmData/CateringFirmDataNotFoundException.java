package com.kateringapp.backend.exceptions.cateringFirmData;

import com.kateringapp.backend.exceptions.NotFoundException;

public class CateringFirmDataNotFoundException extends NotFoundException {
    public CateringFirmDataNotFoundException(Long id) {
      super("CateringFirmData with id " + id + " was not found");
    }
}
