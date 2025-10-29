package org.ozbeman.ebento.services;

import com.github.javafaker.Faker;
import org.ozbeman.ebento.entity.*;
import org.ozbeman.ebento.entity.enums.*;
import org.ozbeman.ebento.repository.ChannelCategoryRepository;
import org.ozbeman.ebento.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Service
@Profile("faker")
public class FakerService implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ChannelCategoryRepository channelCategoryRepository;

    public FakerService(UserRepository userRepository, ChannelCategoryRepository channelCategoryRepository) {
        this.userRepository = userRepository;
        this.channelCategoryRepository = channelCategoryRepository;
    }

    @Override
    public void run(String... args) {
        User adminUser = User.builder()
                .phoneNumber("998971501056")
                .name("Tohir")
                .status(UserStatus.ACTIVE)
                .build();
        adminUser.setRoles(List.of(new Role(adminUser, UserRole.ROLE_USER), new Role(adminUser, UserRole.ROLE_ADMIN)));
        userRepository.save(adminUser);
        Faker faker = new Faker();
        for (int i = 0; i < 20; i++) {
            User user = User.builder()
                    .name(faker.name().username())
                    .phoneNumber(faker.phoneNumber().cellPhone())
                    .status(UserStatus.ACTIVE)
                    .build();
            user.setSessions(
                    List.of(
                            new Session(user, SessionStatus.ACTIVE),
                            new Session(user, SessionStatus.NOT_VERIFIED)
                    )
            );
            user.setRoles(
                    List.of(
                            new Role(user, UserRole.ROLE_USER)
                    )
            );
            userRepository.save(user);
        }

        List<ChannelCategory> categories = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            categories.add(
                    channelCategoryRepository.save(
                            ChannelCategory.builder()
                                    .title(faker.commerce().productName())
                                    .build()
                    ));
        }

        for (int i = 0; i < 20; i++) {
            User user = User.builder()
                    .name(faker.name().username())
                    .phoneNumber(faker.phoneNumber().cellPhone())
                    .status(UserStatus.ACTIVE)
                    .build();
            user.setSessions(
                    List.of(
                            new Session(user, SessionStatus.ACTIVE),
                            new Session(user, SessionStatus.NOT_VERIFIED)
                    )
            );
            user.setRoles(
                    List.of(
                            new Role(user, UserRole.ROLE_USER),
                            new Role(user, UserRole.ROLE_CREATOR)
                    )
            );
            Channel channel = Channel.builder()
                    .title(faker.commerce().department())
                    .description(faker.lorem().sentence(10))
                    .status(ChannelStatus.ACTIVE)
                    .user(user)
                    .category(categories.get(i % 10))
                    .avatarFileId(UUID.randomUUID())
                    .avatarFileType(FileType.JPEG)
                    .backgroundFileId(UUID.randomUUID())
                    .backgroundFileType(FileType.PNG)
                    .build();
            user.setChannel(channel);
            channel.setEvents(IntStream.range(0, 10).<Event>mapToObj(
                    j -> Event.builder()
                            .title(faker.commerce().department())
                            .description(faker.lorem().sentence(10))
                            .startDateTime(LocalDateTime.ofInstant(faker.date().future(1, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()))
                            .endDate(LocalDate.ofInstant(faker.date().future(10, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()))
                            .status(EventStatus.ACTIVE)
                            .type(EventType.ONSITE)
                            .hallSize(new Random().nextInt(50, 200))
                            .channel(channel)
                            .build()
            ).toList());
            userRepository.save(user);
        }
    }
}
