package com.example.demo.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.member.domain.entity.Member;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findMemberBySocialId(Long id);

}
