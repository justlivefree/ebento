package org.ozbema.ebento.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.ozbema.ebento.entity.enums.ChannelStatus;
import org.ozbema.ebento.entity.enums.FileType;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "channels")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Channel extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChannelStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "category_id")
    private ChannelCategory category;

    @Column(name = "avatar_file_id")
    private UUID avatarFileId;

    @Column(name = "avatar_file_type")
    private FileType avatarFileType;

    @Column(name = "background_file_id")
    private UUID backgroundFileId;

    @Column(name = "background_file_type")
    private FileType backgroundFileType;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events;
}
