package com.developers.member.auth.security.service;

import com.developers.member.auth.security.dto.MemberDto;
import com.developers.member.member.entity.Member;
import com.developers.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Spring Security를 위한 MemberDetailsService
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> result = memberRepository.findByEmail(username);
        Member member = result.orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자입니다."));
        log.info("[MemberDetailService] member: {}", member);
        MemberDto dto = new MemberDto(
                member.getEmail(),
                member.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        log.info("[MemberDetailService] MemberLoginRequestDTO: {} ", dto);
        return dto;
    }
}
