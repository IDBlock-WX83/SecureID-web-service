package com.example.webService.Blockchain.domain.services;

import com.example.webService.Blockchain.domain.model.Block;
import com.example.webService.Blockchain.domain.model.Blockchain;
import com.example.webService.Blockchain.domain.model.Identification;
import com.example.webService.Blockchain.infrastructure.persistence.jpa.repositories.BlockRepository;
import com.example.webService.Blockchain.infrastructure.persistence.jpa.repositories.IdentificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BlockchainService {

    private final Blockchain blockchain;
    private final IdentificationRepository identificationRepository;
    private final BlockRepository blockRepository;

    @Autowired
    public BlockchainService(IdentificationRepository identificationRepository, BlockRepository blockRepository) {
        this.blockchain = new Blockchain();
        this.identificationRepository = identificationRepository;
        this.blockRepository = blockRepository;

        // Verificar si el bloque génesis existe; si no, crearlo y guardarlo
        if (blockRepository.count() == 0) {
            Block genesisBlock = blockchain.createGenesisBlock();
            genesisBlock.mineBlock(blockchain.getDifficulty()); // Minar el bloque génesis
            blockRepository.save(genesisBlock);  // Guardar el bloque génesis en la base de datos
            blockchain.getBlockchain().add(genesisBlock); // Agregar el bloque génesis en memoria
        } else {
            // Cargar los bloques de la base de datos en la blockchain en memoria
            blockchain.getBlockchain().addAll(blockRepository.findAll());
        }
    }

    //para gaurar imagnes
    /*
    * public void addIdentification(Identification identification, byte[] firmaData, byte[] dniFrontalData, byte[] dniPosteriorData) {
    String directory = "/path/to/your/image/folder";
    //String directory = "imagenes/fotos";
    *
    // Guarda las imágenes y obtiene las rutas
    String firmaPath = saveImageToFileSystem(firmaData, directory);
    String dniFrontalPath = saveImageToFileSystem(dniFrontalData, directory);
    String dniPosteriorPath = saveImageToFileSystem(dniPosteriorData, directory);

    // Establece las rutas en el objeto Identification
    identification.setFirma(firmaPath);
    identification.setDniFrontal(dniFrontalPath);
    identification.setDniPosterior(dniPosteriorPath);

    // Guarda la identificación en la base de datos
    Identification savedIdentification = identificationRepository.save(identification);

    // Crear un bloque para la nueva identificación
    List<Identification> newIdentifications = new ArrayList<>();
    newIdentifications.add(savedIdentification);

    Block newBlock = new Block(newIdentifications, blockchain.getLatestBlock().getHash());
    newBlock.mineBlock(blockchain.getDifficulty());

    // Guarda el bloque en la base de datos
    blockRepository.save(newBlock);
    blockchain.getBlockchain().add(newBlock);
}

    * */


    public void addIdentification(Identification identification) {
        // Guarda la identificación en la base de datos y obtiene la versión guardada con el id
        Identification savedIdentification = identificationRepository.save(identification);

        // Crea una lista de identificaciones para el bloque, utilizando la identificación guardada
        List<Identification> newIdentifications = new ArrayList<>();
        newIdentifications.add(savedIdentification);

        // Crear un nuevo bloque con la identificación guardada y minarlo
        Block newBlock = new Block(newIdentifications, blockchain.getLatestBlock().getHash());
        newBlock.mineBlock(blockchain.getDifficulty());

        // Guarda el nuevo bloque en la base de datos
        blockRepository.save(newBlock);

        // Agrega el nuevo bloque a la blockchain en memoria
        blockchain.getBlockchain().add(newBlock);
    }

    public boolean validateBlockchain() {
        return blockchain.isChainValid();
    }

    public List<Block> getBlockchain() {
        // Recupera los bloques desde la base de datos
        return blockRepository.findAll();
    }


    //metoo para gaurar imagenes
    public String saveImageToFileSystem(byte[] imageData, String directory) {
        String imageName = UUID.randomUUID().toString() + ".jpg";
        String path = directory + "/" + imageName;

        try {
            Files.write(Paths.get(path), imageData, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path; // Devuelve la ruta para almacenar en la base de datos
    }
}
