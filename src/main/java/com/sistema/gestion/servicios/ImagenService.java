package com.sistema.gestion.servicios;

import com.sistema.gestion.imagen.ImagenStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImagenService {

    private final ImagenStrategy imagenStrategy;
 
    public ImagenService(@Qualifier("cloudinaryStrategy") ImagenStrategy imagenStrategy) {
        this.imagenStrategy = imagenStrategy;
    }

    public String subirImagen(MultipartFile file) throws IOException {
        return imagenStrategy.subirImagen(file);
    }
}
