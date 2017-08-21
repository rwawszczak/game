package game.model.domain;

import java.util.ArrayList;
import java.util.List;

public class UserDetails {
    private List<Long> characterIds;

    public UserDetails(List<Long> characterIds) {
        this.characterIds = new ArrayList<>(characterIds);
    }

    public List<Long> getCharacterIds() {
        return characterIds;
    }
}
