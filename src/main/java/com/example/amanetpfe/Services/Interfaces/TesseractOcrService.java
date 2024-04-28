package com.example.amanetpfe.Services.Interfaces;

import com.example.amanetpfe.Entities.ImageTextDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface TesseractOcrService {
    ImageTextDto extractTextFromImage(MultipartFile file) throws IOException;
}
