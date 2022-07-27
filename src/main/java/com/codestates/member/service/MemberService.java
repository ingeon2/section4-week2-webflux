package com.codestates.member.service;

import com.codestates.exception.BusinessLogicException;
import com.codestates.exception.ExceptionCode;
import com.codestates.member.entity.Member;
import com.codestates.member.repository.MemberRepository;
import com.codestates.utils.CustomBeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final CustomBeanUtils<Member> beanUtils;

    public MemberService(MemberRepository memberRepository, CustomBeanUtils<Member> beanUtils) {
        this.memberRepository = memberRepository;
        this.beanUtils = beanUtils;
    }

    public Mono<Member> createMember(Member member) {
        return verifyExistEmail(member.getEmail())
                .then(memberRepository.save(member));
    }

    public Mono<Member> updateMember(Member member) {
        return findVerifiedMember(member.getMemberId())
                .map(findMember -> beanUtils.copyNonNullProperties(member, findMember))
                .flatMap(updatingMember -> memberRepository.save(updatingMember));
    }

    public Mono<Member> findMember(long memberId) {
        return findVerifiedMember(memberId);
    }

    public Mono<Page<Member>> findMembers(PageRequest pageRequest) {
        return memberRepository.findAllBy(pageRequest)
                .collectList()
                .zipWith(memberRepository.count())
                .map(tuple -> new PageImpl<>(tuple.getT1(), pageRequest, tuple.getT2()));
    }

    private Mono<Void> verifyExistEmail(String email) {
        return memberRepository.findByEmail(email)
                .flatMap(findMember -> {
                    if (findMember != null) {
                        return Mono.error(new BusinessLogicException(
                                ExceptionCode.MEMBER_EXISTS));
                    }
                    return Mono.empty();
                });
    }

    private Mono<Member> findVerifiedMember(long memberId) {
        return memberRepository
                .findById(memberId)
                .switchIfEmpty(Mono.error(new BusinessLogicException(
                                                        ExceptionCode.MEMBER_NOT_FOUND)));
    }
}
