package com.sistema.gestion.imagen;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Component("localStrategy")
public class LocalImagenStrategy implements ImagenStrategy {

    private static final String CARPETA = "C:\\Users\\Christian\\Documents\\Proyectos\\PROJECTS_JAVA\\FRAMEWORK-SPRING\\back-end\\src\\main\\resources\\imagen";

    @Override
    public String subirImagen(MultipartFile file) throws IOException {
        Files.createDirectories(Paths.get(CARPETA));
        Path rutaArchivo = Paths.get(CARPETA, file.getOriginalFilename());
        Files.write(rutaArchivo, file.getBytes());
        return rutaArchivo.toAbsolutePath().toString();
    }
}
