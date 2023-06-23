package kr.anabada.anabadaserver.domain.user.service;

import kr.anabada.anabadaserver.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NicknameService {
    private final UserRepository userRepository;

    public String generateNickname() throws IOException {
        return generateRandomNickname();
    }

    private String generateRandomNickname() throws IOException {
        String[] adjList;
        String[] nounList;
        try (InputStream adjStream = getClass().getResourceAsStream("/nickname_word_list/positive_adjectives.txt");
             InputStream nounStream = getClass().getResourceAsStream("/nickname_word_list/noun.txt");
             BufferedReader adjReader = new BufferedReader(
                     new InputStreamReader(Objects.requireNonNull(adjStream)));
             BufferedReader nounReader = new BufferedReader(
                     new InputStreamReader(Objects.requireNonNull(nounStream)))) {
            adjList = adjReader.lines().toArray(String[]::new);
            nounList = nounReader.lines().toArray(String[]::new);
        }

        Random rand = new SecureRandom();
        String generatedNickname;
        int maxTries = 20;
        do {
            int adjIndex = rand.nextInt(adjList.length);
            int nounIndex = rand.nextInt(nounList.length);
            generatedNickname = adjList[adjIndex] + " " + nounList[nounIndex];
            maxTries--;
        } while (isNicknameExist(generatedNickname) && maxTries > 0);
        return generatedNickname;
    }

    public boolean isNicknameExist(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
