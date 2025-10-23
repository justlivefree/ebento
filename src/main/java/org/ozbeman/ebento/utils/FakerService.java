package org.ozbeman.ebento.utils;

import com.github.javafaker.Faker;
import org.ozbeman.ebento.entity.*;
import org.ozbeman.ebento.entity.enums.*;
import org.ozbeman.ebento.repository.channel.ChannelRepository;
import org.ozbeman.ebento.repository.event.EventRepository;
import org.ozbeman.ebento.repository.user.RoleRepository;
import org.ozbeman.ebento.repository.user.UserRepository;
import org.ozbeman.ebento.services.channel.impl.ChannelAdminServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Service
@Profile("dev")
public class FakerService implements CommandLineRunner {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final ChannelRepository channelRepository;
    private final ChannelAdminServiceImpl channelAdminServiceImpl;
    private final RoleRepository roleRepository;

    public FakerService(UserRepository userRepository, EventRepository eventRepository, ChannelRepository channelRepository, ChannelAdminServiceImpl channelAdminServiceImpl, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.channelRepository = channelRepository;
        this.channelAdminServiceImpl = channelAdminServiceImpl;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
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

        for (int i = 0; i < 10; i++) {
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
                    .build();
            user.setChannel(channel);
            channel.setEvents(IntStream.range(0, 10).<Event>mapToObj(
                    j -> Event.builder()
                            .title(faker.commerce().department())
                            .description(faker.lorem().sentence(10))
                            .startDateTime(LocalDateTime.ofInstant(faker.date().future(1, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()))
                            .endDate(LocalDate.ofInstant(faker.date().future(10, TimeUnit.DAYS).toInstant(), ZoneId.systemDefault()))
                            .status(EventStatus.ACTIVE)
                            .hallSize(100)
                            .channel(channel)
                            .build()
            ).toList());
            userRepository.save(user);
        }
    }
}
