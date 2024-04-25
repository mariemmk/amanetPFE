package com.example.amanetpfe.Controllers;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/OCR")
@CrossOrigin(origins = "*")
public class OCRController {
    @PostMapping("/extract-id-card-data")
    public ResponseEntity<String> extractIdCardData(@RequestParam("idCardImage") MultipartFile idCardImage) {
        try {
            File imageFile = convertMultipartFileToFile(idCardImage);
            ITesseract tesseract = new Tesseract();
            tesseract.setDatapath("path_to_your_tessdata_directory"); // Set the tessdata directory path
            String result = tesseract.doOCR(imageFile);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (TesseractException | IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error extracting ID card data.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Utility method to convert MultipartFile to File
    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        file.transferTo(convertedFile);
        return convertedFile;
    }
}
