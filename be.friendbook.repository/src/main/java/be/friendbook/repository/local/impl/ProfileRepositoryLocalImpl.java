package be.friendbook.repository.local.impl;

import be.friendbook.model.Profile;
import be.friendbook.repository.ProfileRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class ProfileRepositoryLocalImpl implements ProfileRepository {
    
    private final Map<String,Profile> profiles = new HashMap<>();

    public ProfileRepositoryLocalImpl() {
        Profile profile;
        for (int i = 99; i < 200; i++) {
            profile = new Profile("test"+i, "test"+i, "test"+i,java.sql.Date.valueOf(LocalDate.now()));
            this.profiles.put(profile.getUsername(), profile);
        }
    }
    
    @Override
    public Collection findAll() {
        return new ArrayList<>(this.profiles.values());
    }

    @Override
    public Profile findById(String username) {
        return this.profiles.get(username);
    }

    @Override
    public Profile update(Profile entity) {
        return this.profiles.put(entity.getUsername(), entity);
    }

    @Override
    public Profile insert(Profile entity) {
        return this.profiles.put(entity.getUsername(), entity);
    }

    @Override
    public void delete(Profile entity) {
        this.profiles.remove(entity.getUsername());
    }

    @Override
    public void addFriend(Profile entity, Profile newFriend) {
        entity = this.profiles.get(entity.getUsername());
        entity.getFriends().add(newFriend);
    }

    @Override
    public void removeFriend(Profile entity, Profile newFriend) {
        entity = this.profiles.get(entity.getUsername());
        entity.getFriends().remove(newFriend);
    }

    @Override
    public Collection getFriends(Profile entity) {
        return this.profiles.get(entity.getUsername()).getFriends();
    }
    
    
}