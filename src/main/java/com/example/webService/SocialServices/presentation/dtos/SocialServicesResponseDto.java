package com.example.webService.SocialServices.presentation.dtos;

import java.time.LocalDate;

import com.example.webService.SocialServices.domain.models.SocialServicesType;

public class SocialServicesResponseDto {

    public int id;
    public String title;
    public String imageUrl;
    public String location;
    public String schedule;
    public LocalDate expirationDate;
    public SocialServicesType socialServicesType;
    
}
