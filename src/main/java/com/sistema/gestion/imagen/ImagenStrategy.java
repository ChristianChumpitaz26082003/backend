package com.sistema.gestion.imagen;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImagenStrategy {
    String subirImagen(MultipartFile file) throws IOException;
}
