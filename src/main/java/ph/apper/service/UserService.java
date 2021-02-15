package ph.apper.service;

import ph.apper.exception.*;
import ph.apper.payload.UpdateUserRequest;
import ph.apper.payload.UserData;
import ph.apper.payload.UserRegistrationRequest;
import ph.apper.payload.UserRegistrationResponse;

import java.util.List;

public interface UserService {
    UserRegistrationResponse register(UserRegistrationRequest request) throws InvalidUserRegistrationRequestException;
    void verify(String email, String verificationCode) throws InvalidVerificationRequestException;
    UserData login(String email, String password) throws InvalidLoginCredentialException;
    List<UserData> getAllUsers(boolean excludeUnverified, boolean excludeInactive);
    UserData getUser(String id) throws UserNotFoundException;
    void deleteUser(String id) throws UserNotFoundException;
    void updateUser(String id, UpdateUserRequest request) throws UserNotFoundException, InvalidUserRegistrationRequestException;
    boolean checkVerification(String id) throws UserNotFoundException, UserNotVerifiedActive;
}
