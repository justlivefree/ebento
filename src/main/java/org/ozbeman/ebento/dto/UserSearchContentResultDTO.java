package org.ozbeman.ebento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ozbeman.ebento.dto.channel.user.UserChannelListDTO;
import org.ozbeman.ebento.dto.event.user.UserEventListDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchContentResultDTO {
    private List<UserEventListDTO> events;
    private List<UserChannelListDTO> channels;
}
