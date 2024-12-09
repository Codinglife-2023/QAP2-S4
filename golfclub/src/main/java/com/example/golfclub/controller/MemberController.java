package com.example.golfclub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.golfclub.entity.Member;
import com.example.golfclub.repository.MemberRepository;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping
    public ResponseEntity<Member> addMember(@Valid @RequestBody Member member) {
        Member savedMember = memberRepository.save(member);
        return ResponseEntity.status(201).body(savedMember);
    }

    @GetMapping
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return memberRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member updatedMember) {
        return memberRepository.findById(id)
            .map(existingMember -> {
                existingMember.setName(updatedMember.getName());
                existingMember.setAddress(updatedMember.getAddress());
                existingMember.setEmail(updatedMember.getEmail());
                existingMember.setPhoneNumber(updatedMember.getPhoneNumber());
                existingMember.setStartDate(updatedMember.getStartDate());
                existingMember.setDuration(updatedMember.getDuration());
                memberRepository.save(existingMember);
                return ResponseEntity.ok(existingMember);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public List<Member> searchMembersByName(@RequestParam String name) {
        return memberRepository.findByNameContainingIgnoreCase(name);
    }
}
