package com.example.demo.services;


import com.example.demo.models.Product;
import com.example.demo.repositories.ProductRepository;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@PropertySource("config.properties")
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Value("${files.location}")
    private String uploadPath;
    @Value("${image.size_medium}")
    private Integer imageSize;


    public List<Product> listAll() {
        return(List<Product>) productRepository.findAll();
    }

    public void save(Product product) {
        productRepository.save(product);
    }
    public Product get(Integer id) throws ProductNotFoundException {
        Optional<Product> result = productRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new ProductNotFoundException("Could not find any users with the given ID");
    }

    public void delete(Integer id) throws ProductNotFoundException {

        productRepository.deleteById(id);
    }

    public void addImage(Product product, MultipartFile file)throws IOException {
        File uploadDir = new File(uploadPath);
        if(!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + FilenameUtils.removeExtension(file.getOriginalFilename())+".png";

        File temp = new File(uploadPath + "/" + resultFilename);
        product.setFileName(resultFilename);
        //
        file.transferTo(temp);

        resizeImage(temp, imageSize);
    }
    public boolean resizeImage(File sourceFile,Integer size) {
        try {
            BufferedImage bufferedImage = ImageIO.read(sourceFile);
            BufferedImage outputImage = Scalr.resize(bufferedImage, size);
            String newFileName = FilenameUtils.getBaseName(sourceFile.getName())
                    + "_" + size.toString() + ".png";
            System.out.println(FilenameUtils.getBaseName(sourceFile.getName()));
            Path path = Paths.get(uploadPath,newFileName);
            File newImageFile = path.toFile();

            ImageIO.write(outputImage, "png", newImageFile);
            outputImage.flush();
            return true;
        } catch (IOException e) {
            System.out.println("Erorr" + e);
            //logger.error(e.getMessage(), e);
            return false;
        }
    }

    public Page<Product> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Product> list;

        if (listAll().size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, listAll().size());
            list = listAll().subList(startItem, toIndex);
        }

        Page<Product> productPage
                = new PageImpl<Product>(list, PageRequest.of(currentPage, pageSize), listAll().size());

        return productPage;
    }

}
