package kr.anabada.anabadaserver.domain.user.service;

import kr.anabada.anabadaserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class NicknameService {
    private final UserRepository userRepository;

    @Autowired
    ResourceLoader resourceLoader;

    public String generateNickname() throws NoSuchAlgorithmException, IOException {
        return getRandomNickname();
    }

    private String getRandomNickname() throws NoSuchAlgorithmException, IOException {
        // get file from resources 'positive_adjectives.txt', 'noun.txt'
        File adjFile = ResourceUtils.getFile("classpath:nickname_word_list/positive_adjectives.txt");
        File nounFile = ResourceUtils.getFile("classpath:nickname_word_list/noun.txt");

        if (!adjFile.exists() || !nounFile.exists()) {
            throw new NullPointerException("nickname word-set not found");
        }

        String[] adjList = fileToStringArray(adjFile);
        String[] nounList = fileToStringArray(nounFile);
        
        // get secure random number of adjList.length
        Random rand = SecureRandom.getInstanceStrong();
        int adjIndex = rand.nextInt(adjList.length);
        int nounIndex = rand.nextInt(nounList.length);

        String generatedNickname = adjList[adjIndex] + " " + nounList[nounIndex];
        // check if nickname is already used
        while (userRepository.existsByNickname(generatedNickname)) {
            adjIndex = rand.nextInt(adjList.length);
            nounIndex = rand.nextInt(nounList.length);
            generatedNickname = adjList[adjIndex] + " " + nounList[nounIndex];
        }
        return generatedNickname;
    }

    private String[] fileToStringArray(File txtFile) throws IOException {
        // file을 읽고 \n으로 split한 String[]을 return
        return new String(Files.readAllBytes(txtFile.toPath())).split("\n");
    }
}
