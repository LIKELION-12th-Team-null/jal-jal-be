package com.example.demo.member.domain;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class NicknameGenerator {

	private static final List<String> adjective = List.of("무거운", "귀여운", "졸린", "반가운", "행복한", "압도적인", "슬픈", "감정적인");
	private static final List<String> noun = List.of("카피바라", "돌고래", "여우", "고양이", "강아지", "새우", "바퀴벌레", "코끼리");

	public static String generateNickname() {
		int adjectiveIndex = ThreadLocalRandom.current().nextInt(8);
		int nounIndex = ThreadLocalRandom.current().nextInt(8);

		return adjective.get(adjectiveIndex) + " " + noun.get(nounIndex);
	}

}
