package org.ozbeman.ebento.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.ozbeman.ebento.entity.enums.EventStatus;
import org.ozbeman.ebento.entity.enums.EventType;
import org.ozbeman.ebento.entity.enums.FileType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "events")
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "hall_size", nullable = false)
    private Integer hallSize;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventType type;

    @Column(name = "file_id")
    private UUID fileId;

    @Column(name = "file_type")
    private FileType fileType;

    @Column
    private Double lat;

    @Column
    private Double lon;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "channel_id", nullable = false)
    private Channel channel;
}
