package org.example.notificationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.notificationservice.entity.Manager;
import org.example.notificationservice.repository.ManagerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final EmailSender emailSender;
    private final ManagerRepository managerRepository;


    public void addManagerEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Received email is null");
        }

        this.managerRepository.save(Manager.builder().email(email).build());
    }

    public void notifyManagers(Long postId) {
        List<String> managerEmails = this.managerRepository
                .findAll()
                .stream()
                .map(Manager::getEmail)
                .toList();
        for (String email : managerEmails) {
            emailSender.sendEmail(
                    email,
                    "car marketplace notification for managers",
                    "Post " + postId + " is saved as PENDING to db, please review it and consider deleting"
            );
        }

    }
}