package com.example.amanetpfe.Controllers;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PDFController {
    private final String API_KEY="mariem.mkadem@esprit.tn_5yDbY9692r159LIplg9WVq5q8gQp65PG3MLu2cg48OzsMDtwx58n4eZM47btxc91";
    private final String ENDPOINT = "https://api.pdf.co/v1/pdf/convert/to/json2";
     @PostMapping("/convert_pdf_to_json")
     public ResponseEntity<String> convertPDFToJson(@RequestParam("pdfFileUrl") String pdfFileUrl) {
         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
         headers.set("x-api-key", API_KEY);

         // Créer une requête HTTP avec l'URL du fichier PDF
         String requestBody = "url=" + pdfFileUrl;
         HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

         // Effectuer l'appel à l'API PDF.co
         RestTemplate restTemplate = new RestTemplate();
         ResponseEntity<String> response = restTemplate.exchange(ENDPOINT, HttpMethod.POST, request, String.class);

         return response;
     }
}

