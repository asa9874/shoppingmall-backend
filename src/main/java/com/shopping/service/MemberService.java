package com.shopping.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.dto.Request.MemberRegisterRequestDto;
import com.shopping.dto.Request.MemberUpdateRequestDto;
import com.shopping.dto.Response.MemberInfoResponseDto;
import com.shopping.dto.Response.NotificationResponseDto;
import com.shopping.model.Customer;
import com.shopping.model.Member;
import com.shopping.model.Notification;
import com.shopping.model.Seller;
import com.shopping.repository.CustomerRepository;
import com.shopping.repository.MemberRepository;
import com.shopping.repository.NotificationRepository;
import com.shopping.repository.SellerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final SellerRepository sellerRepository;
    private final CustomerRepository customerRepository;
    private final NotificationRepository notificationRepository;

    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final SellerService sellerService;

    // 회원가입
    public void register(MemberRegisterRequestDto memberRegisterDto) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(memberRegisterDto.getPassword());

        // 사용자 정보 저장
        Member member = Member.builder()
                .memberId(memberRegisterDto.getMemberId())
                .password(encodedPassword)
                .nickname(memberRegisterDto.getNickname())
                .role(memberRegisterDto.getRole())
                .build();
        memberRepository.save(member);

        if (member.getRole() == Member.Role.CUSTOMER) {
            customerService.registerCustomer(member);
        }

        if (member.getRole() == Member.Role.SELLER) {
            sellerService.registerSeller(member);
        }
    }

    // 내정보조회
    public MemberInfoResponseDto getMyInfo(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));
                MemberInfoResponseDto responseDto = MemberInfoResponseDto.fromEntity(member);
        return responseDto;
    }

    // 유저정보조회
    public MemberInfoResponseDto getMemberInfo(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));
        MemberInfoResponseDto responseDto = MemberInfoResponseDto.fromEntity(member);
        return responseDto;
    }

    // 유저리스트조회
    public List<MemberInfoResponseDto> getMembers() {
        return memberRepository.findAll().stream()
                .map(MemberInfoResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 유저삭제
    @Transactional
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));

        if (Member.Role.CUSTOMER.equals(member.getRole())) {
            Customer customer = customerRepository.findByMemberId(id)
                    .orElseThrow(() -> new RuntimeException("Customer 정보를 찾을 수 없습니다."));
            customerRepository.delete(customer);
        }

        if (Member.Role.SELLER.equals(member.getRole())) {
            Seller seller = sellerRepository.findByMemberId(id)
                    .orElseThrow(() -> new RuntimeException("Seller 정보를 찾을 수 없습니다."));
            sellerRepository.delete(seller);
        }

        memberRepository.findById(id).ifPresentOrElse(
                existingMember -> {
                    throw new RuntimeException("유저 삭제에 실패했습니다. 아직 존재합니다.");
                },
                () -> System.out.println("Member Delte Sucesss"));
    }

    // 유저업데이트
    public Member updateMember(Long id, MemberUpdateRequestDto requestDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));

        if (requestDto.getNickname() == null) {
            throw new RuntimeException(String.format("Invalid NickName"));
        }
        member.setNickname(requestDto.getNickname());
        return memberRepository.save(member);
    }

    public List<NotificationResponseDto> getNotification(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));
        return member.getNotifications().stream()
                .map(NotificationResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public NotificationResponseDto getNotificationDetail(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("알림 정보를 찾을 수 없습니다."));

        notification.setRead(true);
        notificationRepository.save(notification);
        return NotificationResponseDto.fromEntity(notification);
            
    }
        
}
