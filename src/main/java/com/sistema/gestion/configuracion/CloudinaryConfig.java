package com.sistema.gestion.configuracion;
import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(System.getenv("CLOUDINARY_URL") != null ?
            System.getenv("CLOUDINARY_URL") :
            "cloudinary://759345913141813:5u6aGnxgYDp8kcNQEr_5_8qE2aI@dfopzpjo8");
    }
}
