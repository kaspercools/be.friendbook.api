package be.friendbook.repository;

import be.friendbook.model.Profile;

import java.util.Collection;

/**
 *
 * @author kaspercools
 */
public interface ProfileRepository{
    public Collection findAll();
    public Profile findById(String username);
    public Profile update(Profile entity);
    public Profile insert(Profile entity);
    public void delete(Profile entity);
    public void addFriend(Profile entity,Profile newFriend);
    public void removeFriend(Profile entity,Profile newFriend);
    public Collection getFriends(Profile entity);
}
