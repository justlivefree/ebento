package org.ozbeman.ebento.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "channel_categories")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ChannelCategory extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @OneToMany(
            mappedBy = "category",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}
    )
    private List<Channel> channels;

    public ChannelCategory(String title) {
        this.title = title;
    }
}
