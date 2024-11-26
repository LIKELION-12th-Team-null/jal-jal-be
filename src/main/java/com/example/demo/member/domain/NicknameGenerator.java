package com.example.demo.member.domain;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class NicknameGenerator {

	private static final List<String> adjective = List.of("신비로운", "재빠른", "활기찬", "고요한", "귀여운",
		"호기로운", "용감한", "기발한", "자유로운", "낯선", "조용한", "유쾌한", "차분한", "멋진", "슬기로운", "비범한", "강인한");
	private static final List<String> noun = List.of("벨루가", "범고래", "흰동가리", "혹등고래", "고래상어", "망치상어", "청새치",
		"돌고래", "듀공", "바다코끼리", "바다표범", "해마", "가오리", "물개", "뱀장어", "넙치");

	public static String generateNickname() {
		int adjectiveIndex = ThreadLocalRandom.current().nextInt(8);
		int nounIndex = ThreadLocalRandom.current().nextInt(8);

		return adjective.get(adjectiveIndex) + " " + noun.get(nounIndex);
	}

}
