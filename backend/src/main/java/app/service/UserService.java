package app.service;

import app.model.User;

public interface UserService<T extends User> extends CRUDService<T>{
}
