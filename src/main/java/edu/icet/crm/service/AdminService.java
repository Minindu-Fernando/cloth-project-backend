package edu.icet.crm.service;

import edu.icet.crm.model.Admin;

public interface AdminService {
    Admin persist(Admin admin);
    Admin login(String email, String password);

}
