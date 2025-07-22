package com.sistema.gestion.imagen;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Component("cloudinaryStrategy")
public class CloudinaryImagenStrategy implements ImagenStrategy {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String subirImagen(MultipartFile file) throws IOException {
        Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return result.get("secure_url").toString();
    }
}
