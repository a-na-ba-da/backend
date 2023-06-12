package kr.anabada.anabadaserver.domain.user.service;

import kr.anabada.anabadaserver.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class NicknameServiceTest {
    @Autowired
    private NicknameService nicknameService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("닉네임 생성 정상 작동 확인")
    void testGetRandomNickname() throws NoSuchAlgorithmException, IOException {
        String nickname = nicknameService.generateNickname();

        // check if nickname is not null or empty
        Assertions.assertNotNull(nickname);
        Assertions.assertNotEquals("", nickname);

        // check if nickname contains a space
        Assertions.assertTrue(nickname.contains(" "));

        // check if nickname is unique
        Assertions.assertFalse(userRepository.existsByNickname(nickname));
    }

    @Test
    @DisplayName("닉네임 단어 파일에 공백이 없는지 확인")
    void testFileToStringArray() throws IOException {
        File adjFile = ResourceUtils.getFile("classpath:nickname_word_list/positive_adjectives.txt");
        File nounFile = ResourceUtils.getFile("classpath:nickname_word_list/noun.txt");

        String[] adjList = new String(Files.readAllBytes(adjFile.toPath())).split("\n");
        String[] nounList = new String(Files.readAllBytes(nounFile.toPath())).split("\n");

        // check if there is no empty string in adjList
        for (String adj : adjList) {
            Assertions.assertNotEquals("", adj);
        }

        // check if there is no empty string in nounList
        for (String noun : nounList) {
            Assertions.assertNotEquals("", noun);
        }
    }
}